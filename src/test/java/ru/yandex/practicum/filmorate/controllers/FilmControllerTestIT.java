package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createFilmsTest() {
        final Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(response.getBody().getName(), film.getName());
    }

    @Test
    public void getFilmTest() {
        final Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getName(), film.getName());
    }

    @Test
    public void putFilm() {
        Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        film.setName("never");
        restTemplate.put("/films", film, Film.class);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getName(), film.getName());
    }

    @Test
    public void setLike() {
        Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        restTemplate.put("/films/{id}/like/{userId}", Film.class, 1, 5);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertEquals(response.getBody().getLikes().size(), 1);
    }

    @Test
    public void deleteLike() {
        Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        restTemplate.put("/films/{id}/like/{userId}", Film.class, 1, 5);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertEquals(response.getBody().getLikes().size(), 1);

        restTemplate.delete("/films/{id}/like/{userId}", Film.class, 1, 5);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertEquals(response.getBody().getLikes().size(), 0);
    }

    @Test
    public void deleteFilm() {
        Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);
        restTemplate.delete("/films/{id}", Film.class, 1);
        response = restTemplate.getForEntity("/films/1", Film.class);
        assertNull(response.getBody());
    }

    @Test
    public void getAllFilms() {
        Film film = new Film(1, "Begins", "Drama", LocalDate.of(1996, 7, 3), 120, new HashSet<>());
        var response = restTemplate.postForEntity("/films", film, Film.class);

        ResponseEntity<List<Film>> respons = restTemplate.exchange("/films", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Film>>() {
                });
        List<Film> films = respons.getBody();
        assertEquals(films.size(), 1);
        assertEquals(films.get(0).getName(), film.getName());
    }


}