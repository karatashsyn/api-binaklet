package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.repositories.TransporterRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class TransporterService extends BaseService<Transporter,Long>{

    TransporterRepository transporterRepository;
    public TransporterService(JpaRepository<Transporter, Long> repository,TransporterRepository transporterRepository) {

        super(repository);
        this.transporterRepository=transporterRepository;

    }

    public Long getActiveOrderCount(Transporter transporter){
       return transporterRepository.activeOrderCount(transporter);
    }

}
