package com.binaklet.binaklet.dto.requests.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransporterCreateRequest {

    @NotBlank(message = "İsim boş olamaz.")
    String name;

    @NotBlank(message = "Marka boş olamaz.")
    String brandName;

    @NotNull(message = "Hacim kapasitesi boş olamaz.")
    Float volumeCapacity;

    @NotNull(message = "Kütle kapasitesi boş olamaz.")
    Float massCapacity;

    @Email(message = "Geçerli bir email girmelisiniz.")
    String email;

    @NotBlank(message = "Telefon numarası boş olamaz.")
    String phoneNumber;


    @NotBlank
    String addressText;
}
