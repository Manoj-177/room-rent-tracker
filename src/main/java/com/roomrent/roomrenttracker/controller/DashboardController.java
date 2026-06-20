package com.roomrent.roomrenttracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.repository.UserRepository;
import com.roomrent.roomrenttracker.service.TenantService;

@Controller
public class DashboardController {

@Autowired
private TenantService tenantService;

@Autowired
private UserRepository userRepository;

@GetMapping("/dashboard")
public String dashboard(Model model) {

    Authentication auth =
            SecurityContextHolder.getContext().getAuthentication();

    String username = auth.getName();

    User user = userRepository
            .findByUsername(username)
            .orElseThrow();

    model.addAttribute(
            "totalTenants",
            tenantService.getTotalTenants(user));

    model.addAttribute(
            "paidTenants",
            tenantService.getPaidTenants(user));

    model.addAttribute(
            "unpaidTenants",
            tenantService.getUnpaidTenants(user));

    model.addAttribute(
            "overdueTenants",
            tenantService.getOverdueTenants(user));

    return "dashboard";
}


}
