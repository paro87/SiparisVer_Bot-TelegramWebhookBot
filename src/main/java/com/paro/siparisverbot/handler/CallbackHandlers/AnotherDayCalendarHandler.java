package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@Slf4j
public class AnotherDayCalendarHandler implements CallbackQueryHandler{
    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final InlineButtons inlineButtons;

    public AnotherDayCalendarHandler(UserService userService, ReplyMessageService messagesService, InlineButtons inlineButtons) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.inlineButtons = inlineButtons;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ANOTHER_DAY_SPECIAL_OFFER;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.calendar");
//        userDataCache.setUsersCurrentBotState(userId, BotState.ANOTHER_DAY_SPECIAL_OFFER);
        sendMessage.setReplyMarkup(inlineButtons.getDateInlineButtons());
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }

}
