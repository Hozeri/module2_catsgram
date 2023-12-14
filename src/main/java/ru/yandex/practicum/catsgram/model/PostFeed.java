package ru.yandex.practicum.catsgram.model;

import lombok.Getter;

import java.util.List;

@Getter
public class PostFeed {

    private String sort;
    private Integer size;
    private List<String> friends;
}
