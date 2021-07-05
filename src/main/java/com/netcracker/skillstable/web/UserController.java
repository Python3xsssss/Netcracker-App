package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@RestController
@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;

//    @GetMapping("/data/user")
//    public Optional<User> getSpecificUser(@RequestParam(name = "id", required = true) Optional<Integer> id) {
//        return id.flatMap(inputId -> userService.getUserById(inputId));
//    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user) {
        userService.createUser(user);

        return "redirect:/";
    }
}
