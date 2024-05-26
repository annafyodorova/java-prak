package ru.msu.video_hosting.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.RollbackException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.video_hosting.model.*;
import ru.msu.video_hosting.services.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import java.util.logging.Logger;

@Controller
@Slf4j
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
    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    // Домашняя страница
    @GetMapping( value = {"/", "/index"})
    public String homePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user);
        return "index";
    }

    // Список всех фильмов
    @GetMapping("/films")
    public String listFilms(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", user);

        Collection<Film> films = filmService.getAll();
        model.addAttribute("films", films);
        return "films";
    }

    // Страница фильма
    @GetMapping("/film/{id}")
    public String film(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Integer filmId, Model model) {
        model.addAttribute("user", user);

        Film film = filmService.getById(filmId);
        List<StorageInfo> storageInfo = storageInfoService.findByFilmId(filmId.toString());
        storageInfo.sort((si1, si2)->si1.getPrice().compareTo(si2.getPrice()));
        model.addAttribute("film", film);
        model.addAttribute("storageInfo", storageInfo);
        return "film";
    }

    // Страница клиента
    @GetMapping("/client/{id}")
    public String clientDetails(@AuthenticationPrincipal UserDetails user, @PathVariable("id") Integer clientId, Model model) {
        model.addAttribute("user", user);

        Client client = clientService.getById(clientId);
        model.addAttribute("client", client);
//        Collection<History> histories = historyService.findByClient(client);
//        model.addAttribute("rentedFilms", histories);
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
    @Transactional
    public String saveOrder(@AuthenticationPrincipal UserDetails user,
                                       @RequestBody OrderDTO order,
                                       Model model) {
        model.addAttribute("user", user);
        Client client = clientService.findByEmail(user.getUsername()).get(0);
//        List<FilmCopies> filmCopies = new ArrayList<>();
        log.warn(order.toString());
//        EntityTransaction t = entityManager.getTransaction();

        double sum = 0;
        for(Map.Entry<Integer, Integer> entry: order.getStorages().entrySet()){
            if(entry.getValue() == 0)
                continue;
            sum += storageInfoService.reserveCopies(entry.getKey(), entry.getValue(), client.getClientId());
        }
        if(Math.abs(sum - order.getSum()) > 0.001){
            throw new RollbackException("total price changed");
        }


//        filmCopyId.forEach(filmCopy -> {
//            filmCopies.add(filmCopyService.getById(filmCopy));
//        });
//        int countClients = clientService.countClients();
//        int finalCountClients = countClients + 1;
//
//        filmCopies.forEach(filmCopy -> {
//            History history = new History(finalCountClients, client, filmCopy, dateOfIssue, dateOfReturn);
//            historyService.save(history);
//            model.addAttribute("history", history);
//        });
        return "redirect:/history";
    }
    @GetMapping("/cart")
    public String showCart(@AuthenticationPrincipal UserDetails user, Model model,
                           @CookieValue(name = "encodedCart") byte[] encodedCart) throws IOException {
        model.addAttribute("user", user);
        Map<Integer, Integer> cart = parseCart(Base64.getDecoder().decode(encodedCart));
        Map<Integer, Integer> filteredCart = new HashMap<>();
        cart.forEach((k,v)->{
            if(v != 0)
                filteredCart.put(k, v);
        });
        log.warn(filteredCart.toString());

//        List<FilmCopies> copiesList = new ArrayList<>(cart.size());
//        cart.forEach((k,v)-> copiesList.add(filmCopyService.getById(k)));

        List<StorageInfo> storageInfos = new ArrayList<>(cart.size());
        filteredCart.forEach((k,v) -> storageInfos.add(storageInfoService.getById(k)));

        // pass to analytics
//        model.addAttribute("films", filmService.getAll());
        model.addAttribute("storageInfos", storageInfos);
        model.addAttribute("cart", filteredCart);
        model.addAttribute("cartNotEmpty", filteredCart.size());
        return "/cart";
    }
    @GetMapping("/history")
    public String showHistory(@AuthenticationPrincipal UserDetails user,Model model){
        model.addAttribute(user);
        List<History> histories = historyService.findByClient(clientService.findByEmail(user.getUsername()).get(0));
        histories.sort((h1,h2)->h1.getDateOfIssue().compareTo(h2.getDateOfIssue()));
        model.addAttribute("histories", histories);

        List<StorageInfo> storageInfos = new ArrayList<>();
        histories.forEach(h->storageInfos.add(storageInfoService.findByCopyId(h.getFilmCopyId())));

        model.addAttribute("storageInfos", storageInfos);
        return "/history";
    }

    private Map<Integer, Integer> parseCart(byte[] json) throws IOException {
//        Map<Integer, Integer> cart;
        ObjectMapper objectMapper = new JsonMapper();
//        cart =
        return objectMapper.readerFor(new TypeReference<Map<Integer, Integer>>() { }).readValue(json);
    }

    private void writeCart(Map<Integer, Integer> cart, HttpServletResponse response, String cartCookieName) throws JsonProcessingException {
        ObjectMapper objectMapper = new JsonMapper();
        String stringifiedCookie = objectMapper.writeValueAsString(cart);
        log.warn("write cookie: " + stringifiedCookie);
        Cookie cookie = new Cookie(cartCookieName, stringifiedCookie);
        response.addCookie(cookie);

    }

    @RequestMapping(path = "/test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> test(){
        return ResponseEntity.ok(123);
    }

}