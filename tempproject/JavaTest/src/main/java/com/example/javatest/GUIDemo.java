package com.example.javatest;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Created by suxiaohan on 2018/9/16.
 */

public class GUIDemo{

    private serveCenter serveCenter;
    private int x = 70;
    private int y = 70;

    public static void main(String[] argv){
        GUIDemo demo = new GUIDemo();
        demo.playAnimation();


    }
    public void playAnimation(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyDrawPanel panel = new MyDrawPanel();
        frame.getContentPane().add(panel);

        frame.setSize(500,500);
        frame.setVisible(true);
        for (int i = 0;i<150;i++){
            x++;
            y++;
            panel.repaint();
            try {
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }

    }
    class MyDrawPanel extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.green);
            g.fillOval(x,y,40,40);
        }
    }


    private void initUI(){


        serveCenter = new serveCenter();
        serveruser user1 = new serveruser("lili");
        serveCenter.adduser(user1);


        JFrame frame = new JFrame();

        JButton button = new JButton("click me");
        button.addActionListener(new buttonListener());

        frame.getContentPane().add(button);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    class buttonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            serveCenter.sendMessage("hihi");
        }
    }
}
