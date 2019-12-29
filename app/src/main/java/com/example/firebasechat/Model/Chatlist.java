package com.example.firebasechat.Model;

public class Chatlist {
    public String chatwith;
    public String myid;

    public Chatlist(String chatwith, String myid) {
        this.chatwith = chatwith;
        this.myid = myid;
    }

    public Chatlist() {
    }

    public String getChatwith() {
        return chatwith;
    }

    public void setChatwith(String chatwith) {
        this.chatwith = chatwith;
    }

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }
}
