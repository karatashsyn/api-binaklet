package com.binaklet.binaklet.DTOs;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BasicUserDto {
    Long id;
    String email;
    String name;
    List<String> addresses;
}
