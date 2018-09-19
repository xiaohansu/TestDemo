package com.example.javatest;

/**
 * Created by suxiaohan on 2018/9/15.
 */

public class FamilyZoo {
    public static void main(String[] argv){
        Cat Gaffey = new Cat();
        Gaffey.move();

        RobotCat DoulaAmeng = new RobotCat();
        DoulaAmeng.eat();

        boolean ret = DoulaAmeng instanceof Cat;
        System.out.println(ret);

        AnimalList animalList = new AnimalList();
        animalList.add(Gaffey);
        animalList.add(DoulaAmeng);

        ((RobotCat)animalList.getAnimals()[1]).speek();

        Cat dindang = new Cat("dingdang",6);
        dindang.getName();

    }
    static void havefunc(Pet e){
        e.play();
    }
}


abstract class Animal{
    abstract void eat();
    abstract void move();
    abstract void living();
}

abstract class Felidae extends Animal{
    public Felidae(){
        System.out.println("i creat a Felidae ");
    }
    @Override
    void living() {

    }
}


class Cat extends Felidae{
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

    private String name;
    private int age;
    public Cat(String name){
        this.name = name;
    }
    public Cat(String name,int age){
        this(name);
        this.age = age;
    }
    public Cat(){
        System.out.println("i creat a Cat ");
    }
    @Override
    public void move() {
        System.out.println("run");
    }

    @Override
    void eat() {
        System.out.println("fish");
    }
}

class RobotCat extends Cat{
    @Override
    void eat() {
        System.out.println("battery");
    }

    public void speek(){
        System.out.println("hello human1");
    }
}
class AnimalList{
    public Animal[] getAnimals() {
        return animals;
    }

    public void setAnimals(Animal[] animals) {
        this.animals = animals;
    }

    private Animal[] animals = new Animal[5];
    private int nextindex = 0;
    public void add(Animal a){
       if (nextindex < animals.length){
           animals[nextindex] = a;//
           System.out.println(a.getClass());
           nextindex++;
       }
    }
}

interface Pet{
    abstract void play();
}