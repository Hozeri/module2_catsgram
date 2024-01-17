package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;

@RestController
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);
    private final HackCatService hackCatService;
    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    @GetMapping("/home")
    public String homePage() {
        log.debug("Получен запрос");
        return "Котограм";
    }

    @GetMapping("/do-hack")
    public Optional<String> doHack(){
        // хакните этих котиков
        return hackCatService.doHackNow()
                .map(pass -> "Ура! Пароль подобран: " + pass)
                .or(() -> Optional.of("Не удалось подобрать пароль. "
                        + " Проверьте состояние и настройки базы данных."));
    }
}
