package com.paro.siparisverbot.handler.InputMessageHandlers;

import com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers.PersonalInfoHandler;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class UpdatePersonalInfoInputMessageHandler implements InputMessageHandler {
    private PersonalInfoHandler personalInfoHandler;
    private UserService userService;

    public UpdatePersonalInfoInputMessageHandler(PersonalInfoHandler personalInfoHandler, UserService userService, ReplyMessageService messagesService) {
        this.personalInfoHandler = personalInfoHandler;
        this.userService = userService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.PERSONAL_INFO_INPUT.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = personalInfoHandler.handle(inputMsg);
//        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.order");
        userService.setUsersCurrentBotState(userId, BotState.PERSONAL_INFO_INPUT);
        return sendMessage;
    }
}
