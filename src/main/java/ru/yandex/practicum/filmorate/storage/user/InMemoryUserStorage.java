package ru.yandex.practicum.filmorate.storage.user;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;


@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    @Getter
    private final Map<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User addUser(User user) {
        user.setId(++id);
        users.put(id, user);
        return users.get(id);
    }

    @Override
    public User changeUser(User user) {
        users.put(user.getId(), user);
        log.debug("Текущее количество пользователей: {}", users.size());
        return user;

    }

    @Override
    public Collection<User> allUsers() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @Override
    public void removeUser(Long idUser) {
        users.remove(idUser);
        log.info("Пользователь с номером " + idUser + " удален.");
    }

    @Override
    public Collection<User> allFriends(Long id) {
        Collection<User> friends = new ArrayList<>();
        Set<Long> idFriends = users.get(id).getFriends();
        for (User user : users.values()) {
            for (Long ids : idFriends) {
                if (user.getId() == ids) {
                    friends.add(user);
                }
            }
        }
        return friends;
    }

    @Override
    public User findUser(Long id) {
        return users.get(id);
    }

    @Override
    public void addUserInFriend(Long id, Long friendId) {
        users.get(id).getFriends().add(friendId);
        users.get(friendId).getFriends().add(id);
        log.info("Пользователь с номером " + id + " добавил в друзья пользователя " + friendId);
    }

    @Override
    public void removeUserInFriends(Long id, Long friendId) {
        users.get(id).getFriends().remove(friendId);
        users.get(friendId).getFriends().remove(id);
        log.info("Пользователь с номером " + id + " удалил из друзей пользователя " + friendId);
    }

    @Override
    public Collection<User> mutualFriends(Long id, Long otherId) {
        Collection<User> first = allFriends(id);
        Collection<User> second = allFriends(otherId);
        if (first.size() >= second.size()) {
            first.retainAll(second);
            return first;
        } else {
            second.retainAll(first);
            return second;
        }
    }

    @Override
    public Set<Long> keyUsers() {
        return users.keySet();
    }
}
