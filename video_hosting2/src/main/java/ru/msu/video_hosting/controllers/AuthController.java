package ru.msu.video_hosting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.services.ClientService;

import java.util.List;

/**
 * Created by ytati
 * on 21.05.2024.
 */
@Controller
public class AuthController {

    @Autowired
    ClientService clientService;

    @PostMapping("/register")
    public String registerClient(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        try {
            int size = clientService.countClients();
            size += 1;
            Client client = new Client(size, email, password, fullName);
            clientService.save(client);
            redirectAttributes.addFlashAttribute("success", "Регистрация прошла успешно!");
            return "redirect:/client/" + client.getClientId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            return "redirect:/register";
        }
    }
}
