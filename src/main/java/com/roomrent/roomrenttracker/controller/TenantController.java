package com.roomrent.roomrenttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.roomrent.roomrenttracker.entity.Tenant;
import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.repository.UserRepository;
import com.roomrent.roomrenttracker.service.TenantService;

@Controller
public class TenantController {

@Autowired
private TenantService tenantService;

@Autowired
private UserRepository userRepository;

@GetMapping("/tenants")
public String viewTenants(Model model) {

    Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

    String username = auth.getName();

    User user = userRepository
            .findByUsername(username)
            .orElseThrow();

    model.addAttribute(
            "tenants",
            tenantService.getTenantsByUser(user));

    return "tenants";
}

@GetMapping("/add-tenant")
public String addTenantPage(Model model) {

    model.addAttribute(
            "tenant",
            new Tenant());

    return "add-tenant";
}

@PostMapping("/saveTenant")
public String saveTenant(Tenant tenant) {

    Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

    String username = auth.getName();

    User user = userRepository
            .findByUsername(username)
            .orElseThrow();

    tenant.setUser(user);

    tenantService.saveTenant(tenant);

    return "redirect:/tenants";
}

@GetMapping("/edit-tenant/{id}")
public String editTenant(@PathVariable Long id,
                         Model model) {

    model.addAttribute(
            "tenant",
            tenantService.getTenantById(id));

    return "edit-tenant";
}

@PostMapping("/updateTenant")
public String updateTenant(Tenant tenant) {

    Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

    String username = auth.getName();

    User user = userRepository
            .findByUsername(username)
            .orElseThrow();

    tenant.setUser(user);

    tenantService.saveTenant(tenant);

    return "redirect:/tenants";
}

@GetMapping("/delete-tenant/{id}")
public String deleteTenant(@PathVariable Long id) {

    tenantService.deleteTenant(id);

    return "redirect:/tenants";
}

}
