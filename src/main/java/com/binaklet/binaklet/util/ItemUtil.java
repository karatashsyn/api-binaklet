package com.binaklet.binaklet.util;

import com.binaklet.binaklet.entities.Item;
import com.binaklet.binaklet.entities.User;

public class ItemUtil {
    public static Boolean IsFavourite(User user, Item targetItem){
        return user.getFavourites().contains(targetItem);
    }
}
