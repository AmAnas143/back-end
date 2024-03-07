package com.example.backend.service;

import com.example.backend.dao.RoleRepository;
import com.example.backend.dao.UtilisateurReposiroty;
import com.example.backend.entitie.Role;
import com.example.backend.entitie.Utilisateur;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional

public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {
    private final UtilisateurReposiroty utilisateurReposiroty;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurReposiroty utilisateurReposiroty, RoleRepository  roleRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurReposiroty = utilisateurReposiroty;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        Utilisateur utilisateur = utilisateurReposiroty.findByPseudo(username);
        Role role = roleRepository.findByNom(roleName);
        utilisateur.getRoles().add(role);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurReposiroty.findByPseudo(username);
        if(utilisateur == null){
            throw new UsernameNotFoundException("Utilisateur n'existe pas");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        utilisateur.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getNom()));
        });
        return new org.springframework.security.core.userdetails.User(utilisateur.getPseudo(), utilisateur.getMot_de_passe(), authorities);
    }

    @Override
    public Utilisateur findUserByPseudo(String pseudo) {
        Utilisateur utilisateur = utilisateurReposiroty.findByPseudo(pseudo);
        return utilisateur;
    }

    @Override
    public List<Utilisateur> obtenirUtilisateursAvecRoleFormateur() {
        Long id_role = 2L;
        String nomRole = "ROLE_FORMATEUR";
        return utilisateurReposiroty.findByRolesIdAndRolesNom(id_role,nomRole);
    }

    @Override
    public List<Utilisateur> obtenirUtilisateursAvecRoleAprrenant() {
        Long id_role = 3L;
        String nomRole = "ROLE_APPRENANT";
        return utilisateurReposiroty.findByRolesIdAndRolesNom(id_role, nomRole);
    }

    @Override
    public List<Utilisateur> obtenirUtilisateursAvecRoleAdmin() {
        Long id_role = 1L;
        String nomRole = "ROLE_ADMIN";
        return utilisateurReposiroty.findByRolesIdAndRolesNom(id_role, nomRole);
    }

    @Override
    public void updateUser(Utilisateur utilisateur) {
        utilisateurReposiroty.save(utilisateur);
    }


    @Override
    public Utilisateur addUser(Utilisateur utilisateur){
        utilisateur.setMot_de_passe(passwordEncoder.encode(utilisateur.getMot_de_passe()));
         utilisateurReposiroty.save(utilisateur);
         return utilisateur;
    }
}
