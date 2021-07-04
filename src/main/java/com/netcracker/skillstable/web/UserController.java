package com.netcracker.skillstable.web;

import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/data/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    @ResponseBody
    public Optional<User> getSpecificUser(@RequestParam(name = "id", required = true) Optional<Integer> id) {
        return id.flatMap(aLong -> userService.getUserById(aLong));
    }
}
