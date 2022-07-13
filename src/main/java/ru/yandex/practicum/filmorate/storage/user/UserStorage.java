package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User addUser(User user);
    void removeUser(Long idUser);
    User changeUser(User user);
    Collection<User> allUsers();
    Collection<User> allFriends(Long id);
    User findUser(Long id);
    void addUserInFriend(Long id, Long friendId);
    void removeUserInFriends(Long id, Long friendId);
    Collection<User> mutualFriends(Long id, Long otherId);
}

