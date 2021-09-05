package com.paro.siparisverbot.utils;

import com.paro.siparisverbot.service.ReplyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Component
public class InlineButtons {

//    @Value("#{${storeList}}")
    private final Map<String, String> storeList ;
    private final ReplyMessageService messageService;

    public InlineButtons(@Value("#{${storeList}}") Map<String, String> storeList, ReplyMessageService messageService){
        this.storeList = storeList;
        this.messageService = messageService;
    }


    public InlineKeyboardMarkup getStoreInlineButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        InlineButtons inlineButtons = new InlineButtons(storeList);
        storeList.forEach((key,value)->{
            InlineKeyboardButton button = new InlineKeyboardButton(value);
            button.setCallbackData(key);
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            keyboardButtonsRow.add(button);
            rowList.add(keyboardButtonsRow);
        });
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getOfferInlineButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton buttonDailyDiscount = new InlineKeyboardButton(messageService.getReplyText("button.specialOfferOfTheDay"));
        InlineKeyboardButton buttonProducts = new InlineKeyboardButton(messageService.getReplyText("button.selectAnotherDay"));

        buttonDailyDiscount.setCallbackData("Special_offer");
        buttonProducts.setCallbackData("Another_Day_special_offer");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonDailyDiscount);
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonProducts);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getDateInlineButtons(){
        LocalDate today = LocalDate.now(ZoneId.systemDefault());

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            String day = today.getDayOfWeek().plus(i).getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("tr"));
            InlineKeyboardButton dayNameButton = new InlineKeyboardButton(day);
            dayNameButton.setCallbackData(day);
            keyboardButtonsRow1.add(dayNameButton);

            LocalDate localDate = today.plusDays(i);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String date = localDate.format(formatter);
            InlineKeyboardButton dateButton = new InlineKeyboardButton(String.valueOf(today.plusDays(i).getDayOfMonth()));
            dateButton.setCallbackData(date);
            keyboardButtonsRow2.add(dateButton);
        }


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getReceiptDownloadInlineButton(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton paymentButton = new InlineKeyboardButton(messageService.getReplyText("reply.download_receipt"));
        paymentButton.setCallbackData("Download receipt");
        keyboardButtonsRow.add(paymentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getCartInlineButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton paymentButton = new InlineKeyboardButton(messageService.getReplyText("button.order_complete"));
        paymentButton.setCallbackData("Payment");
        keyboardButtonsRow.add(paymentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getDeliveryAddressInlineButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton paymentButton = new InlineKeyboardButton(messageService.getReplyText("button.update_delivery_address"));
        paymentButton.setCallbackData("Change delivery address");
        keyboardButtonsRow.add(paymentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getPersonalIngoInlineButtons(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton paymentButton = new InlineKeyboardButton(messageService.getReplyText("button.update_personal_info"));
        paymentButton.setCallbackData("Update personal info");
        keyboardButtonsRow.add(paymentButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getOrderInlineButtons(){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        InlineKeyboardButton continueButton = new InlineKeyboardButton(messageService.getReplyText("button.continue"));
        continueButton.setCallbackData("Continue");
        InlineKeyboardButton finishButton = new InlineKeyboardButton(messageService.getReplyText("button.finish"));
        finishButton.setCallbackData("Finish");
        keyboardButtonsRow.add(continueButton);
        keyboardButtonsRow.add(finishButton);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
