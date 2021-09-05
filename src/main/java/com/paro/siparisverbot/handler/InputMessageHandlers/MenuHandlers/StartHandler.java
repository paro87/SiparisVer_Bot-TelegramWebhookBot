package com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers;

import com.paro.siparisverbot.service.MainMenuService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class StartHandler implements MenuHandler {

    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final MainMenuService mainMenuService;

    public StartHandler(UserService userService, ReplyMessageService messagesService, MainMenuService mainMenuService) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.START.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage callbackAnswer = mainMenuService.getMainMenuMessage(chatId);
        SendMessage sendMessage = messagesService.getReplyMessage(callbackAnswer,"reply.start");
        userService.setUsersCurrentBotState(userId, BotState.START);
        return sendMessage;
    }
}
