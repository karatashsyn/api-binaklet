package com.binaklet.binaklet.util;

import com.binaklet.binaklet.entities.Address;
import org.springframework.stereotype.Component;

import java.util.List;


public class AddressUtil {
    public static Address findActiveAddress(List<Address> addresses){
        return addresses.stream().filter(Address::getIsUserDefault).toList().get(0);
    }
}
