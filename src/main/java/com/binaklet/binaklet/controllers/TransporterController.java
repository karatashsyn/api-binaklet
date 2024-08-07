package com.binaklet.binaklet.controllers;

import com.binaklet.binaklet.entities.Transporter;
import com.binaklet.binaklet.dto.requests.Admin.TransporterCreateRequest;
import com.binaklet.binaklet.services.TransporterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transporters")
@Validated
public class TransporterController {
    private final TransporterService transporterService;


    @PostMapping()
    public ResponseEntity<Transporter> createTransporter(@Valid @RequestBody TransporterCreateRequest newTransporter){
        return transporterService.create(newTransporter);
    }
    @GetMapping()
    public ResponseEntity<List<Transporter>> getAllTransporters(){
        return transporterService.getAll();
    }

}