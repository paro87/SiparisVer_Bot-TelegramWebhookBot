package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.service.MainMenuService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.utils.BotState;
import com.paro.siparisverbot.utils.InlineButtons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class BIMHandler implements CallbackQueryHandler {
    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final MainMenuService mainMenuService;
    private final InlineButtons inlineButtons;

    public BIMHandler(UserService userService, ReplyMessageService messagesService, MainMenuService mainMenuService, InlineButtons inlineButtons) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.mainMenuService = mainMenuService;
        this.inlineButtons = inlineButtons;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        return processCallbackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.BIM_SELECTED;
    }

    private SendMessage processCallbackQuery(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        log.info("Bot state before selection/input: {}", userService.getUsersCurrentBotState(userId));
        SendMessage callbackAnswer = mainMenuService.getMainMenuMessage(chatId);
        SendMessage sendMessage = messagesService.getReplyMessage(callbackAnswer,"reply.selectProduct");
        userService.setUsersCurrentBotState(userId,BotState.BIM_SELECTED);
        sendMessage.setReplyMarkup(inlineButtons.getOfferInlineButtons());
        log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
        return sendMessage;
    }

    /*private InlineKeyboardMarkup getInlineMarketButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonDailyDiscount = new InlineKeyboardButton("Günün indirimleri");
        InlineKeyboardButton buttonProducts = new InlineKeyboardButton("Ürünler");

        buttonDailyDiscount.setCallbackData("DailyDiscount");
        buttonProducts.setCallbackData("Products");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonDailyDiscount);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonProducts);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }*/


}
