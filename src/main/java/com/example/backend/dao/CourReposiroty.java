package com.example.backend.dao;

import com.example.backend.entitie.Cour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface CourReposiroty extends JpaRepository<Cour, Long> {
    @Query("SELECT c FROM Cour c WHERE c.id_formateur = :formateurId")
    List<Cour> findCoursByFormateurId(@Param("formateurId") Long formateurId);
    List<Cour> findAll();
}
