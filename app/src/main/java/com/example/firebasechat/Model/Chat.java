package com.example.firebasechat.Model;

public class Chat {

    //在此宣告的變數名稱必須與建構Database的子目錄名稱相同(大小寫一致),否則取不出資料
    private String sender;
    private String receiver;
    private String Message;

    public Chat(String sender, String receiver, String Message) {
        this.sender = sender;
        this.receiver = receiver;
        this.Message = Message;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}
