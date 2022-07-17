package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    FilmService filmService = new FilmService(new InMemoryFilmStorage());

    @Test
    void validateDescriptionFilmMore200() {
        String description = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        final Film film = new Film(1, "Name", description, LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        assertThrows(ValidationException.class, () -> filmService.validate(film));
    }

    @Test
    void validateReleaseFilmIsAfter1895() {
        final Film film = new Film(1, "Name", "Drama", LocalDate.of(1796, 7, 3), 120, new HashSet<>());
        assertThrows(ValidationException.class, () -> filmService.validate(film));
    }


}