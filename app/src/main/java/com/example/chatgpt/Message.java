package com.example.chatgpt;

public class Message {
    public static String SENT_BY_ME = "Me";
    public static String SENT_BY_BOT = "Bot";
    String message , sendby;
    public Message(String message,String sendby){
        this.message = message;
        this.sendby = sendby;
    }

    public static String getSentByMe() {
        return SENT_BY_ME;
    }

    public static void setSentByMe(String sentByMe) {
        SENT_BY_ME = sentByMe;
    }

    public static String getSentByBot() {
        return SENT_BY_BOT;
    }

    public static void setSentByBot(String sentByBot) {
        SENT_BY_BOT = sentByBot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendby() {
        return sendby;
    }

    public void setSendby(String sendby) {
        this.sendby = sendby;
    }
}
