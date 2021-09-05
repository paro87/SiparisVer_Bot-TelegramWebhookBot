package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class UpdatePersonalInfoHandler implements CallbackQueryHandler {
    private UserService userService;
    private ReplyMessageService messagesService;

    public UpdatePersonalInfoHandler(UserService userService, ReplyMessageService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.UPDATE_PERSONAL_INFO;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.personal_info_input");
        userService.setUsersCurrentBotState(userId,BotState.UPDATE_PERSONAL_INFO);
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }




}
