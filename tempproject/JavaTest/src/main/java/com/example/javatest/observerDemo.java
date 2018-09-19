package com.example.javatest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/9/16.
 */

public class observerDemo {

    public static void main(String[] argv){

        WechatServer server = new WechatServer();

        User Alex = new User("Alex");
        server.registerObserver(Alex);
        server.setInfomation("hello dear user!");

    }
}

/***
 * 抽象被观察者接口
 * 声明了添加、删除、通知观察者方法
 * @author jstao
 *
 */
 interface Observerable{
      void registerObserver(Observer o);
      void removeObserver(Observer o);
      void notifyObserver();
}
/***
 * 抽象观察者
 * 定义了一个update()方法，当被观察者调用notifyObservers()方法时，观察者的update()方法会被回调。
 * @author jstao
 *
 */
interface Observer {
     void update(String message);
}

/*
* 发布者
* */

class WechatServer implements Observerable {

    //注意到这个List集合的泛型参数为Observer接口，设计原则：面向接口编程而不是面向实现编程
    private List<Observer> list;//用户列表
    private String message;

    public WechatServer() {
        list = new ArrayList<Observer>();
    }

    @Override
    public void registerObserver(Observer o) {

        list.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if (!list.isEmpty())
            list.remove(o);
    }

    //遍历
    @Override
    public void notifyObserver() {
        for (int i = 0; i < list.size(); i++) {
            Observer oserver = list.get(i);
            oserver.update(message);
        }
    }

    public void setInfomation(String s) {
        this.message = s;
        System.out.println("微信服务更新消息： " + s);
        //消息更新，通知所有观察者
        notifyObserver();
    }
}

    /*
    *
    * 接收
    * */

    class User implements Observer {

        private String name;
        private String message;

        public User(String name) {
            this.name = name;
        }

        @Override
        public void update(String message) {
            this.message = message;
            read();
        }

        public void read() {
            System.out.println(name + " 收到推送消息： " + message);
        }

    }


