package com.example.backend.dao;

import com.example.backend.entitie.Cour;
import com.example.backend.entitie.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface FormateurReposiroty extends JpaRepository<Formateur, Long> {
    Formateur findByPseudo(String pseudo);
}
