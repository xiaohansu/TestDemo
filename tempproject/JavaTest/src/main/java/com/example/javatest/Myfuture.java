package com.example.javatest;

import java.security.PublicKey;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Created by suxiaohan on 2018/8/16.
 */

public class Myfuture {
    private static int num = 0;

    public static void main(String[] arge){
        Dog dog = new AiDog();
        dog.Speak();

    }


}
class  Dog{
    int age ;
    String name;
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Dog(int age ,String name){
        this.age = age;
        this.name = name;
    }
    public Dog(){

    }
    public void Speak(){
        System.out.println("wa!wa!");
    }

}

class AiDog extends Dog{
    @Override
    public void Speak() {
        System.out.println("hello,human!");
    }
}