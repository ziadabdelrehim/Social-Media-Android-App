package com.example.socialnetwork;

import java.util.ArrayList;

public class Message
{
    public static ArrayList<Message> MessagesList=new ArrayList<>();

    private int MessageID;
    private String MessageText;
    private int SrcID;
    private int DstID;

    public Message(int messageID,String messageText,int srcID,int dstID)
    {
        this.MessageID=messageID;
        this.MessageText=messageText;
        this.SrcID=srcID;
        this.DstID=dstID;
    }

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public int getSrcID() {
        return SrcID;
    }

    public void setSrcID(int srcID) {
        SrcID = srcID;
    }

    public int getDstID() {
        return DstID;
    }

    public void setDstID(int dstID) {
        DstID = dstID;
    }
}
