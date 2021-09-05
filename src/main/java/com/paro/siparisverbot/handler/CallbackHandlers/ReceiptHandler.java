package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.SiparisVerBot;
import com.paro.siparisverbot.model.Receipt;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class ReceiptHandler implements CallbackQueryHandler{
    private final UserService userService;
    private final SiparisVerBot siparisVerBot;

    @Autowired
    public ReceiptHandler(UserService userService, @Lazy SiparisVerBot siparisVerBot) {
        this.userService = userService;
        this.siparisVerBot = siparisVerBot;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.RECEIPT;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);

    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        BotState botState = userService.getUsersCurrentBotState(userId);
        log.info("Bot state before selection/input: {}", botState.state);

        String data=callbackQuery.getData();
        BotState state = valueOfLabel(data);
        log.info("Current bot state after clicking: {}", state);
        Receipt receipt = new Receipt();
        try {
            //TODO: receipt.getReceipt(userId)
            siparisVerBot.sendDocument(chatId, "RECEIPT: "+userId, receipt.getReceipt(userId));
            userService.setUsersCurrentBotState(userId, BotState.RECEIPT);
            log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static BotState valueOfLabel(String label) {
        for (BotState e : BotState.values()) {
            if (e.state.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
