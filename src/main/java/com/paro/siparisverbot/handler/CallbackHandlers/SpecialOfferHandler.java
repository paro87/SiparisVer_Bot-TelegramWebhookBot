package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.SiparisVerBot;
import com.paro.siparisverbot.service.PictureService;
import com.paro.siparisverbot.service.ReplyMessageService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
@Slf4j
public class SpecialOfferHandler implements CallbackQueryHandler{
    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final SiparisVerBot siparisVerBot;
    private final PictureService pictureService;

    @Autowired
    public SpecialOfferHandler(UserService userService, ReplyMessageService messagesService, @Lazy SiparisVerBot siparisVerBot, PictureService pictureService) {
        this.userService = userService;
        this.messagesService = messagesService;
        this.siparisVerBot = siparisVerBot;
        this.pictureService = pictureService;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SPECIAL_OFFER;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        BotState botState = userService.getUsersCurrentBotState(userId);
        log.info("Bot state before selection/input: {}", botState.state);
        Integer integer = callbackQuery.getMessage().getDate();
        Instant instant = Instant.ofEpochSecond(integer);
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        Date date = Date.valueOf(localDate);
        String data=callbackQuery.getData();
        BotState state = valueOfState(data);
        List<String> pictureList = pictureService.lookForDate(botState.state, date);

        try {
            for (String picture : pictureList) {
                log.info("SpecialOfferHandler: botstate:{}", botState.state);
                siparisVerBot.sendPhoto(chatId, messagesService.getReplyText("reply.special_offer"), "static/images/"+botState.state+"/"+picture+".png");
//                userDataCache.setUsersCurrentBotState(userId,state);
                log.info("Bot state after selection/input: {}", userService.getUsersCurrentBotState(userId));
            }

    
        } catch (FileNotFoundException | TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BotState valueOfState(String label) {
        for (BotState e : BotState.values()) {
            if (e.state.equals(label)) {
                return e;
            }
        }
        return null;
    }

}
