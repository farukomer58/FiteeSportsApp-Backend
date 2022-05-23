package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.ActivityDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityDateRepository extends JpaRepository<ActivityDate, Long>, JpaSpecificationExecutor<ActivityDate> {
}
