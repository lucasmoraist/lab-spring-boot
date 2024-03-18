package com.example.dio.controller;

import com.example.dio.model.User;
import com.example.dio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> getAll(){
        return this.repository.listAll();
    }

    @PostMapping
    public void post(@RequestBody User user){
        this.repository.save(user);
    }
    @PutMapping
    public void put(@RequestBody User user){
        this.repository.update(user);
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable("id") Long id){
        return this.repository.finById(id);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        this.repository.remove(id);
    }

}
