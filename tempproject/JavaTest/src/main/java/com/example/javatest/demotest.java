package com.example.javatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.function.Function;

/**
 * Created by suxiaohan on 2018/9/10.
 */

public class demotest {
    public static void main(String argv[]){

        int numofGuesses = 0;
        GameHelper helper = new GameHelper();
        DotCom simpleDotCom = new DotCom();
        int randomNum =   (int)(Math.random()*5);
        ArrayList<String> location = new ArrayList<>();
        location.add(""+randomNum);
        location.add(""+(randomNum+1));
        location.add(""+(randomNum+2));

        simpleDotCom.setLocationCells(location);
        boolean isAlive = true;

        while (isAlive){
            String guess = helper.getUserInput("enter a number");
            String result = simpleDotCom.checkYourself(guess);
            numofGuesses ++;
            if (result.equals("kill")){
                isAlive = false;
                System.out.println("You take"+numofGuesses+"to finish");
            }
        }
    }
    public static  int addone(int a, Function<Integer,Integer> action){
        return action.apply(a);
    }

}
class DotCom{
    private ArrayList<String> locationCells;

    public void setLocationCells(ArrayList<String> loc) {
        this.locationCells = loc;
    }

    public String checkYourself(String guessnum){
        String result = "miss";
        int index = locationCells.indexOf(guessnum);
        System.out.println(index);
        if(index>=0){
            locationCells.remove(index);
            if (locationCells.isEmpty()){
                result = "kill";
            }else {
                result = "hit";
            }
        }


        System.out.println(result);
        return result;
    }

}

class GameHelper{
    public String getUserInput(String prompt){
        String inputLine = null;
        System.out.println(prompt+" ");
        try{
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if (inputLine.length() == 0){
                return null;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return inputLine;
    }
}