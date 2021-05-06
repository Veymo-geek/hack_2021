package chmnu.vest.tgbot;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class TGBot extends TelegramLongPollingBot {

    final int RECONNECT_PAUSE =5000;

    private final String userName;
    private final String token;

    public TGBot(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();

        if (inputText.startsWith("/hello")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Hey, dude. It`s me - JobsterBot :)");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            System.out.println("TG API started");
        } catch (TelegramApiRequestException e) {
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}
