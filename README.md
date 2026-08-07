# Ferretería Stahlmann - Sistema de Control de Stock

> Aplicación backend robusta para la gestión de inventario, proveedores, ventas y reportes de una ferretería.

---

## Tecnologías Utilizadas

* **Java 21**
* **Frontend**: HTML/CSS + jQuery + SweetAlert2 (sin framework)
* **Spring Boot** (Web, Data JPA)
* **Spring Security** (Autenticación y autorización basada en roles)
* **MySQL** (Base de datos relacional)
* **Maven** (Gestor de dependencias)


## Arquitectura

Capas separadas: Controller → Service → Repository, con DTOs propios en cada
endpoint (nunca se exponen entidades JPA directamente). Decisiones destacadas:

- **Transacciones**: registro de ventas con @Transactional (rollback completo
  si falta stock a mitad de una venta con varios ítems)
- **Soft delete**: artículos y usuarios se desactivan, no se borran, para
  preservar la integridad referencial con ventas/movimientos históricos
- **Prevención de ciclos JSON**: @JsonIgnore en relaciones bidireccionales
  (Venta↔VentaDetalle, Articulo↔Proveedor)
- **Reportes con SQL nativo y JPQL con proyección a DTO**, según lo que cada
  consulta necesita

## Seguridad y Roles

El sistema implementa **Spring Security** con autenticación por sesión/Basic Auth
y contraseñas hasheadas con BCrypt.

* **`USER`**: acceso a todos los módulos operativos del sistema (Productos,
  Proveedores, Ventas, Almacén, Reportes).
* **`ADMIN`**: mismo acceso que `USER`, más el panel exclusivo de **Usuarios**
  (creación, desactivación, cambio de roles) — el único módulo con restricción
  de rol real a nivel de backend.

> **Nota de implementación**: actualmente la separación de permisos por rol
> está aplicada de forma granular solo en `/api/usuarios/**` (exclusivo
> `ADMIN`). El resto de los endpoints exige estar autenticado, pero no
> distinguen entre `USER` y `ADMIN` a nivel de ruta. Es una limitación
> conocida, no un descuido — queda como mejora pendiente extender
> `SecurityConfig` con reglas por módulo si el caso de uso lo requiere.

### Credenciales por Defecto (Para Pruebas)

| Usuario | Contraseña | Rol |
| :--- | :--- | :--- |
| `Menem` | `admin123` | `ADMIN` |
| `vendedor1` | `user123` | `USER` |
| `Cheney` | `admin123` | `ADMIN` |

Credenciales solo para entorno local de desarrollo, no usar en producción.

## Módulos del Sistema

* **Productos**: Control de inventario con precios y stock.
* **Proveedores**: Directorio de contacto y catálogo de artículos que provee cada uno.
* **Ventas**: Registro de transacciones con detalle de ítems, cálculo de totales y usuario responsable.
* **Almacén**: Historial de movimientos de entrada y salida ajenos a las ventas (compra de insumos, devoluciones, etc.).
* **Reportes**: Analíticas de artículos más vendidos, valor total del stock y facturación por fechas.
* **Panel de Usuarios**: Control de acceso con roles (`ADMIN` / `USER`) y estados activos/inactivos.

## Requisitos Previos

* JDK 21 o superior.
* MySQL Server activo.
* Un IDE de preferencia (IntelliJ IDEA, Eclipse, VS Code).
* **Postman** (Para pruebas de endpoints).

## Configuración e Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/Iwakura-ar/PrimerProyectoSpringBoot1.git
cd PrimerProyectoSpringBoot1
```

### 2. Base de datos
1. Creá la base en MySQL:
   ```sql
   CREATE DATABASE db_ferreteria;
   ```
2. Creá las tablas ejecutando los scripts SQL del proyecto, o dejá que
      Hibernate las genere automáticamente en el primer arranque
      (`spring.jpa.hibernate.ddl-auto=update` en `application.properties`)
3. Copiá `application.properties.example` a `application.properties` y completá 
tus credenciales locales de MySQL.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/db_ferreteria
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   ```

