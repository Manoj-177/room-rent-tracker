package com.roomrent.roomrenttracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roomrent.roomrenttracker.entity.Tenant;
import com.roomrent.roomrenttracker.entity.User;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    long countByStatus(String status);

    @Query("""
           SELECT COUNT(t)
           FROM Tenant t
           WHERE t.rentTo < CURRENT_DATE
           """)
    long countOverdueTenants();

    List<Tenant> findByUser(User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, String status);

    @Query("""
           SELECT COUNT(t)
           FROM Tenant t
           WHERE t.user = :user
           AND t.rentTo < CURRENT_DATE
           """)
    long countOverdueTenantsByUser(User user);

   List<Tenant> findByUserAndStatus(User user, String status);

   @Query("""
       SELECT t
       FROM Tenant t
       WHERE t.user = :user
       AND t.rentTo < CURRENT_DATE
       """)
   List<Tenant> findOverdueTenantsByUser(User user);

    Optional<Tenant> findByNameAndPhoneAndEmail(
            String name,
            String phone,
            String email);
}