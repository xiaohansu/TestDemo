package com.example.javatest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/9/16.
 */


enum Week{
Monday
}
public class testDemo {

    public static void main(String[] argv){

        int a = 100;
        int b = 0;
        boolean needDoagain = true;
        while (needDoagain){
            try {
                int c = a/b;
                needDoagain = false;
                System.out.println(c);
            }catch (ArithmeticException e){
                b = 2;
            }
        }




//        serveCenter serveCenter = new serveCenter();
//        serveruser user1 = new serveruser("lili");
//        serveCenter.adduser(user1);
//
//
//        serveCenter.sendMessage("hello dear user!!");


    }
}


interface observeroffer{
    void adduser(observeuser o);
    void removeuser(observeuser o);
    void notifyObserver();
}
interface observeuser{
    void updatamess(String mess);
}



class serveruser implements observeuser{
    private String name;

    public String getMessage() {
        return message;
    }

    private String message;

    public String getName() {
        return name;
    }

    public serveruser(String name){
        this.name = name;
    }

    @Override
    public void updatamess(String mess) {
        System.out.println("I got message "+mess);
        this.message = mess;
    }
}


class serveCenter implements observeroffer{
    List<observeuser> userList ;

    private String Message;
    public serveCenter(){
        userList = new ArrayList<observeuser>();
    }



    public void sendMessage(String message){
        this.Message = message;
        notifyObserver();

    }
    @Override
    public void adduser(observeuser o) {
        userList.add(o);
    }

    @Override
    public void removeuser(observeuser o) {
        if (!userList.isEmpty()){
            userList.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        System.out.println("I send message!!");
        for(observeuser user : userList){
            user.updatamess(Message);
        }
    }
}