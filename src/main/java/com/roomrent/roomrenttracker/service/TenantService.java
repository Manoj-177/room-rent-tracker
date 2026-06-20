package com.roomrent.roomrenttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomrent.roomrenttracker.entity.Tenant;
import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.repository.TenantRepository;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }

    public long getTotalTenants() {
    return tenantRepository.count();
    }

    public long getPaidTenants() {
        return tenantRepository.countByStatus("Paid");
    }

    public long getUnpaidTenants() {
        return tenantRepository.countByStatus("Unpaid");
    }

    public long getOverdueTenants() {
        return tenantRepository.countOverdueTenants();
    }

    public List<Tenant> getTenantsByUser(User user) {
    return tenantRepository.findByUser(user);
    }

    public long getTotalTenants(User user) {
        return tenantRepository.countByUser(user);
    }

    public long getPaidTenants(User user) {
        return tenantRepository.countByUserAndStatus(user, "Paid");
    }

    public long getUnpaidTenants(User user) {
        return tenantRepository.countByUserAndStatus(user, "Unpaid");
    }
    public long getOverdueTenants(User user) {

        return tenantRepository.countOverdueTenantsByUser(user);
    }
}