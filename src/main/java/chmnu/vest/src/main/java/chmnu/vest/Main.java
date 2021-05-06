package chmnu.vest;

import chmnu.vest.tgbot.TGBot;
import org.telegram.telegrambots.ApiContextInitializer;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TGBot tgBot = new TGBot("jobster_chmnu_bot",
                "1837666964:AAGMG5ThcTTXeUZx4xjLCYCxggmZH74bwW0");
        tgBot.botConnect();
    }
}
