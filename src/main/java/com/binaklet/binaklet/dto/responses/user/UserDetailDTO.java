package com.binaklet.binaklet.dto.responses.user;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@NoArgsConstructor(staticName = "build")
@AllArgsConstructor
@Data
@Builder
public class UserDetailDTO {

    Long id;

    UserRole role;

    String name;

    String email;

    AddressDetailDTO activeAddress;

    String phoneNumber;

    List<BasicItemDTO>  items;

    Date createdDate;



}
