package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.repositories.TransporterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class TransporterService{
    private final TransporterRepository transporterRepository;

    public Transporter getById (Long id){
        return transporterRepository.findById(id).orElse(null);
    }
    public Long getActiveOrderCount(Transporter transporter){
       return transporterRepository.activeOrderCount(transporter);
    }

}
