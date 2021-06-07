package com.netcracker.skillstable.web;

import com.netcracker.skillstable.Person;
import com.netcracker.skillstable.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/data")
public class HelloController {
    @Autowired
    private PersonRepo personRepo;

    @GetMapping("/people")
    public List<Person> getAll() {
        return (List<Person>) personRepo.findAll();
    }
}
