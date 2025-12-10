package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    
    public Optional<Admin> getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public List<Admin> getActiveAdmins() {
        return adminRepository.findByActiveTrue();
    }
    
    public List<Admin> getAdminsByRole(String role) {
        return adminRepository.findByRole(role);
    }
    
    public Admin updateAdmin(Long id, Admin admin) {
        return adminRepository.findById(id)
            .map(existingAdmin -> {
                existingAdmin.setFullName(admin.getFullName());
                existingAdmin.setEmail(admin.getEmail());
                existingAdmin.setRole(admin.getRole());
                existingAdmin.setDepartment(admin.getDepartment());
                existingAdmin.setActive(admin.isActive());
                return adminRepository.save(existingAdmin);
            })
            .orElseThrow(() -> new RuntimeException("Admin not found"));
    }
    
    public boolean authenticateAdmin(String username, String password) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }
}
