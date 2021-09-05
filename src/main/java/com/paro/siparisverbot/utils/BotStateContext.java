/*
package com.paro.siparisverbot.utils;

import com.paro.siparisverbot.handler.CallbackHandlers.CallbackQueryHandler;
import com.paro.siparisverbot.handler.InputMessageHandlers.InputMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BotStateContext {
    private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
    private final Map<BotState, CallbackQueryHandler> callbackQueryHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers, List<CallbackQueryHandler> callbackQueryHandlers) {
        messageHandlers.forEach(handler -> this.messageHandlers.put(BotState.valueOf(handler.getHandlerName()), handler));
        callbackQueryHandlers.forEach(handler -> this.callbackQueryHandlers.put(handler.getHandlerName(), handler));
    }

    public SendMessage processInputMessage(BotState currentState, Message message) {
        log.info("processInputMessage: {}, {}", currentState, message);
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        log.info("currentMessageHandler: {}", currentMessageHandler.getHandlerName());
        return currentMessageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        return messageHandlers.get(currentState);
    }

    public SendMessage processCallbackQueryMessage(BotState currentState, CallbackQuery callbackQuery) {
        CallbackQueryHandler callbackQueryHandler = findCallbackQueryHandler(currentState);
        return callbackQueryHandler.handle(callbackQuery);
    }

    private CallbackQueryHandler findCallbackQueryHandler(BotState currentState) {
        return callbackQueryHandlers.get(currentState);
    }

}
*/
