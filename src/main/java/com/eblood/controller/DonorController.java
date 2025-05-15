package com.eblood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eblood.model.Donor;
import com.eblood.service.DonorService;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*") // allow all frontend URLs to access
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PostMapping("/add")
    public Donor addDonor(@RequestBody Donor donor) {
        return donorService.saveDonor(donor);
    }

    @GetMapping("/all")
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/{id}")
    public Donor getDonorById(@PathVariable Long id) {
        return donorService.getDonorById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return "Donor with ID " + id + " deleted.";
    }
}

