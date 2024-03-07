package com.example.backend.service;

import com.example.backend.entitie.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UtilisateurService {
    void addRoleToUser(String username, String roleName);
    Utilisateur addUser(Utilisateur utilisateur);
    UserDetails loadUserByUsername(String username);
    Utilisateur findUserByPseudo(String pseudo);
    List<Utilisateur> obtenirUtilisateursAvecRoleFormateur();
    List<Utilisateur> obtenirUtilisateursAvecRoleAprrenant();
    List<Utilisateur> obtenirUtilisateursAvecRoleAdmin();
    void updateUser(Utilisateur utilisateur);






}
