package ca.ualberta.cs.lonelytwitter;

import java.util.Date;

public class Tweet {
    String message;
    Date date;
    Tweet(){
    }
    Tweet(String message){
        this.message = message;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    @Override
    public String toString(){
        return message;
    }
}
