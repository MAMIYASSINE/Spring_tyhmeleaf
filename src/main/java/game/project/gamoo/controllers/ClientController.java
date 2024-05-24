package game.project.gamoo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import game.project.gamoo.models.Client;
import game.project.gamoo.services.ClientRepository;
import jakarta.validation.Valid;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // Get all clients
    @GetMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientRepository.findAll());
        return "clients";
    }

    // Show form to add a new client
    @GetMapping("/clients/add")
    public String showAddClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    // Add a new client
    @PostMapping("/clients/add")
    public String addClient(@Valid @ModelAttribute("client") Client client, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        clientRepository.save(client);
        return "redirect:/";
    }

    // Show form to update a client
    @GetMapping("/clients/update/{id}")
    public String showUpdateClientForm(@PathVariable int id, Model model) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        model.addAttribute("client", client);
        return "updateClient";
    }

    // Update a client
    @PostMapping("/clients/update/{id}")
    public String updateClient(@PathVariable int id, @Valid @ModelAttribute("client") Client updatedClient, BindingResult result) {
        if (result.hasErrors()) {
            return "updateClient";
        }
        clientRepository.findById(id).ifPresent(client -> {
            client.setNom(updatedClient.getNom());
            client.setPrenom(updatedClient.getPrenom());
            client.setEmail(updatedClient.getEmail());
            client.setPassword(updatedClient.getPassword());
            clientRepository.save(client);
        });
        return "redirect:/clients";
    }

    // Delete a client
    @GetMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable int id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
        clientRepository.delete(client);
        return "redirect:/clients";
    }
}
