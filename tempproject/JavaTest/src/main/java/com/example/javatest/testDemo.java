package com.example.javatest;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suxiaohan on 2018/9/16.
 */


enum Week {
    Monday
}

public class testDemo {

    public static void main(String[] argv) {
//
//        int a = 100;
//        int b = 0;
//        boolean needDoagain = true;
//        while (needDoagain){
//            try {
//                int c = a/b;
//                needDoagain = false;
//                System.out.println(c);
//            }catch (ArithmeticException e){
//                b = 2;
//            }
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new testDemo().send();
//            }
//        }).start();

          int port = 6000;
          while (isLoclePortUsing(port)){
              port += 1;
          }
          System.out.println(port+" can use");
          send(port);
//        serveCenter serveCenter = new serveCenter();
//        serveruser user1 = new serveruser("lili");
//        serveCenter.adduser(user1);
//
//
//        serveCenter.sendMessage("hello dear user!!");


    }

    static void send(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.println("I send this message");
                System.out.println("--send--");
                Thread.sleep(1000);
                printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

        }
    }
    static boolean isLoclePortUsing(int port){
        boolean flag = false;
        try {
            InetAddress theAddress = InetAddress.getByName("192.168.1.101");

            Socket socket = new Socket(theAddress,port);
            flag = true;
        } catch (IOException e) {

        }
        return flag;

    }

}


interface observeroffer {
    void adduser(observeuser o);

    void removeuser(observeuser o);

    void notifyObserver();
}

interface observeuser {
    void updatamess(String mess);
}


class serveruser implements observeuser {
    private String name;

    public String getMessage() {
        return message;
    }

    private String message;

    public String getName() {
        return name;
    }

    public serveruser(String name) {
        this.name = name;
    }

    @Override
    public void updatamess(String mess) {
        System.out.println("I got message " + mess);
        this.message = mess;
    }
}


class serveCenter implements observeroffer {
    List<observeuser> userList;

    private String Message;

    public serveCenter() {
        userList = new ArrayList<observeuser>();
    }


    public void sendMessage(String message) {
        this.Message = message;
        notifyObserver();

    }

    @Override
    public void adduser(observeuser o) {
        userList.add(o);
    }

    @Override
    public void removeuser(observeuser o) {
        if (!userList.isEmpty()) {
            userList.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        System.out.println("I send message!!");
        for (observeuser user : userList) {
            user.updatamess(Message);
        }
    }
}

class Client {
    public static final int PORT = 12345;
    public static final String IP_ADDR = "localhost";

    public static void main(String[] args) {
        System.out.println("客户端启用》》》》》》");
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n");
        while (true){
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR,PORT);
                socket.setSoTimeout(5000);
                //读取服务器端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                //向服务器端发送数据
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.print("请输入: \t");
                String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(str);

                String ret = input.readUTF();
                System.out.println("服务器端返回过来的是: " + ret);

                if (ret.endsWith("OK")) {
                    System.out.println("客户端将关闭连接");
                    Thread.sleep(500);
                    break;
                }
                out.close();
                input.close();


            } catch (IOException  | InterruptedException e) {
                e.printStackTrace();
            }finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}