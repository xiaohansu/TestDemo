package com.example.javatest;

/**
 * Created by suxiaohan on 2018/9/16.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;

import javax.sound.midi.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MusicTest  implements ControllerEventListener{

    static JFrame f = new JFrame("My First Music Video");
    static MyDrawPanel ml;

    public static void main(String[] argv){
        MusicTest musicTest = new MusicTest();
//        musicTest.play();
    musicTest.GuiDemo2();
    }
    public void GuiDemo(){
        JFrame frame = new JFrame();

        JButton N = new JButton("Click This");
        JButton S = new JButton("Click This");
        JButton E = new JButton("Click This");
        JButton W = new JButton("Click This");
        JButton C = new JButton("Click This");

        frame.getContentPane().add(BorderLayout.NORTH,N);
        frame.getContentPane().add(BorderLayout.SOUTH,S);
        frame.getContentPane().add(BorderLayout.EAST,E);
        frame.getContentPane().add(BorderLayout.WEST,W);
        frame.getContentPane().add(BorderLayout.CENTER,C);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
    }

    public void GuiDemo2(){
        JFrame frame = new JFrame();
        Panel panel = new Panel();
        JButton bt1 = new JButton("click me !!");
        JButton bt2 = new JButton("click me !!");
        panel.setBackground(Color.black);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(BorderLayout.EAST,panel);
        panel.add(bt1);
        panel.add(bt2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        frame.setVisible(true);






    }


    public void setUpGui(){
        ml = new MyDrawPanel();
        f.setContentPane(ml);
        f.setBounds(30,30,300,300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public  void play(){
        setUpGui();
        try{
            Sequencer player = MidiSystem.getSequencer();//播放器
            player.open();

            int[] eventsIWant = {127};
            player.addControllerEventListener(ml,eventsIWant);

            Sequence seq = new Sequence(Sequence.PPQ,4);//CD
            Track track = seq.createTrack();//旋律/单曲


            for (int i = 5;i<80;i+=4){
                track.add(makeEvent(144,1,i,100,i));
                track.add(makeEvent(176,1,127,0,i));
                track.add(makeEvent(128,1,i,100,i+2));

            }
            player.setSequence(seq);
            player.setTempoInBPM(220);
            player.start();


        }catch (MidiUnavailableException e){
            e.printStackTrace();
        }catch (InvalidMidiDataException e){
            e.printStackTrace();
        }


    }


    public static MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public void controlChange(ShortMessage shortMessage) {
        System.out.println("la");
    }
    class MyDrawPanel extends JPanel implements ControllerEventListener{
        boolean msg = false;
        @Override
        public void controlChange(ShortMessage shortMessage) {
            msg = true;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            if (msg){
                Graphics2D g2 = (Graphics2D) graphics;

                int r = (int)(Math.random()*250);
                int gr = (int)(Math.random()*250);
                int b = (int)(Math.random()*250);
                g2.setColor(new Color(r,gr,b));

                int ht = (int)((Math.random()* 120) +10);
                int width = (int)((Math.random()* 120) +10);
                int x = (int)((Math.random()* 40) +10);
                int y = (int)((Math.random()* 40) +10);
                g2.fillRect(x,y,width,ht);
                msg = false;

            }
        }
    }
}
