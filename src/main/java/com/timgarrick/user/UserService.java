package com.timgarrick.user;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    static List<User> userList = new ArrayList<>();


    public static User createUser(User user) {
        user.setUserID(userList.size()+1);
        userList.add(user);
        return userList.get((userList.size()-1));

    }

    public static List<User> getUserList() {
        if (userList.size()>0) {
            return userList;
        } else {
            return null;
        }
    }

    public static User findUser(String name) {
        for (User user: userList) {
            if (user.getUsername().equalsIgnoreCase(name)) {
                return user;
            }
        }

        return null;
    }

}
