package com.binaklet.binaklet.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class AddressCreateRequest {

    String addressText;

}
