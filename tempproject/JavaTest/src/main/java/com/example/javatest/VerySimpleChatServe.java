package com.example.javatest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdk.nashorn.internal.runtime.arrays.IteratorAction;

/**
 * Created by suxiaohan on 2018/9/24.
 */

public class VerySimpleChatServe {
    List clientOutputStreams;
    public class clientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public clientHandler(Socket socket) {
            this.socket = socket;
            try {
                InputStreamReader isreader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isreader);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read:" + message);
                    tellEveryone(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void go() {
        clientOutputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread t = new Thread(new clientHandler(socket));
                t.start();
                System.out.println("get a connection");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        new VerySimpleChatServe().go();
    }
}
