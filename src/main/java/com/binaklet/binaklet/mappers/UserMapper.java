package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.auth.MeDTO;
import com.binaklet.binaklet.dto.responses.item.MyItemDTO;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.dto.responses.user.UserDetailDTO;
import com.binaklet.binaklet.entities.Address;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.exceptions.ApiRequestException;
import com.binaklet.binaklet.util.UserUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserMapper {


    public static UserDetailDTO toUserDetailDTO(User user, User currentUser){
        List<Address> userAddresses = user.getAddresses();
        Optional<Address> defaultAddress = userAddresses.stream().filter(Address::getIsUserDefault).findFirst();
        if(defaultAddress.isEmpty()){throw new ApiRequestException("Kullanıcının varsayılan adresi yok");}

        return UserDetailDTO.build(user.getId(),user.getRole(),user.getProfile().getName(),user.getEmail(),user.getProfile().getAvatar(), user.getRateCount(), user.getRating(), AddressMapper.toAddressDetailDTO(defaultAddress.get()),user.getPhoneNumber(), ItemMapper.toBasicItemDTOList(user.getItems()),user.getCreatedDate(), UserUtil.IsFollowed(currentUser, user),user.getFollowers().size());
    }



    public static MeDTO toMeDTO(User user){
        List<MyItemDTO> myItems = ItemMapper.toMyItemsDTOList(user.getItems());
        List<AddressDetailDTO> myAddresses = AddressMapper.toAddressDetailDTOs(user.getAddresses());
        return MeDTO.build(user.getId(),user.getRole(),user.getProfile(),user.getEmail(),user.getPhoneNumber(), myAddresses, myItems,user.getCreatedDate());
    }

    public static BasicUserDto toBasicUserDTO(User user, User currentUser){
        Address activeUserAddress = user.getAddresses().stream().filter(Address::getIsUserDefault).toList().get(0);
        AddressDetailDTO activeUserAddressDTO = AddressMapper.toAddressDetailDTO(activeUserAddress);
        Boolean isFollowed = UserUtil.IsFollowed(currentUser, currentUser);

        return BasicUserDto.build(user.getId(), user.getEmail(), user.getProfile().getName(), user.getProfile().getAvatar(),user.getRating(),user.getRateCount(),activeUserAddressDTO,isFollowed,user.getFollowers().size());
    }
}
