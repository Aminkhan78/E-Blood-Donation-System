package com.eblood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eblood.model.Donor;
import com.eblood.repository.DonorRepository;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public Donor saveDonor(Donor donor) {
    try {
        return donorRepository.save(donor);
    } catch (Exception e) {
        e.printStackTrace(); // print full stack trace
        return null;
    }
}


    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id).orElse(null);
    }
}
