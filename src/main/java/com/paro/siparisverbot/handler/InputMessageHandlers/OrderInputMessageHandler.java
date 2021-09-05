package com.paro.siparisverbot.handler.InputMessageHandlers;

import com.paro.siparisverbot.cache.CartCache;
import com.paro.siparisverbot.model.Product;
import com.paro.siparisverbot.service.ProductService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class OrderInputMessageHandler implements InputMessageHandler{
    private final UserService userService;
    private final InlineButtons inlineButtons;
    private final CartCache cartCache;
    private final ProductService productService;
    private final ReplyMessageService messageService;

    @Autowired
    public OrderInputMessageHandler(UserService userService, InlineButtons inlineButtons, CartCache cartCache, ProductService productService, ReplyMessageService messageService) {
        this.userService = userService;
        this.inlineButtons = inlineButtons;
        this.cartCache = cartCache;
        this.productService = productService;
        this.messageService = messageService;
    }

    @Override
    public String getHandlerName() {
        return BotState.ORDER_INPUT.state;
    }

    @Override
    public SendMessage handle(Message message) {
        int userId = message.getFrom().getId();
        String chatId = message.getChatId().toString();
        String text = message.getText();
        System.out.println(text);
        String[] inputProduct = message.getText().replace("ORD:","").split(",");
        String productId = inputProduct[0];
        int quantity = Integer.parseInt(inputProduct[1].trim());
        Product product = productService.getProductByID(productId);
        cartCache.saveItem(userId, product, quantity);
//        if (text.startsWith("ORD:")){
//
//        }





        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));
//        SendMessage sendMessage = replyMessageService.getReplyMessage(chatId,"reply.selectMarket");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(messageService.getReplyText("reply.addedToCart"));
        sendMessage.setText(sendMessage.getText()+inputProduct[0]+": "+quantity);
        sendMessage.setReplyMarkup(inlineButtons.getOrderInlineButtons());
        userService.setUsersCurrentBotState(userId, BotState.ORDER_INPUT);
        return sendMessage;
    }



}
