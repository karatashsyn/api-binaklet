package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class AddressMapper {

    public static AddressDetailDTO toAddressDetailDTO(Address address){
        AddressDetailDTO addressDetailDTO = new AddressDetailDTO();

        addressDetailDTO.setAddressText(address.getAddressText());
        addressDetailDTO.setAddressTitle(address.getAddressTitle());
        addressDetailDTO.setId(address.getId());
        addressDetailDTO.setContactPhone(address.getContactPhone());
        addressDetailDTO.setIsUserDefault(address.getIsUserDefault());
        return addressDetailDTO;
    }


    public static List<AddressDetailDTO> toAddressDetailDTOs(List<Address> addresses){
        if(addresses==null || addresses.isEmpty()){return new ArrayList<>();}
        return addresses.stream().map(AddressMapper::toAddressDetailDTO).toList();
    }
}
