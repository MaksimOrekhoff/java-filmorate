package ru.yandex.practicum.filmorate.controllers;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public User findUser(@PathVariable Long id) {
        return userService.getUser(id);
    }


    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> mutualFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.allMutualFriends(id, otherId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getAllFriendsUser(@PathVariable Long id) {
        return userService.allFriendsUser(id);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public User put(@RequestBody User user) {
        return userService.changeUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addInFriends(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addInFriends(id, friendId);
    }

    @DeleteMapping("/{idUser}")
    public void removeUser(@PathVariable Long idUser) {
        userService.removeUser(idUser);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeUserInFriends(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteUserInFriends(id, friendId);
    }
}


