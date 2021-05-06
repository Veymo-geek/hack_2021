package chmnu.vest.tgbot;

import org.telegram.telegrambots.ApiContextInitializer;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TGBot test_habr_bot = new TGBot("jobster_chmnu_bot",
                "1837666964:AAGMG5ThcTTXeUZx4xjLCYCxggmZH74bwW0");
        test_habr_bot.botConnect();
    }
}
