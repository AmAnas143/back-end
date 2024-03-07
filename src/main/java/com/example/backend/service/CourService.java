package com.example.backend.service;

import com.example.backend.entitie.Cour;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourService {
    Cour addCour(Cour cour);
    void addCourToUser(String peudo, Long id);
    List<Cour> showCours(String pseudo);
    //List<Cour> showFormateurCours(Long id);
}
