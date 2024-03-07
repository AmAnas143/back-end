package com.example.backend.controller;

import com.example.backend.dao.CourReposiroty;
import com.example.backend.dao.FormateurReposiroty;
import com.example.backend.entitie.Cour;
import com.example.backend.entitie.Formateur;
import com.example.backend.entitie.Utilisateur;
import com.example.backend.service.CourService;
import com.example.backend.service.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/manageCourse")
@Slf4j
@CrossOrigin("*")
public class CourController {

    @Autowired
    private HttpSession session;
    @Autowired
    private CourService courService;
    @Autowired
    private UtilisateurService utilisateurService;
   @Autowired
    private CourReposiroty courReposiroty;


    @PostMapping("/add")
    public ResponseEntity<Cour> addCour(@RequestBody Cour cour){
        String username = (String) session.getAttribute("username");
       Utilisateur utilisateur = utilisateurService.findUserByPseudo(username);
       Long id_formateur = utilisateur.getId();
        cour.setId_formateur(id_formateur);
        Cour c = courService.addCour(cour);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    //methode pour acheter un cours
    @PostMapping("/inscrire/{id}")
    public ResponseEntity<?> inscrireAuCours(@PathVariable Long id, HttpSession session){
        String username = (String) session.getAttribute("username");
        courService.addCourToUser(username, id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/showcours")
    public ResponseEntity<List<Cour>> showCours(HttpSession session){
        String username = (String) session.getAttribute("username");
        List<Cour> cours = courService.showCours(username);
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }

    @GetMapping("/showformateurcours")
    public ResponseEntity<List<Cour>> getCoursByFormateurId() {
        String username = (String) session.getAttribute("username");
        Utilisateur utilisateur = utilisateurService.findUserByPseudo(username);
        Long id_formateur = utilisateur.getId();
        List<Cour> cours = courReposiroty.findCoursByFormateurId(id_formateur);
        return new ResponseEntity<>(cours, HttpStatus.OK);
    }
    @GetMapping("getCours")
    public ResponseEntity<List<Cour>> getCours(){
        List<Cour> cours = courReposiroty.findAll();
        return new ResponseEntity<>(cours,HttpStatus.OK);
    }
}
