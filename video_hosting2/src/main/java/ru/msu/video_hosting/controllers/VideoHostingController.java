package ru.msu.video_hosting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.model.Film;
import ru.msu.video_hosting.services.*;

import java.util.Collection;
import java.util.List;

@Controller
public class VideoHostingController {
    @Autowired
    private  ClientService clientService;
    @Autowired
    private  FilmService filmService;
    @Autowired
    private  FilmCopyService filmCopyService;
    @Autowired
    private  HistoryService historyService;
    @Autowired
    private  StorageInfoService storageInfoService;

    // Домашняя страница
    @GetMapping( value = {"/", "/index"})
    public String homePage() {
        return "index";
    }

    // Список всех фильмов
    @GetMapping("/films")
    public String listFilms(Model model) {
        Collection<Film> films = filmService.getAll();
        model.addAttribute("films", films);
        return "films";
    }

    // Страница фильма
    @GetMapping("/film")
    public String film(@RequestParam("id") Integer filmId, Model model) {
        Film film = filmService.getById(filmId);
        model.addAttribute("film", film);
        return "film";
    }

    // Страница входа
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Обработка входа
    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if (clientService.authenticate(email, password)) {
            List<Client> clients = clientService.findByEmail(email);
            if (clients.size() == 1) {
                return "redirect:/client?id=" + clients.get(0).getClientId();
            }
            else {
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }

    // Страница клиента
    @GetMapping("/client")
    public String clientDetails(@RequestParam("id") Integer clientId, Model model) {
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
        return "redirect:/client";
    }

}