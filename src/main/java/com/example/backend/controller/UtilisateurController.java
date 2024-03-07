package com.example.backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backend.dao.UtilisateurReposiroty;
import com.example.backend.dto.RoleToUserForm;
import com.example.backend.entitie.Role;
import com.example.backend.entitie.Utilisateur;
import com.example.backend.service.UtilisateurService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin("*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    @Autowired
    private HttpSession session;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    //api for adding role to user
    @PostMapping("/artu")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form){
        utilisateurService.addRoleToUser(form.getPseudo(), form.getUserType());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                Utilisateur utilisateur = utilisateurService.findUserByPseudo(username);

                String access_token = JWT.create()
                        .withSubject(utilisateur.getPseudo())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", utilisateur.getRoles().stream().map(Role::getNom).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }
            catch (Exception exception){
                log.error("Error logging in {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.sendError(FORBIDDEN.value());
            }
        } else {
                throw new RuntimeException("Refresh token is missing");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Utilisateur> addUser(@RequestBody Utilisateur utilisateur){
        Utilisateur utilisateur1 = utilisateurService.addUser(utilisateur);
        return new ResponseEntity<>(utilisateur1, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Utilisateur> getCurrentUser(HttpSession session){
        String username = (String) session.getAttribute("username");
        Utilisateur utilisateur = utilisateurService.findUserByPseudo(username);
        if (utilisateur == null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(utilisateur, HttpStatus.FOUND);
    }

    //methode pour obtenir tous les formateurs
    @GetMapping("/formateurs")
    public ResponseEntity<List<Utilisateur>> findAllFormateur(){
        List<Utilisateur> formateurs = new ArrayList<>();
        formateurs = utilisateurService.obtenirUtilisateursAvecRoleFormateur();
        return new ResponseEntity<>(formateurs, HttpStatus.OK);
    }

    //methode pour obtenir tous les apprenants
    @GetMapping("/apprenants")
    public ResponseEntity<List<Utilisateur>> findAllApprenant(){
        List<Utilisateur> apprenants = new ArrayList<>();
        apprenants = utilisateurService.obtenirUtilisateursAvecRoleAprrenant();
        return new ResponseEntity<>(apprenants, HttpStatus.OK);
    }

    //methode pour obtenir tous les admins
    @GetMapping("/admins")
    public ResponseEntity<List<Utilisateur>> findAllAdmin(){
        List<Utilisateur> admins = new ArrayList<>();
        admins = utilisateurService.obtenirUtilisateursAvecRoleAdmin();
        return new ResponseEntity<>(admins, HttpStatus.FOUND);
    }

    @GetMapping("getRole")
    public ResponseEntity<List<Role>> getRole(){
        String username = (String) session.getAttribute("username");
        Utilisateur utilisateur = utilisateurService.findUserByPseudo(username);
        List<Role> roles = utilisateur.getRoles();
        return new ResponseEntity<>(roles,HttpStatus.OK);
    }


}

