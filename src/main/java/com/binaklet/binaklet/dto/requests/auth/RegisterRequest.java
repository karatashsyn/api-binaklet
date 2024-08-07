package com.binaklet.binaklet.dto.requests.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Ad-soyad boş olamaz.")
    private String name;

    @Email(message = "Email geçerli değil.")
    private String email;

    @Size(min = 6, max = 100,message = "Sifre 6 ile 100 karakter uzunluğunda olmalı.")
    private String password;

    @NotEmpty(message = "Telefon numarası boş olamaz.")
    private String phoneNumber;
}
