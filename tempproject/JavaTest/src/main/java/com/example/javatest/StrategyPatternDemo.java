package com.example.javatest;

/**
 * Created by suxiaohan on 2018/9/20.
 * 观察者模式
 */

public class StrategyPatternDemo {
    public static void main(String[] argv){
        Duck D = new Duck();
        D.setFlyBehavior(new CanFly());
        D.performFly();


        Duck T = new Duck();
        T.setFlyBehavior(new NoFly());
        T.performFly();

    }


}
interface FlyBehavior{
    public void fly();
}
class NoFly implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("this duck can not fly");
    }
}
class CanFly implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("this duck can fly");
    }
}
class Duck{
    public Duck() {
        flyBehavior = new NoFly();
    }

    FlyBehavior flyBehavior;

    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }
    public void performFly(){
        flyBehavior.fly();
    }
}