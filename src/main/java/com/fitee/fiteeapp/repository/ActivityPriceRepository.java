package com.fitee.fiteeapp.repository;

import com.fitee.fiteeapp.model.ActivityPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityPriceRepository extends JpaRepository<ActivityPrice, Long>, JpaSpecificationExecutor<ActivityPrice> {
}
