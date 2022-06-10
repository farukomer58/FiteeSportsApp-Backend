package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

//    List<Activity> findAllByPrice(double price, Pageable pageable);

}
