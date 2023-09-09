package com.binaklet.binaklet.repositories;

import com.binaklet.binaklet.entities.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransporterRepository extends JpaRepository<Transporter,Long> {


    @Query("SELECT COUNT(o) from Order o where o.transporter= :transporter And o.status= 'ORDER_STATUS_CREATED'")
    Long activeOrderCount(@Param("transporter") Transporter transporter);
}
