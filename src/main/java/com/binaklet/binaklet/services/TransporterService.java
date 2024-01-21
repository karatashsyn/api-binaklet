package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.repositories.TransporterRepository;
import com.binaklet.binaklet.requests.TransporterCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransporterService{
    private final TransporterRepository transporterRepository;
    private final AddressRepository addressRepository;

    public Transporter getById (Long id){
        return transporterRepository.findById(id).orElse(null);
    }
    public Long getActiveOrderCount(Transporter transporter){
       return transporterRepository.activeOrderCount(transporter);
    }

    public List<Transporter> getAll (){
        return transporterRepository.findAll();
    }
//    public Long getActiveOrderCount(Transporter transporter){
//        return transporterRepository.activeOrderCount(transporter);
//    }
    public Transporter create(TransporterCreateRequest request){
        Transporter newTransporter = new Transporter();
        newTransporter.setAddressText(request.getAddressText());
        newTransporter.setEmail(request.getEmail());
        newTransporter.setFirstName(request.getFirstName());
        newTransporter.setLastName(request.getLastName());
        newTransporter.setBrandName(request.getBrandName());
        newTransporter.setVolumeCapacity(request.getVolumeCapacity());
        newTransporter.setMassCapacity(request.getMassCapacity());
        newTransporter.setPhoneNumber(request.getPhoneNumber());
        newTransporter.setOccupiedCapacity((float) 0);
        transporterRepository.save(newTransporter);
        return newTransporter;
    }

}
