package com.secu.springsec3;

import com.secu.springsec3.Entite.Roles;
import com.secu.springsec3.Entite.Users;
import com.secu.springsec3.Service.ServiceSec;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@SpringBootApplication

public class Springsec3Application {
    public static void main(String[] args)
    {
        SpringApplication.run(Springsec3Application.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(ServiceSec serviceSec){
        return args -> {

            serviceSec.addnewuser(new Users(1L,"user1","1234",new ArrayList<>()));
            serviceSec.addnewuser(new Users(2L,"user2","1234",new ArrayList<>()));
            serviceSec.addnewuser(new Users(3L,"user3","1234",new ArrayList<>()));


            serviceSec.addRolenamee(new Roles(1L,"USER"));
            serviceSec.addRolenamee(new Roles(2L,"ADMIN"));
            serviceSec.addRolenamee(new Roles(3L,"RESPONSABLE"));

            serviceSec.addroletousers("user1","USER");
            serviceSec.addroletousers("user1","ADMIN");
            serviceSec.addroletousers("user2","ADMIN");
            serviceSec.addroletousers("user3","ADMIN");
            serviceSec.addroletousers("user3","USER");
            serviceSec.addroletousers("user3","RESPONSABLE");






        };
    }

}
