package com.example.dio.controller;

import com.example.dio.model.auth.dto.AuthenticationDTO;
import com.example.dio.model.auth.dto.RegisterDTO;
import com.example.dio.model.auth.dto.TokenDTO;
import com.example.dio.model.user.User;
import com.example.dio.repository.user.UserRepository;
import com.example.dio.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService service;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(emailPassword);

        var token = service.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){

        if(this.repository.findByEmail(dto.email()) != null) return ResponseEntity.badRequest().build();

        String encrypetdPassword = new BCryptPasswordEncoder().encode(dto.password());
        User user = new User(dto.email(), encrypetdPassword, dto.role());

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }

}
