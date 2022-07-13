package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAllFilms() {
        return filmService.allFilms();
    }

    @GetMapping("/{id}")
    public Film findFilm(@PathVariable Long id) {
        return filmService.getFilm(id);
    }

    @GetMapping("/popular")
    public List<Film> topPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.allPopFilms(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film userSetLike(@PathVariable Long id, @PathVariable Long userId) {
        return filmService.setLike(id, userId);
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film changeFilm(@RequestBody Film film) {
        return filmService.changesFilm(film);
    }

    @DeleteMapping("/{idFilm}")
    public void deletesFilm(@PathVariable Long idFilm) {
        filmService.removeFilm(idFilm);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void userRemoveLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }

    @ExceptionHandler
    public Map<String, String> handleNegativeCount(final IllegalArgumentException e) {
        return Map.of("error", "Передан отрицательный параметр count.");
    }
}
