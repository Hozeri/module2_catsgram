package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.PostFeed;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> getFriendsFeed(@RequestBody String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        PostFeed postFeed;
        try {
            String paramsFromString = objectMapper.readValue(body, String.class);
            postFeed = objectMapper.readValue(paramsFromString, PostFeed.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("невалидный формат json", e);
        }

        if (postFeed != null) {
            return postService.findAllByEmail(postFeed.getSort(), postFeed.getSize(), postFeed.getFriends());
        } else {
            throw new RuntimeException("Некорректно переданы параметры");
        }
    }
}
