package com.example.backend.dao;

import com.example.backend.entitie.Cour;
import com.example.backend.entitie.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
@CrossOrigin("*")
public interface UtilisateurReposiroty extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByPseudo(String Pseudo);
    List<Utilisateur> findByRolesIdAndRolesNom(Long idRole, String nomRole);

}
