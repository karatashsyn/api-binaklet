package com.binaklet.binaklet.mappers;

import com.binaklet.binaklet.dto.responses.address.AddressDetailDTO;
import com.binaklet.binaklet.dto.responses.item.BasicItemDTO;
import com.binaklet.binaklet.dto.responses.item.ItemDetailDTO;
import com.binaklet.binaklet.dto.responses.item.MyItemDTO;
import com.binaklet.binaklet.dto.responses.user.BasicUserDto;
import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;
import com.binaklet.binaklet.repositories.AddressRepository;
import com.binaklet.binaklet.util.ItemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
public class ItemMapper {

    private final AddressRepository addressRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;

    public ItemDetailDTO toItemDetailDTO(Item item, User currentUser){
        boolean isFavourite = ItemUtil.IsFavourite(currentUser, item);
        BasicUserDto seller = userMapper.toBasicUserDTO(item.getUser(), currentUser);
        AddressDetailDTO itemAddressDTO = addressMapper.toAddressDetailDTO(addressRepository.findById(item.getPickupAddressId()).get());
        return ItemDetailDTO.build(item.getId(), item.getName(), item.getPrice(), item.getHeight(), item.getWidth(), item.getDepth(), item.getMass(), item.getBrand(), item.getStatus(), item.getDescription(), item.getImages(), item.getCategory(),seller,isFavourite,itemAddressDTO);

    }

    public static BasicItemDTO toBasicItemDTO(Item item){
    BasicItemDTO basicItemDTO = new BasicItemDTO();
    basicItemDTO.setId(item.getId());
    basicItemDTO.setName(item.getName());
    basicItemDTO.setStatus(item.getStatus());
    basicItemDTO.setBrand(item.getBrand());
    basicItemDTO.setPrice(item.getPrice());
    if(!item.getImages().isEmpty()){
        String banner = item.getImages().get(0).getUrl();
        basicItemDTO.setBanner(banner);
    }
    else{
        basicItemDTO.setBanner("");
    }
    return basicItemDTO;
    }

    public static MyItemDTO toMyItemDTO(Item item){
        MyItemDTO myItemDTO = new MyItemDTO();
        myItemDTO.setId(item.getId());
        myItemDTO.setName(item.getName());
        myItemDTO.setPrice(item.getPrice());
        myItemDTO.setWidth(item.getWidth());
        myItemDTO.setHeight(item.getHeight());
        myItemDTO.setDepth(item.getDepth());
        myItemDTO.setMass(item.getMass());
        myItemDTO.setBrand(item.getBrand());
        myItemDTO.setStatus(item.getStatus());
        myItemDTO.setDescription(item.getDescription());
        myItemDTO.setImages(item.getImages());
        myItemDTO.setType(item.getCategory());
        return myItemDTO;
    }

    public static List<BasicItemDTO> toBasicItemDTOList(List<Item> items){
        if(items==null || items.isEmpty()){return new ArrayList<>();}
        return items.stream().map(ItemMapper::toBasicItemDTO).toList();
    }

    public static List<MyItemDTO> toMyItemsDTOList(List<Item> items){
        if(items==null || items.isEmpty()){return new ArrayList<>();}
        return items.stream().map(ItemMapper::toMyItemDTO).toList();
    }

}
