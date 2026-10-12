package org.fzl.ebankbot.telegram;

import org.fzl.ebankbot.agents.EBankAIAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;

import java.util.List;

@Component
public class TelegramBot implements SpringLongPollingBot, LongPollingUpdateConsumer {

    private final EBankAIAgent agent;
    private final String botToken;
    private final TelegramClient telegramClient;

    public TelegramBot(@Value("${telegram.token}") String botToken,
                       EBankAIAgent agent) {
        this.botToken = botToken;
        this.agent = agent;
        this.telegramClient = new OkHttpTelegramClient(botToken);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(List<Update> updates) {
        System.out.println(">>> Message reçu de Telegram : " + updates);
        for (Update update : updates) {
            handleUpdate(update);
        }
    }

    private void handleUpdate(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String query = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        String response = agent.chat(query);

        if (response.length() > 4000) {
            response = response.substring(0, 4000) + "...";
        }

        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(response)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}