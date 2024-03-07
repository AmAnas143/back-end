package com.example.backend.service;

import com.example.backend.dao.CourReposiroty;
import com.example.backend.dao.UtilisateurReposiroty;
import com.example.backend.entitie.Cour;
import com.example.backend.entitie.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourServiceImpl implements CourService{

    @Autowired
    private CourReposiroty courReposiroty;

    @Autowired
    private UtilisateurReposiroty utilisateurReposiroty;

    @Override
    public Cour addCour(Cour cour) {
        Cour cour1 = courReposiroty.save(cour);
        return cour1;
    }

    @Override
    public void addCourToUser(String peudo, Long id) {
        Cour cour = courReposiroty.findById(id).get();
        Utilisateur utilisateur = utilisateurReposiroty.findByPseudo(peudo);
        utilisateur.getCours().add(cour);

    }

    @Override
    public List<Cour> showCours(String pseudo) {
        Utilisateur utilisateur = utilisateurReposiroty.findByPseudo(pseudo);
        return new ArrayList<>(utilisateur.getCours());
    }
}
