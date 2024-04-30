package ru.msu.video_hosting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.services.*;

import java.util.Collection;

@Controller
public class VideoHostingController {

    private final ClientService clientService = new ClientService();
    private final FilmService filmService = new FilmService();
    private final FilmCopyService filmCopyService = new FilmCopyService();
    private final HistoryService historyService = new HistoryService();
    private final StorageInfoService storageInfoService = new StorageInfoService();


    // Домашняя страница
    @GetMapping( "/index")
    public String homePage(Model model) {
        model.addAttribute("welcomeMessage", "Welcome to the Video Hosting Platform!");
        return "index";
    }

    // Список всех фильмов
    @GetMapping("/films")
    public String listFilms(Model model) {
        Collection<Film> films = filmService.getAll();
        model.addAttribute("films", films);
        return "films";
    }

    // Страница клиента
    @GetMapping("/client/{id}")
    public String clientDetails(@PathVariable("id") Integer clientId, Model model) {
        Client client = clientService.getById(clientId);
        model.addAttribute("client", client);
        return "client";
    }

    // Страница регистрации клиента
    @GetMapping("/register")
    public String showClientRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "registration";
    }

    @PostMapping("/register")
    public String registerClient(Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

}