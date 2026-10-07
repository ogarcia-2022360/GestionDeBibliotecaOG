package kinal.GestionDeBibliotecaOG.service;

import kinal.GestionDeBibliotecaOG.dto.AuthResponse;
import kinal.GestionDeBibliotecaOG.dto.LoginRequest;
import kinal.GestionDeBibliotecaOG.dto.RegisterRequest;
import kinal.GestionDeBibliotecaOG.entity.EstadoUsuario;
import kinal.GestionDeBibliotecaOG.entity.Rol;
import kinal.GestionDeBibliotecaOG.entity.Usuario;
import kinal.GestionDeBibliotecaOG.exception.ApiException;
import kinal.GestionDeBibliotecaOG.repository.UsuarioRepository;
import kinal.GestionDeBibliotecaOG.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // Constructor explícito para inyección de dependencias
    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("El email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
        }

        Rol rolAsignado = (request.getRol() != null) ? request.getRol() : Rol.LECTOR;

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setRol(rolAsignado);

        usuarioRepository.save(usuario);

        UserDetails userDetails = new User(
                usuario.getEmail(),
                usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()))
        );

        String token = jwtUtils.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        UserDetails userDetails = new User(
                usuario.getEmail(),
                usuario.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(usuario.getRol().name()))
        );

        String token = jwtUtils.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}