package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.service.MainMenuService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class A101Handler implements CallbackQueryHandler {
//    private final UserDataCache userDataCache;
    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final MainMenuService mainMenuService;
    private final InlineButtons inlineButtons;

    public A101Handler(UserService userService, ReplyMessageService messagesService, MainMenuService mainMenuService, InlineButtons inlineButtons) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
        this.inlineButtons = inlineButtons;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.A101_SELECTED;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
//        log.info("Bot state before selection/input: {}", userDataCache.getUsersCurrentBotState(userId));
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage callbackAnswer = mainMenuService.getMainMenuMessage(chatId);
        SendMessage sendMessage = messagesService.getReplyMessage(callbackAnswer,"reply.selectProduct");
//        userDataCache.setUsersCurrentBotState(userId,BotState.A101_SELECTED);
        userService.setUsersCurrentBotState(userId,BotState.A101_SELECTED);
        sendMessage.setReplyMarkup(inlineButtons.getOfferInlineButtons());
//        log.info("Bot state after selection/input: {}", userDataCache.getUsersCurrentBotState(userId));
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }

}
