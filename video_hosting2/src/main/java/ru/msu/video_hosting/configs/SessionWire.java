package ru.msu.video_hosting.configs;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.msu.video_hosting.model.*;

import java.io.IOException;

@Configuration
public class SessionWire {
    @Bean(name="entityManagerFactory")
    public SessionFactory sessionFactory() throws IOException {

        SessionFactory sessionFactory;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClasses(Client.class,
                            Film.class,
                            FilmCopies.class,
                            StorageInfo.class,
                            History.class)
                        .buildMetadata()
                    .buildSessionFactory();
        }
        catch (Exception e) {
            throw e;
        }
        return sessionFactory;
    }
}
