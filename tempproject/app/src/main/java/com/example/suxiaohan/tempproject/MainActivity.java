package com.example.suxiaohan.tempproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean isrunning = true;
    private boolean wasrunning = true;
    private ListView ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ls= (ListView)findViewById(R.id.list_view);
        ls.setOnItemClickListener(itemClickListener);


    }


    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0){
            Intent intent  = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        isrunning = wasrunning;
    }

    @Override
     protected void onStop(){
        super.onStop();
        wasrunning = isrunning;
        isrunning = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", isrunning);
        savedInstanceState.putBoolean("wasrunning",wasrunning);
    }


    public void onClickstart(View view){
        isrunning = true;
    }
    public void onClickstop(View view){
        isrunning = false;
    }
    public void onClickreset(View view){
        isrunning = false;
        seconds = 0;
    }
    public void onClick (View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "test");
        Intent chcoseitent = Intent.createChooser(intent,"title");
        startActivity(chcoseitent);

//        Intent intent = new Intent("adb");
//
//        Intent choseiten = Intent.createChooser(intent,"title");
//        startActivity(choseiten);
    }

//    private void timerunner(){
//        final Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                int hours = seconds/3600;
//                int minutes = (seconds%3600)/60;
//                int secs = seconds%60;
//                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
//                showtime.setText(time);
//                if (isrunning) {
//                    seconds++;
//                }
//                handler.postDelayed(this,1000);
//
//            }
//        });
//
//
//    }
//
//
//    private void LoadMissionFromJson(String path){
//        try {
//
//            InputStream is = MainActivity.this.getClass().getClassLoader().getResourceAsStream("assets/" + "data6");
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            StringBuilder builder = new StringBuilder();
//            while((line = br.readLine()) != null){
//                builder.append(line);
//            }
//            br.close();
//            isr.close();
//            JSONObject testjson = new JSONObject(builder.toString());//builder读取了JSON中的数据。
//            //直接传入JSONObject来构造一个实例
//            JSONArray array = testjson.getJSONArray("wayPoints");         //从JSONObject中取出数组对象
//
//
//            //"towerLat":22.590045783402292,"towerLon":113.9799489325725,"toweralt":25.5,
//            Log.d("info","name=" + testjson.getString("name"));
//            Log.d("info","towerLat=" + testjson.getDouble("towerLat"));
//            Log.d("info","towerLon=" + testjson.getDouble("towerLon"));
//
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject lan = array.getJSONObject(i);
//
//                Log.d("info","altitude=" + lan.getDouble("altitude"));
//                Log.d("info","latitude=" + lan.getString("latitude"));
//                Log.d("info","longitude=" + lan.getString("longitude"));
//
//                Log.d("info","-----------------------");
//
//            }//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
