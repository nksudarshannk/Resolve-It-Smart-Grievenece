package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        if (adminService.authenticateAdmin(username, password)) {
            return adminService.getAdminByUsername(username)
                .map(admin -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("admin", admin);
                    response.put("message", "Login successful");
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.badRequest().build());
        }
        
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Invalid credentials");
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin created = adminService.createAdmin(admin);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Admin>> getActiveAdmins() {
        return ResponseEntity.ok(adminService.getActiveAdmins());
    }
    
    @GetMapping("/role/{role}")
    public ResponseEntity<List<Admin>> getAdminsByRole(@PathVariable String role) {
        return ResponseEntity.ok(adminService.getAdminsByRole(role));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return ResponseEntity.ok(adminService.updateAdmin(id, admin));
    }
}
