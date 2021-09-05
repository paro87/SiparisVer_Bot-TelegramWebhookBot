package com.paro.siparisverbot.handler.InputMessageHandlers;

import com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers.DeliveryAddressHandler;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class UpdateDeliveryAddressInputMessageHandler implements InputMessageHandler {
    private DeliveryAddressHandler deliveryAddressHandler;
    private UserService userService;
    private ReplyMessageService messagesService;

    public UpdateDeliveryAddressInputMessageHandler(DeliveryAddressHandler deliveryAddressHandler, UserService userService, ReplyMessageService messagesService) {
        this.deliveryAddressHandler = deliveryAddressHandler;
        this.userService = userService;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.DELIVERY_ADDRESS_INPUT.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = deliveryAddressHandler.handle(inputMsg);
//        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.order");
        userService.setUsersCurrentBotState(userId, BotState.DELIVERY_ADDRESS_INPUT);
        return sendMessage;
    }
}
