package com.example.suxiaohan.tempproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.suxiaohan.tempproject.SecondActivity.EXTRA_DRINKNO;

/**
 * Created by suxiaohan on 2018/9/9.
 */

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);
        Drink drink = Drink.drinks[drinkNo];

        //Populate the drink image
        ImageView photo = (ImageView)findViewById(R.id.photo);
        photo.setImageResource(drink.getImageResourceId());
        photo.setContentDescription(drink.getName());
//Populate the drink name
        TextView name = (TextView)findViewById(R.id.name);
        name.setText(drink.getName());
//Populate the drink description
        TextView description = (TextView)findViewById(R.id.description);
        description.setText(drink.getDescription());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
