package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.InvalidEmailException;
import ru.yandex.practicum.catsgram.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final Set<User> users = new HashSet<>();

    public Set<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users;
    }

    public User create(User user) throws InvalidEmailException, UserAlreadyExistException {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("Такой пользователь уже существует");
        } else if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Email не может быть пустым");
        } else {
            users.add(user);
            log.debug(String.valueOf(user));
            return user;
        }
    }

    public User update(User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new InvalidEmailException("Email не может быть пустым");
        } else {
            users.add(user);
            log.debug(String.valueOf(user));
            return user;
        }
    }

    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst().orElse(null);
    }
}
