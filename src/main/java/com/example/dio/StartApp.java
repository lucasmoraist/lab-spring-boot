package com.example.dio;

import com.example.dio.model.User;
import com.example.dio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartApp implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setName("Lucas Morais");
        user.setUsername("lucasmn");
        user.setPassword("1234567890");
        repository.save(user);

        for(User u: repository.findAll()){
            System.out.println(u);
        }
    }
}
