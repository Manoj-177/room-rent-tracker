package com.roomrent.roomrenttracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roomrent.roomrenttracker.entity.Tenant;
import com.roomrent.roomrenttracker.entity.TenantHistory;
import com.roomrent.roomrenttracker.entity.User;

public interface TenantHistoryRepository
        extends JpaRepository<TenantHistory, Long> {

    List<TenantHistory> findByUser(User user);
    Optional<Tenant> findByNameAndPhoneAndEmail(
        String name,
        String phone,
        String email);
}