package game;

import base.LongId;
import user.User;

public class ChatMessage {

    private LongId<User>    userId;
    private String  messageDate;
    private String  text;


    public ChatMessage(LongId<User> userId, String text){
        this.userId      = userId;
        this.text        = text;
        this.messageDate = setDateTime();
    }

    private String setDateTime(){
        long curTime = System.currentTimeMillis();
        //Date curDate = new Date(curTime);
        return         Long.toString(curTime); //new SimpleDateFormat("dd.MM.yyyy").format(curTime);
    }

    public String getStringMessage(){
        return messageDate+"   "+text;

    }



}
