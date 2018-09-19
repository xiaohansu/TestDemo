package com.example.javatest;

/**
 * Created by suxiaohan on 2018/9/14.
 */

public class class7 {
    public static void main(String[] arge){

        superclass sp = new sonclass();
        sp.pint();

    }
}
class superclass{
   public void pint(){
       System.out.println("i am father's func");
   }

   public void pint(String str){
       System.out.println(str);
   }
}
class sonclass extends superclass{

    @Override
    public void pint(String str) {
        System.out.println("i am son's func");
    }

    public void sonpint(){
        System.out.println("i am son's persional func");
    }
}
