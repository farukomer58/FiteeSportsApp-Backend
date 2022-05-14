package com.fitee.fiteeApp.repository;

import com.fitee.fiteeApp.model.Activity;
import com.fitee.fiteeApp.model.ActivityDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

}
