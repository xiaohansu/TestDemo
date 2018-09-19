package com.example.suxiaohan.tempproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by suxiaohan on 2018/8/10.
 */

public class SecondActivity extends ListActivity{

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,Drink.drinks);

        ListView listView = getListView();
        listView.setAdapter(arrayAdapter);

        Drink drink = new Drink("milktea","simple",android.R.drawable.ic_menu_report_image);
        Drink.drinks[1] = drink;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(SecondActivity.this, DrinkActivity.class);
        intent.putExtra(SecondActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}
