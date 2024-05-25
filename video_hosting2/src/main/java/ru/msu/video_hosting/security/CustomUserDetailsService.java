package ru.msu.video_hosting.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.msu.video_hosting.DAO.ClientDAO;
import ru.msu.video_hosting.model.Client;
import ru.msu.video_hosting.services.ClientService;

import java.util.Collections;

public class CustomUserDetailsService implements UserDetailsService {
    private final ClientService clientService;
    public CustomUserDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optional = clientService.findByEmail(username);
        if(optional.isEmpty())
            throw new UsernameNotFoundException("Wrong Credentials :CustomUserDetailsService");
        Client cl = optional.get(0);
        User u = new User(username, cl.getPassword(), Collections.emptyList());
        System.out.println(u);
        return u;
    }
}