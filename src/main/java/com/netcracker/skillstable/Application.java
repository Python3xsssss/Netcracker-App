package com.netcracker.skillstable;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner (PersonRepo personRepo) {
//        return args -> {
//            Person mgerasimov = new Person("Mikhail", "Gerasimov");
//            Person iermolayev = new Person("Ilya", "Ermolaev");
//            Person arylov = new Person("Alexander", "Rylov");
//            personRepo.save(mgerasimov);
//            personRepo.save(iermolayev);
//            personRepo.save(arylov);
//        };
//    }


}
