package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 0;
    private final LocalDate happyBirthdayMovie = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film, HttpServletRequest request) {
        validate(film);
        film.setId(++id);
        films.put(id, film);
        log.info("Получен запрос к эндпоинту: '{} {}', Строка параметров запроса: '{}'",
                request.getMethod(), request.getRequestURI(), request.getQueryString());
        return films.get(id);
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            validate(film);
            films.put(film.getId(), film);
            log.debug("Текущее количество фильмов: {}", films.size());
            return film;
        } else {
            throw new UserAlreadyExistException("Фильм с таким Id не существует.");
        }
    }

    public void validate(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым.");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма превышает 200 символов.");
        }

        if (film.getReleaseDate().compareTo(happyBirthdayMovie) < 0) {
            throw new ValidationException("Релиз фильма раньше рождения кино в истории.");
        }

        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
    }

}
