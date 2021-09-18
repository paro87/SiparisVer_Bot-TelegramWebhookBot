package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.cache.CartCache;
import com.paro.siparisverbot.repository.OrderRepository;
import com.paro.siparisverbot.service.MainMenuService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.time.LocalDate;

@Slf4j
@Component
public class PaymentHandler implements CallbackQueryHandler {
    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final MainMenuService mainMenuService;
    private final InlineButtons inlineButtons;
    private final CartCache cartCache;
    private final OrderRepository orderRepository;

    public PaymentHandler(UserService userService, ReplyMessageService messagesService, MainMenuService mainMenuService, InlineButtons inlineButtons, CartCache cartCache, OrderRepository orderRepository) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
        this.inlineButtons = inlineButtons;
        this.cartCache = cartCache;
        this.orderRepository = orderRepository;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PAYMENT;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage callbackAnswer = mainMenuService.getMainMenuMessage(chatId);
        processOrder(userId);
        SendMessage sendMessage = messagesService.getReplyMessage(callbackAnswer,"reply.payment");
        // TODO: Setting delivery date.
        sendMessage.setText(sendMessage.getText()+ LocalDate.now().plusDays(1));
        userService.setUsersCurrentBotState(userId,BotState.PAYMENT);
        sendMessage.setReplyMarkup(inlineButtons.getReceiptDownloadInlineButton());
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }

    @Transactional
    public void processOrder(int userId){
        orderRepository.save(cartCache.getOrderByUserId(userId));
        log.info("Order for userId {} saved in database", userId);
        cartCache.deleteOrder(userId);
        log.info("Order for userId {} deleted from the cache", userId);
    }
}
