package com.binaklet.binaklet.services;

import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.repositories.TransporterRepository;
import com.binaklet.binaklet.dto.requests.Admin.TransporterCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransporterService{
    private final TransporterRepository transporterRepository;


    public ResponseEntity<List<Transporter>> getAll (){
        return ResponseEntity.ok(transporterRepository.findAll());
    }

    public ResponseEntity<Transporter> create(TransporterCreateRequest request){
        Transporter newTransporter = new Transporter();
        newTransporter.setAddressText(request.getAddressText());
        newTransporter.setEmail(request.getEmail());
        newTransporter.setName(request.getName());
        newTransporter.setBrandName(request.getBrandName());
        newTransporter.setVolumeCapacity(request.getVolumeCapacity());
        newTransporter.setMassCapacity(request.getMassCapacity());
        newTransporter.setPhoneNumber(request.getPhoneNumber());
        newTransporter.setOccupiedCapacity((float) 0);

        transporterRepository.save(newTransporter);
        return ResponseEntity.ok(newTransporter);
    }

//    public Transporter getById (Long id){
//        return transporterRepository.findById(id).orElse(null);
//    }

//     public Long getActiveOrderCount(Transporter transporter){
//       return transporterRepository.activeOrderCount(transporter);
//    }

}
