package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);
    void removeFilm(Long idFilm);
    Film changeFilm(Film film);
    Collection<Film> allFilms();
}
