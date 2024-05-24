package game.project.gamoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import game.project.gamoo.models.Categorie;
import game.project.gamoo.services.CategorieRepository;

@Controller
public class CategorieController {

	@Autowired
	private CategorieRepository categorieRepository;

	// Get all categories
	@GetMapping("/categories_all")
	public String getAllCategories(Model model) {
		model.addAttribute("categories", categorieRepository.findAll());
		return "categories";
	}

	@GetMapping("/categories_all/addCategory")
	public String Addcategories(Model model) {
		model.addAttribute("categorie",new Categorie());
		return "addCategory";
	}

	// Add a new category
	@PostMapping("/categories_all/addCategory")
	public String addCategory(@ModelAttribute("categorie") Categorie categorie, BindingResult result, Model model) {
	    categorieRepository.save(categorie);
	    return "redirect:/categories_all";
	}


	// Delete a category
	@GetMapping("/categories_all/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categorieRepository.delete(categorie);
        return "redirect:/categories_all";
    }

    @GetMapping("/categories_all/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("categorie", categorie);
        return "update-category"; // Assurez-vous que ce nom correspond au nom de votre fichier HTML sans extension
    }

    @PostMapping("/categories_all/update/{id}")
    public String updateCategory(@PathVariable int id, @ModelAttribute("categorie") Categorie updatedCategory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-category";
        }
        categorieRepository.findById(id).ifPresent(category -> {
            category.setNom_categ(updatedCategory.getNom_categ());
            categorieRepository.save(category);
        });
        return "redirect:/categories_all";
}
}
