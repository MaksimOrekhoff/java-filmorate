package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LocalDate happyBirthdayMovie = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Collection<Film> allFilms() {
        return filmStorage.allFilms();
    }

    public Film addFilm(Film film) {
        validate(film);
        return filmStorage.addFilm(film);
    }

    public Film changesFilm(Film film) {
        validate(film);
        if (filmStorage.keyFilms().contains(film.getId())) {
            return filmStorage.changeFilm(film);
        } else {
            throw new FilmNotFoundException("Фильм с таким Id не существует.");
        }

    }

    public void removeFilm(Long idFilm) {
        if (filmStorage.keyFilms().contains(idFilm)) {
            filmStorage.removesFilm(idFilm);
        } else {
            throw new FilmNotFoundException("Film not found.");
        }
    }

    public Film getFilm(Long id) {
        if (filmStorage.keyFilms().contains(id)) {
            return filmStorage.findFilm(id);
        } else {
            throw new FilmNotFoundException("Фильм с таким Id не существует.");
        }
    }

    public Film setLike(Long id, Long userId) {
        if (filmStorage.keyFilms().contains(id)) {
            return filmStorage.userSetLike(id, userId);
        } else {
            throw new FilmNotFoundException("Фильм с таким Id не существует.");
        }
    }

    public void removeLike(Long id, Long userId) {
        if (filmStorage.keyFilms().contains(id) &&
                getFilm(id).getLikes().contains(userId)) {
            filmStorage.removeLikeUser(id, userId);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }

    }

    public List<Film> allPopFilms(Integer count) {
        List<Film> films = filmStorage.popularFilms(count);
        if (count == null) {
            return films.stream().limit(10).collect(Collectors.toList());
        } else {
            return films.stream().limit(count).collect(Collectors.toList());
        }
    }

    public void validate(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название не может быть пустым.");
        }

        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма превышает 200 символов.");
        }

        if (film.getReleaseDate().isBefore(happyBirthdayMovie)) {
            throw new ValidationException("Релиз фильма раньше рождения кино в истории.");
        }

        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
    }

}
