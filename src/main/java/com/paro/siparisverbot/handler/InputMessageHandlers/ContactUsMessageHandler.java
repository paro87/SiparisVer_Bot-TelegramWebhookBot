package com.paro.siparisverbot.handler.InputMessageHandlers;

import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class ContactUsMessageHandler implements InputMessageHandler {
    private final UserService userService;

    @Autowired
    public ContactUsMessageHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getHandlerName() {
        return BotState.CONTACT_US_INPUT.state;
    }

    @Override
    public SendMessage handle(Message message) {
        final String chatId = message.getChatId().toString();
        final int userId = message.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        String text = message.getText();
//        SendMessage sendMessage = replyMessageService.getReplyMessage(chatId,"reply.selectMarket");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("reply.contact_us");
        userService.setUsersCurrentBotState(userId, BotState.CONTACT_US_INPUT);
        return sendMessage;
    }


}
