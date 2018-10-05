package com.example.suxiaohan.javastudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by suxiaohan on 2018/10/5.
 */

public class TestDemo {
    public static void main(String[] argv){
//        TargetObservable server = new TargetObservable();
//        TargetObserver user = new TargetObserver();
//        server.addObserver(user);
//        server.setMessage("I call all");

        User u1 = new User("lili",35,true) {
            @Override
            public void updateMessage(String message) {
                System.out.println("get message from server!"+message);
            }
        };

        User u2 = new User("wanghua",15,false) {
            @Override
            public void updateMessage(String message) {
                System.out.println("u2,get message from server!"+message);
            }
        };
        Server server = new Server();
        server.addUser(u1);
        server.addUser(u2);
        server.sendMessage("这是一条少儿不宜的新闻！");
        
    }
}
class TargetObservable extends Observable{
    private String message;

    public String getConent() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        // 被观察者数据发生变化时，通过以下两行代码通知所有的观察者
        this.setChanged();
        this.notifyObservers(message);
    }
}
class TargetObserver implements Observer {
    // 定义观察者名称
    private String name;

    public String getObserverName() {
        return name;
    }

    public void setObserverName(String observerName) {
        this.name = observerName;
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        //更新消息数据
        System.out.println(name + "收到了发生变化的数据内容是："
                + ((TargetObservable) arg0).getConent());
    }
    

}


interface MyObserver{
    void updateMessage(String message);
}

abstract class User implements MyObserver{
    private String name;
    private int age;
    private boolean woman;
    private int income;

    public User(String name, int age, boolean woman) {
        this.name = name;
        this.age = age;
        this.woman = woman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isWoman() {
        return woman;
    }

    public void setWoman(boolean woman) {
        this.woman = woman;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}



class Server{
    List<User> users = new ArrayList<>();
    void addUser(User user){
        users.add(user);
    }
    void sendMessage(String mess){
        for (User u : users){
            if (u.getAge()>18){
                u.updateMessage(mess);
            }else {
                u.updateMessage("你不能查看这条信息");
            }


        }

    }

}
    