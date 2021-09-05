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

import java.time.LocalDate;

@Slf4j
@Component
public class PaymentHandler implements CallbackQueryHandler {
    private UserService userService;
    private ReplyMessageService messagesService;
    private MainMenuService mainMenuService;
    private final InlineButtons inlineButtons;

    public PaymentHandler(UserService userService, ReplyMessageService messagesService, MainMenuService mainMenuService, InlineButtons inlineButtons) {
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
        return BotState.PAYMENT;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage callbackAnswer = mainMenuService.getMainMenuMessage(chatId);
        SendMessage sendMessage = messagesService.getReplyMessage(callbackAnswer,"reply.payment");
        // TODO: Setting delivery date.
        sendMessage.setText(sendMessage.getText()+ LocalDate.now().plusDays(1));
        userService.setUsersCurrentBotState(userId,BotState.PAYMENT);
        sendMessage.setReplyMarkup(inlineButtons.getReceiptDownloadInlineButton());
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }
}
