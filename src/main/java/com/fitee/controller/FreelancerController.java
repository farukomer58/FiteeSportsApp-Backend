package com.fitee.controller;

import com.fitee.model.Freelancer;
import com.fitee.service.FreelancerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/freelancers")
public class FreelancerController {

    private final FreelancerService freelancerService;

    /**
     * Retrieves freelancer-info with the given freelancer-id.
     */
    @GetMapping("/{id}")
    public Freelancer getSupplierInfo(@PathVariable long id) {
        return freelancerService.findById(id);
    }

    /**
     * Retrieves all freelancer-info.
     */
    @GetMapping("")
    public List<Freelancer> getAllSuppliers() {
        return freelancerService.findAll();
    }

    /**
     * Updates the freelancer-info of the current logged-in user. and return whether update succeeded
     */
    @PutMapping
    public boolean updateSupplierInfo(@RequestBody Freelancer freelancer) throws IOException {
        return this.freelancerService.updateById(freelancer);
    }

}
