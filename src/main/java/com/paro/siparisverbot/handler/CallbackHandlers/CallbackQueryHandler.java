package com.paro.siparisverbot.handler.CallbackHandlers;

import com.paro.siparisverbot.utils.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackQueryHandler {
    SendMessage handle(CallbackQuery callbackQuery);
    BotState getHandlerName();
}
