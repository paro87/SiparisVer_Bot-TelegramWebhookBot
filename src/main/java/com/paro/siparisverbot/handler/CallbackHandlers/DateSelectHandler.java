package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.SiparisVerBot;
import com.paro.siparisverbot.service.PictureService;
import com.paro.siparisverbot.service.UserService;
import com.paro.siparisverbot.utils.BotState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class DateSelectHandler implements CallbackQueryHandler {
    private final SiparisVerBot siparisVerBot;
    private final UserService userService;
    private final PictureService pictureService;

    @Autowired
    public DateSelectHandler(@Lazy SiparisVerBot siparisVerBot, UserService userService, PictureService pictureService) {
        this.siparisVerBot = siparisVerBot;
        this.userService = userService;
        this.pictureService = pictureService;
    }



    public SendMessage handle(Message message) {
        final String chatId = message.getChatId().toString();
        final int userId = message.getFrom().getId();
        BotState botState = userService.getUsersCurrentBotState(userId);
        log.info("Bot state before selection/input: {}", botState);
        String inputMessage = message.getText();
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputMessage);
        localDate = LocalDate.parse(inputMessage, formatter);
        log.info("Received date: {}", localDate);

        List<String> pictureList = pictureService.lookForDate(botState.state, java.sql.Date.valueOf(localDate));
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        List<InputMedia> mediaList = new ArrayList<>();
        try {
//            for (String picture : pictureList) {
//
//                siparisVerBot.sendPhoto(chatId, messageService.getReplyText("reply.special_offer"), "static/images/A101/"+picture+".png");
//
//            }

            for (String picture : pictureList) {
                InputMedia photo = new InputMediaPhoto();
                File image = ResourceUtils.getFile("classpath:" + "static/images/A101/"+picture+".png");
                photo.setMedia(image, picture);
                mediaList.add(photo);
            }


            sendMediaGroup.setChatId(chatId);
            sendMediaGroup.setMedias(mediaList);
            siparisVerBot.execute(sendMediaGroup);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public SendMessage handle(CallbackQuery callbackQuery) {
        String date = callbackQuery.getData();
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();
        BotState botState = userService.getUsersCurrentBotState(userId);
        log.info("Bot state before selection/input: {}", botState);
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputMessage);
        localDate = LocalDate.parse(date, formatter);
        log.info("Received date: {}", localDate);
        List<String> pictureList = new ArrayList<>();
        log.info("DateSelectHandler: botstate:{}", botState.state);
        if (botState ==BotState.A101_SELECTED||botState==BotState.BIM_SELECTED||botState == BotState.SOK_SELECTED)
            pictureList = pictureService.lookForDate(botState.state, java.sql.Date.valueOf(localDate));
        else
            pictureList = pictureService.lookForDate(java.sql.Date.valueOf(localDate));
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        List<InputMedia> mediaList = new ArrayList<>();
        try {
//            for (String picture : pictureList) {
//
//                siparisVerBot.sendPhoto(chatId, messageService.getReplyText("reply.special_offer"), "static/images/A101/"+picture+".png");
//
//            }

            for (String picture : pictureList) {
                InputMedia photo = new InputMediaPhoto();
                File image = ResourceUtils.getFile("classpath:" + "static/images/"+botState.state+"/"+picture+".png");
                photo.setMedia(image, picture);
                mediaList.add(photo);
            }


            sendMediaGroup.setChatId(chatId);
            sendMediaGroup.setMedias(mediaList);
            siparisVerBot.execute(sendMediaGroup);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.DATE;
    }


}
