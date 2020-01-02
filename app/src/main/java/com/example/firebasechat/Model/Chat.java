package com.example.firebasechat.Model;

public class Chat {

    //在此宣告的變數名稱必須與建構Database的標籤名稱相同(大小寫一致),否則取不出資料
    private String sender;
    private String receiver;
    private String Message;
    private boolean seen;
    private String uptime;

    public Chat(String sender, String receiver, String Message, boolean seen, String uptime) {
        this.sender = sender;
        this.receiver = receiver;
        this.Message = Message;
        this.seen = seen;
        this.uptime = uptime;
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

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }
}
