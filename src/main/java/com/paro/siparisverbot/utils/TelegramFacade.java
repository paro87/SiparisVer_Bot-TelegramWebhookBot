package com.paro.siparisverbot.utils;

import com.paro.siparisverbot.handler.CallbackHandlers.CallbackQueryHandler;
import com.paro.siparisverbot.handler.InputMessageHandlers.InputMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TelegramFacade {
    private Map<String, InputMessageHandler> inputMessageHandlerMap;
    private Map<BotState, CallbackQueryHandler> callbackQueryHandlerMap;
    @Autowired
    public TelegramFacade(List<InputMessageHandler> inputMessageHandlers, List<CallbackQueryHandler> callbackQueryHandlers) {
        inputMessageHandlerMap = inputMessageHandlers.stream().collect(Collectors.toMap(InputMessageHandler::getHandlerName, Function.identity()));
        callbackQueryHandlerMap = callbackQueryHandlers.stream().collect(Collectors.toMap(CallbackQueryHandler::getHandlerName, Function.identity()));
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("CallbackQuery received from username: {}, userId: {} with data: {}",
                    callbackQuery.getFrom().getUserName(),
                    callbackQuery.getFrom().getId(),
                    callbackQuery.getData());
            return processCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("Input message received from User:{}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(),
                    message.getChatId(),
                    message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

//    @SneakyThrows
    private SendMessage handleInputMessage(Message message) {
        final String chatId = message.getChatId().toString();
        final int userId = message.getFrom().getId();
        String inputMsg = message.getText();
        log.info("callbackQuery received from userId: {}, chatId:{}, and message: {}", userId, chatId, inputMsg);
        InputMessageHandler inputMessageHandler = inputMessageHandlerMap.get(inputMsg);
        if (inputMessageHandler ==null){
            if (inputMsg.startsWith("ORD:")){
                inputMessageHandler = inputMessageHandlerMap.get("Order input");
            }else if (inputMsg.startsWith("DELADD:")){
                inputMessageHandler = inputMessageHandlerMap.get("Delivery address input");
            }else if (inputMsg.startsWith("PERINF:")){
                inputMessageHandler = inputMessageHandlerMap.get("Personal info input");
            }else if (inputMsg.startsWith("MSG:")){
                inputMessageHandler = inputMessageHandlerMap.get("Contact us input");
            }else if (inputMsg.matches("\\d{2}.\\d{2}.\\d{4}")){
                inputMessageHandler = inputMessageHandlerMap.get("Date");
            }else
                inputMessageHandler = inputMessageHandlerMap.get("Wrong input");
        }
        log.info("InputMessage redirected to the handler: {}", inputMessageHandler.getHandlerName());
        return inputMessageHandler.handle(message);
    }
    private BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery){
        final String chatId = callbackQuery.getMessage().getChatId().toString();
        final int userId = callbackQuery.getFrom().getId();

        String data=callbackQuery.getData();
        String msg = callbackQuery.getMessage().getText();
        BotState state = valueOfState(callbackQuery.getData());
        log.info("CallbackQuery received from userId: {}, chatId:{}, data: {}, and message: {}", userId, chatId, data, msg);
        CallbackQueryHandler callbackQueryHandler = callbackQueryHandlerMap.get(state);
        if (callbackQueryHandler==null)
            callbackQueryHandler = callbackQueryHandlerMap.get(BotState.DATE);
        log.info("CallbackQuery redirected to the handler: {}", callbackQueryHandler.getHandlerName().state);
        return callbackQueryHandler.handle(callbackQuery);
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