package com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers;

import com.paro.siparisverbot.model.User;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Slf4j
@Component
public class DeliveryAddressHandler implements MenuHandler {

    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final InlineButtons inlineButtons;

    public DeliveryAddressHandler(UserService userService, ReplyMessageService messagesService, InlineButtons inlineButtons) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.inlineButtons = inlineButtons;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.DELIVERY_ADDRESS.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        String text = inputMsg.getText();
        String deliveryAddress = "";
        if (text.equals("Delivery address")){
            Optional<User> userOptional = userService.getUser(userId);
            deliveryAddress = userOptional.map(user -> user.getDeliveryAddress()).orElse("No user or delivery address found");
            log.info("Retrieving delivery address for userId: {}: {}",userId, deliveryAddress);
        } else if (text.startsWith("DELADD:")){
            String finalText = text.replace("DELADD:","");
            Optional<User> userOptional = userService.getUser(userId);

            userOptional.ifPresentOrElse(user -> {
                log.info("User with id {} exists, updating delivery address to: {}", userId, finalText);
                user.setDeliveryAddress(finalText);
                userService.saveUser(user);
            }, ()-> {
                User newUser = new User();
                newUser.setId(userId);
                newUser.setDeliveryAddress(finalText);
                log.info("User with id {} does not exist, saving user with delivery address: {}", userId, finalText);

                userService.saveUser(newUser);
            });
            userOptional = userService.getUser(userId);
            deliveryAddress = userOptional.map(user -> user.getName()+" "+user.getSurname()+" "+user.getDeliveryAddress())
                    .orElse("No user or personal info found");

        }
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));

        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.delivery_address");
        sendMessage.setText(sendMessage.getText()+" "+ deliveryAddress);
//        userService.setUsersCurrentBotState(userId, BotState.DELIVERY_ADDRESS);
        sendMessage.setReplyMarkup(inlineButtons.getDeliveryAddressInlineButtons());
        return sendMessage;
    }


}
