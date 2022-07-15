package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> allUsers() {
        return userStorage.allUsers();
    }

    public User addUser(User user) {
        validate(user);
        return userStorage.addUser(user);
    }

    public User changeUser(User user) {
        validate(user);
        if (userStorage.keyUsers().contains(user.getId())) {
            return userStorage.changeUser(user);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }
    }

    public void removeUser(Long idUser) {
        if (userStorage.keyUsers().contains(idUser)) {
            userStorage.removeUser(idUser);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }
    }

    public Collection<User> allFriendsUser(Long id) {
        return userStorage.allFriends(id);
    }

    public User getUser(Long id) {
        if (userStorage.keyUsers().contains(id)) {
            return userStorage.findUser(id);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }
    }

    public void addInFriends(Long id, Long friendId) {
        if (userStorage.keyUsers().contains(id) && userStorage.keyUsers().contains(friendId)) {
            userStorage.addUserInFriend(id, friendId);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }
    }

    public void deleteUserInFriends(Long id, Long friendId) {
        if (userStorage.keyUsers().contains(id)) {
            userStorage.removeUserInFriends(id, friendId);
        } else {
            throw new UserNotFoundException("Пользователь с таким Id  не существует.");
        }
    }

    public Collection<User> allMutualFriends(Long id, Long otherId) {
        return userStorage.mutualFriends(id, otherId);
    }

    public void validate(User user) {

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }

        if (!user.getEmail().contains("@")) {
            throw new InvalidEmailException("Адрес электронной почты должен содержать символ @.");
        }

        if (user.getLogin() == null || user.getLogin().isBlank()) {
            throw new ValidationException("Login не может быть пустым.");
        }

        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login не может содержать пробелы.");
        }

        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        if (user.getBirthday().compareTo(LocalDate.now()) > 0) {
            throw new ValidationException("Пользователь еще не родился.");
        }

    }
}
