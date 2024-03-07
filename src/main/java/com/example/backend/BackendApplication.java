package com.example.backend;

import com.example.backend.config.MyConfiguration;
import com.example.backend.dao.RoleRepository;
import com.example.backend.dao.UtilisateurReposiroty;
import com.example.backend.entitie.Role;
import com.example.backend.entitie.Utilisateur;
import com.example.backend.service.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.ArrayList;
import org.springframework.context.annotation.Import;

@SpringBootApplication @Import(MyConfiguration.class)
@EnableWebMvc
public class BackendApplication {

    public static void main(String[] args) {

        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
/*
   @Bean
    CommandLineRunner run(UtilisateurReposiroty utilisateurReposiroty, RoleRepository roleRepository, UtilisateurService utilisateurService){
        return args -> {
                roleRepository.save(new Role(null, "ROLE_ADMIN"));
                roleRepository.save(new Role(null, "ROLE_FORMATEUR"));
                roleRepository.save(new Role(null, "ROLE_APPRENANT"));

                utilisateurService.addUser(new Utilisateur(null, "simo", "big", "simo", "simoxe@gmail.com", "1234", null, "maroc", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                utilisateurService.addUser(new Utilisateur(null, "anas", "amar", "anas", "simoxe@gmail.com", "1234", null, "maroc", new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));
                utilisateurService.addUser(new Utilisateur(null, "riad", "abdel", "riad", "simoxe@gmail.com", "1234", null, "maroc", new ArrayList<>(), new ArrayList<>(),new ArrayList<>()));

                utilisateurService.addUser(new Utilisateur(null, "kamal", "our", "kamal", "simoxe@gmail.com", "1234", null, "maroc", new ArrayList<>(), new ArrayList<>(),new ArrayList<>()));
                utilisateurService.addUser(new Utilisateur(null, "abdel", "smini", "abdel", "simoxe@gmail.com", "1234", null, "maroc", new ArrayList<>(), new ArrayList<>(),new ArrayList<>()));

                utilisateurService.addRoleToUser("simo", "ROLE_ADMIN");
                utilisateurService.addRoleToUser("anas", "ROLE_ADMIN");
                utilisateurService.addRoleToUser("riad", "ROLE_ADMIN");
                utilisateurService.addRoleToUser("kamal", "ROLE_FORMATEUR");
                utilisateurService.addRoleToUser("abdel", "ROLE_APPRENANT");

        };
    }*/

}
