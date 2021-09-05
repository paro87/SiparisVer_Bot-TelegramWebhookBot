package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers.CartHandler;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class FinishHandler implements CallbackQueryHandler {
    private UserService userService;
    private CartHandler cartHandler;

    public FinishHandler(UserService userService, CartHandler cartHandler) {
        this.userService = userService;
        this.cartHandler = cartHandler;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FINISH;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = cartHandler.handle(callbackQuery.getMessage());
        userService.setUsersCurrentBotState(userId,BotState.FINISH);
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }




}
