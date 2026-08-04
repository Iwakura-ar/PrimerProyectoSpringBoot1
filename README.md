# Ferretería Stahlmann - Sistema de Control de Stock

> Aplicación backend robusta para la gestión de inventario, proveedores, ventas y reportes de una ferretería en tiempo real.

---

## Tecnologías Utilizadas

* **Java 21** (o tu versión actual)
* **Spring Boot** (Web, Data JPA)
* **Spring Security** (Autenticación y autorización basada en roles)
* **MySQL** (Base de datos relacional)
* **Maven** (Gestor de dependencias)

## Seguridad y Roles

El sistema implementa **Spring Security** para proteger los endpoints según la jerarquía de los usuarios:
* **`USER`**: Puede listar productos, proveedores, registrar ventas o movimientos y consultar la sección de reportes financieros.
* **`ADMIN`**: Acceso total al sistema, incluyendo el panel de usuarios (creación, desactivación, cambio de roles).

### Credenciales por Defecto (Para Pruebas)

| Usuario | Contraseña | Rol |
| :--- | :--- | :--- |
| `Menem` | `admin123` | `ADMIN` |
| `vendedor1` | `user123` | `USER` |
| `Cheney` | `admin123` | `ADMIN` |

## Módulos del Sistema

* **Productos**: Control de inventario con precios y stock.
* **Proveedores**: Directorio de contacto y catálogo de artículos que provee cada uno.
* **Ventas**: Registro de transacciones con detalle de ítems, cálculo de totales y usuario responsable.
* **Almacén**: Historial de movimientos de entrada y salida ajenos a las ventas (compra de insumos, devoluciones, etc.).
* **Reportes**: Analíticas de artículos más vendidos, valor total del stock y facturación por fechas.
* **Panel de Usuarios**: Control de acceso con roles (`ADMIN` / `USER`) y estados activos/inactivos.

## Requisitos Previos

* JDK 25 o superior.
* MySQL Server activo.
* Un IDE de preferencia (IntelliJ IDEA, Eclipse, VS Code).
* **Postman** (Para pruebas de endpoints).

## Configuración e Instalación

### 1. Clonar el repositorio

### 2. Base de Datos

### 3. Ejecución

La API estará disponible en: `http://localhost:9585`

## Pruebas con Postman

## Endpoints de la API

### Productos (`USER` / `ADMIN`)
* `GET /api/articulos` - Lista todos los artículos (con precio y stock).
* `GET /api/articulos/{id}` - Listar un artículo con determinado id.
* `POST /api/articulos` - Registra un nuevo artículo.
* `PUT /api/articulos/{id}` - Actualizar la información de un artículo.
* `DELETE /api/articulos/{id}` - Eliminar un artículo.
* `PUT /api/articulos/{id}/activar` - Reactivar un artículo desactivado del listado previamente (Ej. por ingreso de stock).
* `GET /api/articulos/inactivos` - Listar artículos desactivados y ocultados del listado (Ej. por falta de stock).

### Proveedores (`USER` / `ADMIN`)
* `GET /api/proveedores` - Lista los proveedores y su información de contacto.
* `GET /api/proveedores/{id}` - Buscar proveedor por id.
* `POST /api/proveedores` - Registra un nuevo proveedor.
* `PUT /api/proveedores/{id}` - Actualizar la información de un proveedor.
* `DELETE /api/proveedores/{id}` - Eliminar la información de un proveedor.
* `PUT /api/proveedores/{id}/articulos` - Asociar artículos a un proveedor (articulos provistos por este).
* `DELETE /api/proveedores/{id}/articulos/{articuloId}` - Desasociar un artículo de un proveedor.

### Ventas (`USER` / `ADMIN`)
* `POST /api/ventas` - Registra una nueva venta (guarda fecha, usuario, detalle y total automatizado).
* `GET /api/ventas` - Historial general de ventas realizadas con detalle consultable.
* `GET /api/ventas/{id}` - Buscar venta por id.

### Almacén (`USER` / `ADMIN`)
* `POST /api/almacen/entradas` - Registrar salidas manuales de stock (Ej. unidades defectuosas, rotas o pérdidas).
* `POST /api/almacen/salidas` - Registrar entradas manuales de stock (Ej. Compra de mercadería).
* `GET /api/almacen/articulo/{articuloId}` - Consultar el historial de movimientos de un artículo (por su id).
* `GET /api/almacen/articulo/stock-bajo` - Listar aquellos artículos con stock bajo (Actualmente, menor a 10 unidades).

### Reportes (`USER` / `ADMIN`)
* `GET /api/reportes/articulos-mas-vendidos` - Listar productos más vendidos.
* `GET /api/reportes/valor-inventario` - Calcula el valor monetario total del inventario actual.
* `GET /api/reportes/ventas-por-periodo` - Total facturado en un rango de fechas.

### Usuarios (Exclusivo ADMIN)
* `GET /api/usuarios/` - Listar todos los usuarios registrados.
* `POST /api/usuarios` - Crea un nuevo usuario en el sistema.
* `DELETE /api/usuarios/{id}` - Desactivar un usuario.
* `PUT /api/usuarios/{id}/rol` - Cambia el rol de un usuario (`USER` / `ADMIN`).

---

Desarrollado por [Francisco Carloni] (https://github.com/Iwakura-ar) 
