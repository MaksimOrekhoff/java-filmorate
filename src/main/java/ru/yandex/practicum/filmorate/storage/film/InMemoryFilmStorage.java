package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 0;

    @Override
    public Film addFilm(Film film) {
        film.setId(++id);
        films.put(id, film);
        log.info("Фильм " + film.getName() + " добавлен.");
        return films.get(id);
    }

    @Override
    public void removesFilm(Long idFilm) {
        films.remove(idFilm);
    }

    @Override
    public Film changeFilm(Film film) {
        films.put(film.getId(), film);
        log.debug("Текущее количество фильмов: {}", films.size());
        return film;
    }

    @Override
    public Collection<Film> allFilms() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @Override
    public Film findFilm(Long id) {
        return films.get(id);
    }

    @Override
    public Film userSetLike(Long id, Long userId) {
        films.get(id).getLikes().add(userId);
        return films.get(id);
    }

    @Override
    public void removeLikeUser(Long id, Long userId) {
        films.get(id).getLikes().remove(userId);
    }

    @Override
    public List<Film> popularFilms(Integer count) {
        List<Film> films1 = films.values().stream()
                .sorted(Comparator.comparingLong(film -> film.getLikes().size()))
                .collect(Collectors.toList());
        Collections.reverse(films1);
        return films1;
    }

    @Override
    public Set<Long> keyFilms() {
        return films.keySet();
    }
}
