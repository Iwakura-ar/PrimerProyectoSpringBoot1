package com.hibernate.ferreteria.servicios;

import com.hibernate.ferreteria.DTOs.UsuarioDTO;
import com.hibernate.ferreteria.DTOs.UsuarioRequestDTO;
import com.hibernate.ferreteria.entity.Usuario;
import com.hibernate.ferreteria.repositorio.Repo_usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private Repo_usuarios repoUsuarios;

    @Override
    public UserDetails loadUserByUsername(String nombreUsuario)
            throws UsernameNotFoundException {
        var usuario = repoUsuarios.findByUsuario(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" +
                        nombreUsuario));

        return new User(
                usuario.getUsuario(),
                usuario.getPassword(),
                usuario.isActivo(),
                true,                // accountNonExpired
                true,                // credentialsNonExpired
                true,                // accountNonLocked
                List.of(new SimpleGrantedAuthority(("ROLE_" + usuario.getRol()))));
        }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarUsuarios() {
        return repoUsuarios.findByActivoTrue().stream()
                .map(this::aDto)
                .collect(Collectors.toList());
    }

    public UsuarioDTO crearUsuario(UsuarioRequestDTO request) {
        if (repoUsuarios.findByUsuario(request.getUsuario()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre");
        }
        if (!"ADMIN".equals(request.getRol()) && !"USER".equals(request.getRol())) {
            throw new IllegalArgumentException("El rol debe ser ADMIN o USER");
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setActivo(true);

        return aDto(repoUsuarios.save(usuario));
    }

    public UsuarioDTO cambiarRol(Long id, String nuevoRol) {
        if (!"ADMIN".equals(nuevoRol) && !"USER".equals(nuevoRol)) {
            throw new IllegalArgumentException("El rol debe ser ADMIN o USER");
        }
        Usuario usuario = repoUsuarios.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + id));
        usuario.setRol(nuevoRol);
        return aDto(repoUsuarios.save(usuario));
    }

    public String desactivarUsuario(Long id) {
        return repoUsuarios.findById(id)
                .map(usuario -> {
                    usuario.setActivo(false);
                    repoUsuarios.save(usuario);
                    return "Usuario desactivado correctamente";
                })
                .orElse("No se encontró el usuario: " + id);
    }

    private UsuarioDTO aDto(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getUsuario(), u.getRol(), u.isActivo());
    }
}
