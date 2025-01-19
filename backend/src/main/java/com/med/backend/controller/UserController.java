package com.med.backend.controller;

import com.med.backend.model.entity.User;
import com.med.backend.model.enums.Role;
import com.med.backend.model.response.UserRes;
import com.med.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserRes>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/{id}/{role}")
    public ResponseEntity<UserRes> update(@PathVariable UUID id, @PathVariable String role) {
        return ResponseEntity.ok(userService.updateRole(id, Role.valueOf(role)));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/auth/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "Logout successful";
    }
}
