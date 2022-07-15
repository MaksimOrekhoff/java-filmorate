package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface FilmStorage {
    Film addFilm(Film film);
    void removesFilm(Long idFilm);
    Film changeFilm(Film film);
    Collection<Film> allFilms();
    Film findFilm(Long id);
    Film userSetLike(Long id, Long userId);
    void removeLikeUser(Long id, Long userId);
    List<Film> popularFilms(Integer count);
    Set<Long> keyFilms();

}
