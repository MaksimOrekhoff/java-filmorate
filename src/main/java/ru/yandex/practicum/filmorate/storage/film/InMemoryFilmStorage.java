package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 0;
    private final LocalDate happyBirthdayMovie = LocalDate.of(1895, 12, 28);

    @Override
    public Film addFilm(Film film) {

            validate(film);
            film.setId(++id);
            films.put(id, film);
            return films.get(id);

    }

    @Override
    public void removeFilm(Long idFilm) {
        films.remove(idFilm);
    }

    @Override
    public Film changeFilm(Film film) {
        if (films.containsKey(film.getId())) {
            validate(film);
            films.put(film.getId(), film);
            log.debug("Текущее количество фильмов: {}", films.size());
            return film;
        } else {
            throw new FilmAlreadyExistException("Фильм с таким Id не существует.");
        }
    }

    @Override
    public Collection<Film> allFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
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
