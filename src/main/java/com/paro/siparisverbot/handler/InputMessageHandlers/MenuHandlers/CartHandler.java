package com.paro.siparisverbot.handler.InputMessageHandlers.MenuHandlers;

import com.paro.siparisverbot.cache.CartCache;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Slf4j
@Component
public class CartHandler implements MenuHandler {

    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final InlineButtons inlineButtons;
    private final CartCache cartCache;

    public CartHandler(UserService userService, InlineButtons inlineButtons, CartCache cartCache, ReplyMessageService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.inlineButtons = inlineButtons;
        this.cartCache = cartCache;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public String getHandlerName() {
        return BotState.CART.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        //Inputmessages come has UserId coming from Users
        //Messages inside CallBackQueries have botId as a userId
        if (inputMsg.getFrom().getIsBot()){
            int botId = inputMsg.getFrom().getId();
            userId = Integer.parseInt(inputMsg.getChatId().toString());
        }
        String chatId = inputMsg.getChatId().toString();
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.cart");
        List<String> orderList = cartCache.getOrdersAsList(userId);
        System.out.println(orderList.toString());
        sendMessage.setText(sendMessage.getText()+" "+orderList.toString());
        userService.setUsersCurrentBotState(userId, BotState.CART);
        sendMessage.setReplyMarkup(inlineButtons.getCartInlineButtons());
        return sendMessage;
    }


}
