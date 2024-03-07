package com.example.backend.entitie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cour {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String lien;
    private double prix;
    @Temporal(TemporalType.DATE)
    private Date date_publication;
    private String photo;
    @JsonIgnore
    @ManyToMany(mappedBy = "cours")
    private Collection<Utilisateur> utilisateurs;

    @Column(name = "id_formateur")
    private Long id_formateur;

    @JsonIgnore
    @OneToMany(mappedBy = "cour")
    private Collection<Quiz> quizzes;



}
