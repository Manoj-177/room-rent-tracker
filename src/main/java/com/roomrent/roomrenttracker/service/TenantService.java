package com.roomrent.roomrenttracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roomrent.roomrenttracker.entity.Tenant;
import com.roomrent.roomrenttracker.entity.TenantHistory;
import com.roomrent.roomrenttracker.entity.User;
import com.roomrent.roomrenttracker.repository.TenantHistoryRepository;
import com.roomrent.roomrenttracker.repository.TenantRepository;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private TenantHistoryRepository tenantHistoryRepository;
    public Tenant saveTenant(Tenant tenant) {

    if (tenant.getId() == null) {

        boolean exists =
                tenantRepository
                        .findByNameAndPhoneAndEmail(
                                tenant.getName(),
                                tenant.getPhone(),
                                tenant.getEmail())
                        .isPresent();

        if (exists) {
            throw new RuntimeException(
                    "Tenant already exists");
        }
    }

    return tenantRepository.save(tenant);
}

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }

    public void deleteTenant(Long id) {

    Tenant tenant =
            tenantRepository.findById(id)
                    .orElse(null);

    if (tenant != null) {

        TenantHistory history =
                new TenantHistory();

        history.setName(
                tenant.getName());

        history.setPhone(
                tenant.getPhone());

        history.setEmail(
                tenant.getEmail());

        history.setRoomNumber(
                tenant.getRoomNumber());

        history.setDeletedDate(
                java.time.LocalDate.now());

        history.setUser(
                tenant.getUser());

        tenantHistoryRepository.save(history);

        tenantRepository.delete(tenant);
    }
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

    public List<Tenant> getPaidTenantsByUser(User user) {
        return tenantRepository.findByUserAndStatus(user, "Paid");
    }

    public List<Tenant> getUnpaidTenantsByUser(User user) {
        return tenantRepository.findByUserAndStatus(user, "Unpaid");
    }
    
    public List<Tenant> getOverdueTenantsByUser(User user) {
    return tenantRepository.findOverdueTenantsByUser(user);
}
public void updateExpiredTenants(User user) {

    List<Tenant> tenants = tenantRepository.findByUser(user);

    for (Tenant tenant : tenants) {

        if (tenant.getRentTo() != null
                && tenant.getRentTo().isBefore(java.time.LocalDate.now())
                && "Paid".equalsIgnoreCase(tenant.getStatus())) {

            tenant.setStatus("Unpaid");
            tenantRepository.save(tenant);
        }
    }
}
}