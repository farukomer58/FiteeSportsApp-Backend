package com.fitee.service;

import com.fitee.exception.ResourceNotFoundException;
import com.fitee.model.Freelancer;
import com.fitee.repository.FreelancerRepository;
import lombok.AllArgsConstructor;
import lombok.var;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class FreelancerService {

    private final FreelancerRepository freelancerRepository;
    //    private final ImageRepository imageRepository;
    private final ModelMapper modelmapper;

    /**
     * Retrieves the freelancer-info with a given supplier-id. If not found throws a
     * ResourceNotFoundException indicating that the supplier-info could not be found.
     */
    public Freelancer findById(Long id) {
        return freelancerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Freelancer not  found with id: " + id));
    }

    /**
     * Retrieves all freelancer-info and returns it.
     */
    public List<Freelancer> findAll() {
        return freelancerRepository.findAll();
    }

    /**
     * Updates the freelancer-info of the current logged-in user. This is partially done by
     * retrieving a reference of the supplierEntity through lazy-loading without having to
     * access the actual database and finally mapping the new data into the current data.
     */
    public boolean updateById(Freelancer newFreelancer) {
        // var principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // long supplierId((Number) principal.getClaims().get("sid")).longValue()

        long freelancerId = newFreelancer.getId();
        Freelancer currentFreelancer = freelancerRepository.getOne(freelancerId); // lazy load

        // update address relationship
//        var address = newSupplier.getAddresses().iterator().next();
//        address.setSupplier(currentSupplier);
//
//        modelmapper.map(newSupplier, currentSupplier); // new --> updateInto --> current
//        if (imageEntity != null) {
//            currentSupplier.setProfileImage(imageEntity);
//        }
        return freelancerRepository.save(currentFreelancer) != null ? true : false;
    }

}
