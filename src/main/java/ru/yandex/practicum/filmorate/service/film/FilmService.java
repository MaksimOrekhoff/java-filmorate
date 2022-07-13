package ru.yandex.practicum.filmorate.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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
        return filmStorage.changeFilm(film);
    }

    public void removeFilm(Long idFilm) {
        filmStorage.removesFilm(idFilm);
    }

    public Film getFilm(Long id) {
        return filmStorage.findFilm(id);
    }

    public Film setLike(Long id, Long userId) {
        return filmStorage.userSetLike(id, userId);
    }

    public void removeLike(Long id, Long userId) {
        filmStorage.removeLikeUser(id, userId);
    }

    public List<Film> allPopFilms(Integer count) {
        return filmStorage.popularFilms(count);
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
