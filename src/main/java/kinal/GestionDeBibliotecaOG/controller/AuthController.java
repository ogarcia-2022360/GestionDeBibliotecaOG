package kinal.GestionDeBibliotecaOG.controller;

import kinal.GestionDeBibliotecaOG.dto.AuthResponse;
import kinal.GestionDeBibliotecaOG.dto.LoginRequest;
import kinal.GestionDeBibliotecaOG.dto.RegisterRequest;
import kinal.GestionDeBibliotecaOG.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // Constructor explícito para inyección de dependencias
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}