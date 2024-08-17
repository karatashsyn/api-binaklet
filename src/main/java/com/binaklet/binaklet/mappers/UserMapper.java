package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.auth.MeDTO;
import com.binaklet.binaklet.dto.responses.item.MyItemDTO;
import com.binaklet.binaklet.entities.User;

import java.util.List;

public class UserMapper {


    public static MeDTO toMeDTO(User user){
        List<MyItemDTO> myItems = ItemMapper.toMyItemsDTOList(user.getItems());
        List<AddressDetailDTO> myAddresses = AddressMapper.toAddressDetailDTOs(user.getAddresses());
        return MeDTO.build(user.getId(),user.getRole(),user.getProfile(),user.getEmail(),user.getPhoneNumber(), myAddresses, myItems,user.getCreatedDate());
    }
}
