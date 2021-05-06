package chmnu.vest.tgbot;

import chmnu.vest.entities.Category;
import chmnu.vest.parser.PagesParser;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TGBot extends TelegramLongPollingBot {

    private static final int RECONNECT_PAUSE = 5000;

    private final String userName;
    private final String token;

    private final PagesParser parser = new PagesParser("https://www.work.ua");

    public TGBot(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                Long chatId = update.getMessage().getChatId();

                String customMessage = "";
                SendMessage message = new SendMessage();
                message.setChatId(chatId);

                switch (update.getMessage().getText()) {
                    case "/start":
                        customMessage = "Привіт, чувак!" + "\n" +
                                "Хочеш знайти роботу? Без питань!" + "\n" +
                                "Просто введи 'Хочу працювати!'" + "\n" +
                                "Також бот підтримує наступні команди:" + "\n" +
                                "Команди - відображає можливі команди" + "\n" +
                                "Допомога - відображає корисні посилання" + "\n";
                        break;

                    case "Хочу працювати!":
                        try {
                            customMessage = "Готово!";
                            execute(sendInlineKeyBoard(chatId, parser.getCategories()));
                        } catch (IOException | TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "Команди":
                        customMessage = "Бот підтримує наступні команди:" + "\n" +
                                "Команди - відображає можливі команди" + "\n" +
                                "Допомога - відображає корисні посилання" + "\n";
                        break;

                    case "Допомога":
                        customMessage = "Автори:" + "\n" +
                                "@verylonganduselessnicknameforme " + "\n" +
                                "@Ti_sher" + "\n" +
                                "@Veymo" + "\n" +
                                "Кучеренко Егор, який не має посилання :(" + "\n";
                        break;

                    default:
                        customMessage = "Я вас не зрозумів :( Використайте команду 'Команди' для довідки!";
                        break;

                }
                message.setText(customMessage);
            }
        }
        else if (update.hasCallbackQuery()){
            try {
                execute(new SendMessage().setText(parser.getVacancy(update.getCallbackQuery().getData()))
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                execute(new AnswerCallbackQuery()
                        .setCallbackQueryId(update.getCallbackQuery().getId())
                        .setShowAlert(false));
            } catch (TelegramApiException | IOException e) {
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

    private static SendMessage sendInlineKeyBoard(long chatId, List<Category> categories) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        categories.forEach(cat -> {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(cat.getName());
            inlineKeyboardButton.setCallbackData(cat.getUrl());
            row.add(inlineKeyboardButton);
            rowList.add(row);
        });

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        return new SendMessage().setChatId(chatId).setText("Поточні категорії").setReplyMarkup(inlineKeyboardMarkup);
    }

}
