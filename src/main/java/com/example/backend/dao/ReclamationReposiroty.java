package com.example.backend.dao;

import com.example.backend.entitie.Cour;
import com.example.backend.entitie.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ReclamationReposiroty extends JpaRepository<Reclamation, Long> {
}
