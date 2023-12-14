package ru.yandex.practicum.catsgram.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostService {

    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;
    private static Integer globalId = 0;

    private static Integer getNextId() {
        return globalId++;
    }

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(String sort, Integer size, Integer from) {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
            if (sort.equals("desc")) {
                comp = -1 * comp;
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public List<Post> findAllByEmail(String sort, Integer size, List<String> email) {
        log.debug("Текущее количество постов: {}", posts.size());
        List<Post> result = new ArrayList<>();
        for (String s : email) {
            if (s != null) {
                    result.addAll(posts.stream()
                        .filter(it -> s.equals(it.getAuthor()))
                        .sorted((p0, p1) -> {
                            int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
                            if (sort.equals("desc")) {
                                comp = -1 * comp;
                            }
                            return comp;
                        }).limit(size).collect(Collectors.toList()));
            }
        }
        return result;
    }

    public Post create(Post post) {
        log.debug(String.valueOf(post));
        User user = userService.getUserByEmail(post.getAuthor());
        if (user == null) {
            throw new UserNotFoundException(String.format(
                    "Пользователь %s не найден",
                    post.getAuthor()));
        }
        post.setId(getNextId());
        posts.add(post);
        return post;
    }

    public Post findById(int postId) {
        return posts.stream()
                .filter(post -> post.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }
}
