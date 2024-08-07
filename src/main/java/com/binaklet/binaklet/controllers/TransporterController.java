package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Transporter;
import dto.requests.Admin.TransporterCreateRequest;
import com.binaklet.binaklet.services.TransporterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transporters")
public class TransporterController {


    private TransporterService transporterService;

    public TransporterController(TransporterService transporterService){
        this.transporterService=transporterService;
    }

//    @GetMapping
//    public List<Transporter> getTransporters(){
//        return transporterService.getAll();
//    }

    @PostMapping()
    public Transporter createTransporter(@RequestBody TransporterCreateRequest newTransporter){
        System.out.println("Create Denendi");
        return transporterService.create(newTransporter);
    }
    @GetMapping()
    public List<Transporter> getAllTransporters(){
        System.out.println("Get Denendi");
        return transporterService.getAll();
    }

//    @GetMapping({"/{transporterId}"})
//    public Transporter getTransporter(@PathVariable Long transporterId){
//        return transporterService.getById(transporterId);
//    }

//    @PostMapping({"/{transporterId}"})
//    public Transporter updateTransporter(@PathVariable Long transporterId, @RequestBody Transporter newTransporter){
//        return transporterService.update(transporterId,newTransporter);
//    }
}