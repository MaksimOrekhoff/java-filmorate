package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    UserService userService = new UserService(new InMemoryUserStorage());

    @Test
    void validateEmailIsEmpty() {
        final User user = new User(1, "", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        assertThrows(InvalidEmailException.class, () -> userService.validate(user));
    }

    @Test
    void validateEmailIsNotContainsDog() {
        final User user = new User(1, "newUserEmail", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        assertThrows(InvalidEmailException.class, () -> userService.validate(user));
    }

    @Test
    void validateLoginIsEmpty() {
        final User user = new User(1, "newUser@email", "", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        assertThrows(ValidationException.class, () -> userService.validate(user));
    }

    @Test
    void validateLoginIsContainsSpace() {
        final User user = new User(1, "newUser@email", "Us er", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        assertThrows(ValidationException.class, () -> userService.validate(user));
    }

    @Test
    void validateBirthdayIsFuture() {
        final User user = new User(1, "newUser@email", "User", "User", LocalDate.of(2996, 7, 3), new HashSet<>());
        assertThrows(ValidationException.class, () -> userService.validate(user));
    }
}