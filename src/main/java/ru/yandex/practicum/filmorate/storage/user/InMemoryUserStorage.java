package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;


    @Override
    public User addUser(User user) {
        validate(user);
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }
    @Override
    public User changeUser(User user) {
        if (users.containsKey(user.getId())) {
            validate(user);
            users.put(user.getId(), user);
            log.debug("Текущее количество пользователей: {}", users.size());
            return user;
        } else {
            throw new ValidationException("Пользователь с таким Id  не существует.");
        }
    }

    @Override
    public Collection<User> allUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @Override
    public void removeUser(Long idUser) {
        if (users.containsKey(idUser)) {
            users.remove(idUser);
            log.info("Пользователь с номером " + idUser + " удален.");
        } else {
            log.info("Пользователь с номером " + idUser + " не найден.");
        }

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
