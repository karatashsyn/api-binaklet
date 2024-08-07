package com.binaklet.binaklet.dto.requests.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotBlank(message = "Adres başlığı boş olamaz.")
    String addressTitle;

    @NotBlank(message = "Adres boş olamaz.")
    String addressText;

    @NotBlank(message = "İletişim Numarası boş olamaz.")
    String contactPhone;
}
