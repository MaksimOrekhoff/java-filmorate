package ru.yandex.practicum.filmorate.controllers;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;

    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.allFilms();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmStorage.addFilm(film);
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        return filmStorage.changeFilm(film);
    }

    @DeleteMapping
    public void deleteFilm(@RequestBody Long idFilm) {
        filmStorage.removeFilm(idFilm);
    }
}
