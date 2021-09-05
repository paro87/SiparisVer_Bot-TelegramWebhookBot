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
public class WrongInputMessageHandler implements InputMessageHandler{

    private final UserService userService;

    @Autowired
    public WrongInputMessageHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getHandlerName() {
        return BotState.WRONG_INPUT.state;
    }

    @Override
    public SendMessage handle(Message message) {
        String text = message.getText();
        int userId = message.getFrom().getId();
        String chatId = message.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
//        SendMessage sendMessage = replyMessageService.getReplyMessage(chatId,"reply.selectMarket");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Yanlış mesaj girişi. Lütfen tekrar giriniz.");
        userService.setUsersCurrentBotState(userId, BotState.WRONG_INPUT);
        return sendMessage;
    }


}
