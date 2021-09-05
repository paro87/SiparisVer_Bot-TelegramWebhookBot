package com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers;

import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class SelectMarketHandler implements MenuHandler {
    private UserService userService;
    private ReplyMessageService replyMessageService;
    private InlineButtons inlineButtons;


    public SelectMarketHandler(UserService userService, ReplyMessageService replyMessageService, InlineButtons inlineButtons) {
        this.userService = userService;
        this.replyMessageService = replyMessageService;
        this.inlineButtons = inlineButtons;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.SELECT_MARKET.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = replyMessageService.getReplyMessage(chatId,"reply.selectMarket");
        userService.setUsersCurrentBotState(userId, BotState.SELECT_MARKET);

        sendMessage.setReplyMarkup(inlineButtons.getStoreInlineButtons());
        return sendMessage;
    }

}