### 3. Ejecución
```bash
mvn clean install
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:9585`

### 4. Frontend
Abrí `index.html` en el navegador (o serví la carpeta con Live Server) con el
backend ya corriendo. La interfaz consume la API en `localhost:9585`.

## Pruebas con Postman

Todos los endpoints (salvo `/api/auth/**`) requieren autenticación. Usá
**Basic Auth** en Postman con alguna de las credenciales de la tabla de
arriba.

1. En cada request, pestaña **Authorization** → tipo **Basic Auth** → usuario
   y contraseña
2. Para los endpoints `POST`/`PUT`, pestaña **Body** → **raw** → **JSON**

### Ejemplo: registrar una venta
```
POST http://localhost:9585/api/ventas
Body:
{
  "usuarioId": 1,
  "items": [
    { "articuloId": 3, "cantidad": 2 }
  ]
}
```

### Ejemplo: consultar quién está logueado
```
GET http://localhost:9585/api/auth/me
```

Para el resto de los endpoints, seguí la lista de rutas de la sección
anterior — cada uno acepta el mismo esquema de autenticación.

Importá la colección completa desde [`/postman/Stahlmann.postman_collection.json`](./postman/Stahlmann.postman_collection.json).

## Endpoints de la API

### Productos
* `GET /api/articulos` - Lista todos los artículos (con precio y stock).
* `GET /api/articulos/{id}` - Listar un artículo con determinado id.
* `POST /api/articulos` - Registra un nuevo artículo.
* `PUT /api/articulos/{id}` - Actualizar la información de un artículo.
* `DELETE /api/articulos/{id}` - Eliminar un artículo.
* `PUT /api/articulos/{id}/activar` - Reactivar un artículo desactivado del listado previamente (Ej. por ingreso de stock).
* `GET /api/articulos/inactivos` - Listar artículos desactivados y ocultados del listado (Ej. por falta de stock).

### Proveedores 
* `GET /api/proveedores` - Lista los proveedores y su información de contacto.
* `GET /api/proveedores/{id}` - Buscar proveedor por id.
* `POST /api/proveedores` - Registra un nuevo proveedor.
* `PUT /api/proveedores/{id}` - Actualizar la información de un proveedor.
* `DELETE /api/proveedores/{id}` - Eliminar la información de un proveedor.
* `PUT /api/proveedores/{id}/articulos` - Asociar artículos a un proveedor (articulos provistos por este).
* `DELETE /api/proveedores/{id}/articulos/{articuloId}` - Desasociar un artículo de un proveedor.

### Ventas
* `POST /api/ventas` - Registra una nueva venta (guarda fecha, usuario, detalle y total automatizado).
* `GET /api/ventas` - Historial general de ventas realizadas con detalle consultable.
* `GET /api/ventas/{id}` - Buscar venta por id.

### Almacén 
* `POST /api/almacen/entradas` - Registrar entradas manuales de stock (Ej. Compra de mercadería). 
* `POST /api/almacen/salidas` - Registrar salidas manuales de stock (Ej. unidades defectuosas, rotas o pérdidas).
* `GET /api/almacen/articulo/{articuloId}` - Consultar el historial de movimientos de un artículo (por su id).
* `GET /api/almacen/stock-bajo?umbral=N` - Listar aquellos artículos con stock bajo (10 artículos por defecto).

### Reportes 
* `GET /api/reportes/articulos-mas-vendidos` - Listar productos más vendidos.
* `GET /api/reportes/valor-inventario` - Calcula el valor monetario total del inventario actual.
* `GET /api/reportes/ventas-por-periodo` - Total facturado en un rango de fechas.

### Usuarios (Exclusivo ADMIN)
* `GET /api/usuarios` - Listar todos los usuarios registrados.
* `POST /api/usuarios` - Crea un nuevo usuario en el sistema.
* `DELETE /api/usuarios/{id}` - Desactivar un usuario.
* `PUT /api/usuarios/{id}/rol` - Cambia el rol de un usuario (`USER` / `ADMIN`).

---

Desarrollado por [Francisco Carloni](https://github.com/Iwakura-ar)
