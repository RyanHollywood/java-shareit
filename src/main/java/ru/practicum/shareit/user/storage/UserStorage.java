package ru.practicum.shareit.user.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorage {

    private Map<Long, User> storage;
    private long idCounter;

    public UserStorage() {
        storage = new HashMap<>();
        idCounter = 1;
    }

    public void add(User user) {
        storage.put(user.getId(), user);
    }

    public User get(long id) {
        return storage.get(id);
    }

    public Collection<User> getAll() {
        return storage.values();
    }

    public long generateId() {
        return idCounter++;
    }

    public boolean checkDuplicateEmail(String email) {
        for (User user : storage.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void delete(long id) {
        storage.remove(id);
    }

    public boolean contains(long id) {
        return storage.containsKey(id);
    }
}
