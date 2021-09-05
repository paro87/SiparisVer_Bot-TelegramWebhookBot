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
public class PersonalInfoHandler implements MenuHandler {

    private final UserService userService;
    private final ReplyMessageService messagesService;
    private final InlineButtons inlineButtons;

    public PersonalInfoHandler(UserService userService, ReplyMessageService messagesService, InlineButtons inlineButtons) {
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
        return BotState.PERSONAL_INFO.state;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        String chatId = inputMsg.getChatId().toString();
        String text = inputMsg.getText();
        String personalInfo = "";
        if (text.equals("Personal info")){
            Optional<User> userOptional = userService.getUser(userId);
            personalInfo = userOptional.map(User::toString).orElse("No user or personal info found");
            log.info("Retrieving personal info for userId: {}: {}",userId, personalInfo);
        } else if (text.startsWith("PERINF:")){
            text = text.replace("PERINF:","");
            String[] personalInfoInput = text.split(" ");
            String name = personalInfoInput[0];
            String surname = personalInfoInput[1];
            Optional<User> userOptional = userService.getUser(userId);
            userOptional.ifPresentOrElse(user -> {
                log.info("User with id {} exists, updating personal info to: {}", userId, name+" "+surname);
                user.setName(name);
                user.setSurname(surname);
                userService.saveUser(user);
            }, ()-> {
                User newUser = new User();
                newUser.setId(userId);
                newUser.setName(name);
                newUser.setSurname(surname);
                log.info("User with id {} does not exist, saving user with personal info: {}", userId, name+" "+surname);

                userService.saveUser(newUser);
            });
            userOptional = userService.getUser(userId);
            personalInfo = userOptional.map(user -> user.getName()+" "+user.getSurname()+" "+user.getDeliveryAddress())
                    .orElse("No user or personal info found");

        }
        log.info("processUsersInput: CurrentBotState: {}", userService.getUsersCurrentBotState(userId));

        SendMessage sendMessage = messagesService.getReplyMessage(chatId,"reply.personal_info");
        sendMessage.setText(sendMessage.getText()+" "+ personalInfo);
//        userService.setUsersCurrentBotState(userId, BotState.PERSONAL_INFO);
        sendMessage.setReplyMarkup(inlineButtons.getPersonalIngoInlineButtons());
        return sendMessage;
    }


}
