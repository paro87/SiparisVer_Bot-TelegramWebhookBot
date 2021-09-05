package com.paro.siparisverbot.config;

import com.paro.siparisverbot.SiparisVerBot;
import com.paro.siparisverbot.utils.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String botUsername;
    private String botToken;
    private String webhookPath;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(webhookPath).build();
    }

    @Bean
    public SiparisVerBot siparisVerBot(TelegramFacade telegramFacade) throws TelegramApiException {

        SiparisVerBot siparisVerBot = new SiparisVerBot(telegramFacade);
        siparisVerBot.setBotUsername(botUsername);
        siparisVerBot.setBotToken(botToken);
        siparisVerBot.setWebhook(setWebhookInstance());
//        siparisVerBot.setBotPath(webhookPath);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(siparisVerBot, setWebhookInstance());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return siparisVerBot;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
