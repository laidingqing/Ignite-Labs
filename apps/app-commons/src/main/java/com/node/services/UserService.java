package com.node.services;

import com.node.model.Payment;
import com.node.model.User;
import org.apache.ignite.services.Service;

/**
 * Created by skylai on 2017/9/27.
 */
public interface UserService extends Service {

    public static final String SERVICE_NAME = "UserService";

    public User getUser(Long userId);

    public void addUser(User user);
}
