package game.project.gamoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import game.project.gamoo.models.Jeu;
import game.project.gamoo.services.JeuRepository;

@Controller
public class JeuController {

    @Autowired
    private JeuRepository jeuRepository;

    // Afficher tous les jeux
    @GetMapping("/jeux")
    public String getAllJeux(Model model) {
        model.addAttribute("jeux", jeuRepository.findAll());
        return "jeux"; // Assurez-vous que ce nom correspond au nom de votre fichier HTML sans extension
    }

    // Affichage du formulaire pour ajouter un jeu
    @GetMapping("/jeux/add")
    public String showAddJeuForm(Model model) {
        model.addAttribute("jeu", new Jeu());
        return "add-jeu"; 
    }

    // Ajouter un jeu
    @PostMapping("/jeux/add")
    public String addJeu(@ModelAttribute("jeu") Jeu jeu, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-jeu";
        }
        jeuRepository.save(jeu);
        return "redirect:/jeux";
    }

    // Supprimer un jeu
    @GetMapping("/jeux/delete/{id}")
    public String deleteJeu(@PathVariable int id) {
        Jeu jeu = jeuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid game Id:" + id));
        jeuRepository.delete(jeu);
        return "redirect:/jeux";
    }

    // Mise à jour d'un jeu
    @GetMapping("/jeux/update/{id}")
    public String showUpdateJeuForm(@PathVariable int id, Model model) {
        Jeu jeu = jeuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid game Id:" + id));
        model.addAttribute("jeu", jeu);
        return "update-jeu";
    }

    @PostMapping("/jeux/update/{id}")
    public String updateJeu(@PathVariable int id, @ModelAttribute("jeu") Jeu updatedJeu, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-jeu";
        }
        jeuRepository.findById(id).ifPresent(jeu -> {
            jeu.setTitle(updatedJeu.getTitle());
            jeu.setConsole(updatedJeu.getConsole());
            jeu.setPrix(updatedJeu.getPrix());
            jeu.setIl_y_a_promo(updatedJeu.isIl_y_a_promo());
            jeu.setPrixPromo(updatedJeu.getPrixPromo());
            jeu.setQt_Stock(updatedJeu.getQt_Stock());
            jeu.setEst_dispo(updatedJeu.getEst_dispo());
            jeuRepository.save(jeu);
        });
        return "redirect:/jeux";
    }
}
