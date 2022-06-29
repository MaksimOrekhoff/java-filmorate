package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmController  filmController = new FilmController();

    @Test
    void validateEmailIsEmpty() {
        final Film film = new Film(1, "", "Drama", LocalDate.of(1996, 7, 3), 120);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void validateDescriptionFilmMore200() {
        String description = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        final Film film = new Film(1, "Name", description, LocalDate.of(1996, 7, 3), 120);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void validateReleaseFilmIsAfter1895() {
        final Film film = new Film(1, "Name", "Drama", LocalDate.of(1796, 7, 3), 120);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void validateDurationFilmIsNegative() {
        final Film film = new Film(1, "Name", "Drama", LocalDate.of(1996, 7, 3), -120);
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

}