package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers.OrderHandler;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class ContinueHandler implements CallbackQueryHandler {
    private UserService userService;
    private OrderHandler orderHandler;

    public ContinueHandler(UserService userService, OrderHandler orderHandler) {
        this.userService = userService;
        this.orderHandler = orderHandler;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CONTINUE;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = orderHandler.handle(callbackQuery.getMessage());
        userService.setUsersCurrentBotState(userId,BotState.CONTINUE);
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }




}
