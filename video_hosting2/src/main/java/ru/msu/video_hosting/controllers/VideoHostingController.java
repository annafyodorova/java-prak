package ru.msu.video_hosting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.video_hosting.model.*;
import ru.msu.video_hosting.services.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.logging.Logger;

@Controller
public class VideoHostingController {
    private static final Logger logger = Logger.getLogger(VideoHostingController.class.getName());

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
    public String homePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user);
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
    @GetMapping("/film/{id}")
    public String film(@PathVariable("id") Integer filmId, Model model) {
        Film film = filmService.getById(filmId);
        List<StorageInfo> storageInfo = storageInfoService.findByFilmId(filmId.toString());
        model.addAttribute("film", film);
        model.addAttribute("storageInfo", storageInfo);
        return "film";
    }

    // Страница клиента
    @GetMapping("/client/{id}")
    public String clientDetails(@PathVariable("id") Integer clientId, Model model) {
        Client client = clientService.getById(clientId);
        model.addAttribute("client", client);
        Collection<History> histories = historyService.findByClient(client);
        model.addAttribute("rentedFilms", histories);
        return "client";
    }

    // Страница входа
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    // Страница регистрации клиента
    @GetMapping("/register")
    public String showClientRegistrationForm(Model model) {
        model.addAttribute("client", new Client()); // Создаем новый пустой объект Client для формы
        return "registration";
    }



    // Страница оформления заказа
    @GetMapping("/order")
    public String showOrderForm() {
        return "order";
    }

    // Страница сохранения заказа
    @PostMapping("/saveOrder")
    public String saveOrder(@RequestParam("clientId") Integer clientId,
                            @RequestParam("filmCopyId") List<Integer> filmCopyId,
                            @RequestParam("dateOfIssue") LocalDate dateOfIssue,
                            @RequestParam("dateOfReturn") LocalDate dateOfReturn,
                            Model model) {
        Client client = clientService.getById(clientId);
        List<FilmCopies> filmCopies = new ArrayList<>();
        filmCopyId.forEach(filmCopy -> {
            filmCopies.add(filmCopyService.getById(filmCopy));
        });
        int countClients = clientService.countClients();
        int finalCountClients = countClients + 1;

        filmCopies.forEach(filmCopy -> {
            History history = new History(finalCountClients, client, filmCopy, dateOfIssue, dateOfReturn);
            historyService.save(history);
            model.addAttribute("history", history);
        });
        return "createdOrder";
    }
}