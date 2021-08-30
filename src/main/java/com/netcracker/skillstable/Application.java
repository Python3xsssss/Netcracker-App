package com.netcracker.skillstable;

import com.netcracker.skillstable.model.dto.User;
import com.netcracker.skillstable.model.dto.enumeration.Position;
import com.netcracker.skillstable.model.dto.enumeration.Role;
import com.netcracker.skillstable.service.dto.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserService userService) {
        return args -> {
            List<User> allUsers = userService.getAllUsers();
            boolean creatorExists = false;
            for (User user : allUsers) {
                System.out.println(user.getRoles());
                if (user.getRoles().contains(Role.CREATOR)) {
                    creatorExists = true;
                    break;
                }
            }

            if (!creatorExists) {
                User creator = new User(
                        null,
                        "creator",
                        "letmein",
                        null,
                        null,
                        "Mikhail",
                        "Gerasimov",
                        "biathloner2@mail.ru",
                        "Admin of this application",
                        Position.NEWCOMER,
                        new HashSet<>(),
                        true,
                        true
                );
                userService.setCreator(creator);
            }
        };
    }
}
