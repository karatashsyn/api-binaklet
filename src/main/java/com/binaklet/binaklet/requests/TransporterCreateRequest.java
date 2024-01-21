package com.binaklet.binaklet.requests;

import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.Order;
import com.binaklet.binaklet.entities.UserComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class TransporterCreateRequest {



    String firstName;
    String lastName;
    String brandName;
    Float volumeCapacity;
    Float massCapacity;
    String email;
    String phoneNumber;
    String addressText;

}
