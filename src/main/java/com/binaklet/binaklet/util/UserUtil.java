package com.binaklet.binaklet.util;

import com.binaklet.binaklet.entities.User;

public class UserUtil {
    public static Boolean IsFollowed(User user, User targetUser){
        return user.getFollowings().contains(targetUser);
    }
}
