package com.paro.siparisverbot.controller;

import com.paro.siparisverbot.SiparisVerBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@RestController
public class SiparisVerController {
    private final SiparisVerBot siparisVerBot;

    @Autowired
    public SiparisVerController(SiparisVerBot siparisVerBot){
        this.siparisVerBot=siparisVerBot;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update){
        log.info("Update received. Id: {}", update.getUpdateId());
        return siparisVerBot.onWebhookUpdateReceived(update);
    }
}
