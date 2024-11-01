package com.binaklet.binaklet.dto.responses.user;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor(staticName = "build")
public class BasicUserDto {
    Long id;

    String email;

    String name;

    String avatar;

    Float rating;

    Integer rateCount;

    AddressDetailDTO activeAddress;

    Boolean isFollowed;

    Integer followers;
}
