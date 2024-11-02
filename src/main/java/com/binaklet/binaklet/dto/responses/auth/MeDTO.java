package com.binaklet.binaklet.dto.responses.auth;

import com.binaklet.binaklet.dto.responses.cart.CartDto;
import com.binaklet.binaklet.dto.responses.item.MyItemDTO;
import com.binaklet.binaklet.entities.Profile;
import com.binaklet.binaklet.enums.UserRole;
import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

// Public user DTO that can be seen by another user.
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class MeDTO {

    Long id;

    UserRole role;

    Profile profile;

    String email;

    String phoneNumber;

    List<AddressDetailDTO> addresses;

    AddressDetailDTO activeAddress;

    List<MyItemDTO>  items;

    Date createdDate;



}
