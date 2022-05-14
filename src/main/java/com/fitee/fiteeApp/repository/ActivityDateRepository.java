package com.fitee.fiteeApp.repository;

import com.fitee.fiteeApp.model.ActivityDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityDateRepository extends JpaRepository<ActivityDate, Long>, JpaSpecificationExecutor<ActivityDate> {
}
