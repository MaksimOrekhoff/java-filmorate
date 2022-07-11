package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserStorage userStorage;

    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.allUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userStorage.addUser(user);
    }

    @PutMapping
    public User put(@RequestBody User user) {
        return userStorage.changeUser(user);
    }

    @DeleteMapping
    public void removeUser(@RequestBody Long idUser) {
        userStorage.removeUser(idUser);
    }

}


