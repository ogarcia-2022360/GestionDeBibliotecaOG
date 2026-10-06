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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("El email ya se encuentra registrado", HttpStatus.BAD_REQUEST);
        }

        Rol rolAsignado = (request.getRol() != null) ? request.getRol() : Rol.LECTOR;

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado(EstadoUsuario.ACTIVO)
                .rol(rolAsignado)
                .build();

        usuarioRepository.save(usuario);

        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().name());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        String token = jwtUtils.generateToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token, usuario.getEmail(), usuario.getRol().name());
    }
}