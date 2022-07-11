package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User film);
    void removeUser(Long idUser);
    User changeUser(User user);
    Collection<User> allUsers();
}
