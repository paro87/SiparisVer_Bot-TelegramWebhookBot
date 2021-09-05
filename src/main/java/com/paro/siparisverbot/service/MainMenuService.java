package com.paro.siparisverbot.service;

import com.paro.siparisverbot.utils.Emojis;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

    private final ReplyMessageService messageService;

    public MainMenuService(ReplyMessageService messageService) {
        this.messageService = messageService;
    }

    public SendMessage getMainMenuMessage(final String chatId) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        final SendMessage mainMenuMessage = createMessageWithKeyboard(chatId, replyKeyboardMarkup);

        return mainMenuMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        String stores = "\uD83C\uDFEA";
        //row1.add(new KeyboardButton(messageService.getReplyText("menuButton.stores2")));
        row1.add(new KeyboardButton(messageService.getReplyText("menuButton.stores", Emojis.CONVENIENCE_STORE)));
        row1.add(new KeyboardButton(messageService.getReplyText("menuButton.giveOrder", Emojis.MOTORBIKE)));
        row2.add(new KeyboardButton(messageService.getReplyText("menuButton.cart", Emojis.SHOPPING_TROLLEY)));
        row2.add(new KeyboardButton(messageService.getReplyText("menuButton.deliveryAddress", Emojis.HOUSE_WITH_GARDEN)));
        row3.add(new KeyboardButton(messageService.getReplyText("menuButton.personalInfo", Emojis.BUST_IN_SILHOUETTE)));
        row3.add(new KeyboardButton(messageService.getReplyText("menuButton.contactUs", Emojis.INCOMING_ENVELOPE)));
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final String chatId, final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}
