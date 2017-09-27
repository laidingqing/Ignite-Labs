package com.github.ignite.app.controllers;

import com.node.model.Payment;
import com.node.model.User;
import com.node.services.PaymentService;
import com.node.services.UserService;
import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by skylai on 2017/9/27.
 */
@RestController
public class UserController {

    @Autowired
    private Ignite ignite;

    @PostMapping("/payment/add")
    @ResponseBody
    public ResponseEntity<String> add() {
        UserService userService = ignite.services().serviceProxy(UserService.SERVICE_NAME, UserService.class, false);
        userService.addUser(
                User.builder()
                        .name("laidingqing")
                        .email("laidingqing@gmail.com")
                        .creationDate(new Date())
                        .build()
        );
        return ResponseEntity.ok("Created");
    }

    @GetMapping("/payment/{id}")
    @ResponseBody
    public ResponseEntity<User> find(@PathVariable("id") Long id) {
        UserService userService = ignite.services().serviceProxy(UserService.SERVICE_NAME, UserService.class, false);
        return ResponseEntity.ok(userService.getUser(id));
    }
}
