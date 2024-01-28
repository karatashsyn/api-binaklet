package com.binaklet.binaklet.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicUserDto {
    Long id;

    String email;
}
