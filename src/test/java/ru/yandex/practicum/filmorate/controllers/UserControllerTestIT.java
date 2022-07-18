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
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createUsersTest() {
        final User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        var response = restTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getName(), user.getName());
    }

    @Test
    public void getUserTest() {
        final User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        var response = restTemplate.postForEntity("/users", user, User.class);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getName(), user.getName());
    }

    @Test
    public void putUser() {
        User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        var response = restTemplate.postForEntity("/users", user, User.class);
        user.setName("never");
        restTemplate.put("/users", user, User.class);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getName(), user.getName());
    }

    @Test
    public void addFriends() {
        User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        User user1 = new User(1, "ttt@mail.ru", "Login", "Second", LocalDate.of(1996, 7, 3), new HashSet<>());

        var response = restTemplate.postForEntity("/users", user, User.class);
        restTemplate.postForEntity("/users", user1, User.class);
        restTemplate.put("/users/{id}/friends/{friendId}", User.class, 1, 2);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertEquals(response.getBody().getFriends().size(), 1);
    }

    @Test
    public void deleteFriends() {
        User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        User user1 = new User(1, "ttt@mail.ru", "Login", "Second", LocalDate.of(1996, 7, 3), new HashSet<>());

        var response = restTemplate.postForEntity("/users", user, User.class);
        restTemplate.postForEntity("/users", user1, User.class);
        restTemplate.put("/users/{id}/friends/{friendId}", User.class, 1, 2);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertEquals(response.getBody().getFriends().size(), 1);

        restTemplate.delete("/{id}/friends/{friendId}", User.class, 1, 2);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertEquals(response.getBody().getFriends().size(), 0);

    }

    @Test
    public void deleteUser() {
        User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        var response = restTemplate.postForEntity("/users", user, User.class);
        restTemplate.delete("/users/{id}", Film.class, 1);
        response = restTemplate.getForEntity("/users/1", User.class);
        assertNull(response.getBody());
    }

    @Test
    public void getAllUsers() {
        User user = new User(1, "ttt@mail.ru", "Login", "User", LocalDate.of(1996, 7, 3), new HashSet<>());
        User user1 = new User(1, "ttt@mail.ru", "Login", "Second", LocalDate.of(1996, 7, 3), new HashSet<>());

        restTemplate.postForEntity("/users", user, User.class);
        restTemplate.postForEntity("/users", user1, User.class);

        ResponseEntity<List<User>> response = restTemplate.exchange("/users", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                });
        List<User> users = response.getBody();
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getName(), user.getName());
    }


}
