package com.example.javatest;

interface OutInterface{
    public void f();
}
public class myClass{

    public static void main(String arge[]){
//        OuterClass2 out = new OuterClass2();
//        OutInterface outInterface = out.doit();
//        outInterface.f();
        

        new mythread().start();
    }

}

class mythread extends Thread{
    public void run(){
        System.out.println("dd");
    }
}
class OuterClass2{
    public OutInterface doit(){
        return new interClass("tset");
    }


    private class interClass implements OutInterface{
        interClass(String s){
            System.out.println(s);
        }
        public void f(){
            System.out.println("访问内部类中的方法");
        }
    }
}