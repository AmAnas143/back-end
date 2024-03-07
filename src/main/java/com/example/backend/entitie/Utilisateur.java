package com.example.backend.entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String mot_de_passe;
    @Temporal(TemporalType.DATE)
    private Date date_naissance;
    private String pays;

    @OneToMany(mappedBy = "utilisateur")
    private Collection<Reclamation> reclamations;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Inscription", joinColumns = @JoinColumn(name = "utilisateurId"), inverseJoinColumns = @JoinColumn(name = "courdId"))
    private Collection<Cour> cours;

    @ManyToMany
    @JoinTable(name = "utilisateur_roles",
            joinColumns = @JoinColumn(name = "utilisateur_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles;


}
