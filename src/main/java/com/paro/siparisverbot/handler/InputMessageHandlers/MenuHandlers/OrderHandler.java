package com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers;

import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class OrderHandler implements MenuHandler {

    private UserService userService;
    private ReplyMessageService messagesService;

    public OrderHandler(UserService userService, ReplyMessageService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.ORDER.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        //Inputmessages has UserId from Users
        //Messages inside CallBackQueries have botId as a userId
        if (inputMsg.getFrom().getIsBot()){
            int botId = inputMsg.getFrom().getId();
            userId = Integer.parseInt(inputMsg.getChatId().toString());
        }

        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.order");
        userService.setUsersCurrentBotState(userId, BotState.ORDER);
        return sendMessage;
    }
}
