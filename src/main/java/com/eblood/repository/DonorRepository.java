package com.eblood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eblood.model.Donor;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {
    // You can add custom queries here if needed
}
