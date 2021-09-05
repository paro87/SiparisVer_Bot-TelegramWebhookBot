/*
package com.paro.siparisverbot.handler.InputMessageHandlers;

import com.paro.siparisverbot.SiparisVerBot;
import com.paro.siparisverbot.cache.UserDataCache;
import com.paro.siparisverbot.service.PictureService;
import com.paro.siparisverbot.service.ReplyMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
//Deprecated
public class DateInputMessageHandler implements InputMessageHandler {
    private final ReplyMessageService replyMessageService;
    private final SiparisVerBot siparisVerBot;
    private final UserDataCache userDataCache;
    private final PictureService pictureService;
    private final ReplyMessageService messageService;

    @Autowired
    public DateInputMessageHandler(ReplyMessageService replyMessageService, @Lazy SiparisVerBot siparisVerBot, UserDataCache userDataCache, PictureService pictureService, ReplyMessageService messageService) {
        this.replyMessageService = replyMessageService;
        this.siparisVerBot = siparisVerBot;
        this.userDataCache = userDataCache;
        this.pictureService = pictureService;
        this.messageService = messageService;
    }


    @Override
    public SendMessage handle(Message message) {
        final String chatId = message.getChatId().toString();
        final int userId = message.getFrom().getId();
        String inputMessage = message.getText();
        LocalDate localDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        //Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputMessage);
        localDate = LocalDate.parse(inputMessage, formatter);
        log.info("Received date: {}", localDate);

        List<String> pictureList = pictureService.lookForDate(java.sql.Date.valueOf(localDate));
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
    public String getHandlerName() {
        return "";
        //return BotState.DATE.state;
    }
}
*/
