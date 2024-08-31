package com.binaklet.binaklet.dto.requests.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "Ad Soyad boş olamaz.")
    String name;
    String description;

}
