package com.example.mark.scan;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.Toast;

import com.google.zxing.client.android.Intents;

import java.util.ArrayList;


public class MainActivity4 extends AppCompatActivity {

    private TextView mTextMessage;
    private MenuItem lastItem; // 上一个选中的item
    public  ArrayList<String> listItems;
    private ArrayAdapter<String> adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_history:
                    mTextMessage.setText(R.string.title_history);

                    return true;
                case R.id.navigation_setting:
                    mTextMessage.setText(R.string.title_setting);
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView2();
        ListView listView = (ListView) findViewById(R.id.listview);

        listItems = new ArrayList<String>();
        //listItems=MainActivity2
        listItems = ((MyApplication) this.getApplication()).getSomeVariable();


                //listItems.add("First Item - added on Activity Create");
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private void initView2() {
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.navigation2);
        //拿到默认选中的item
        //bnv.setSelected( true);
        MenuItem targetMenuItem =  bnv.getMenu().getItem(1);
        targetMenuItem.setChecked(true);
        //item.setChecked(item.getItemId() == 1);

        lastItem = bnv.getMenu().getItem(0);
        //点击选择item
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //if (lastItem != item) { // 判断当前点击是否为item自身
                    lastItem = item;
                    String title = item.getTitle().toString();

                    if (title.equals("主页")) {
                        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity4.this, title, Toast.LENGTH_LONG).show();
                    }
                    else if (title.equals("查询历史")) {
                        //Intent intent = new Intent(MainActivity4.this, MainActivity4.class);
                        //startActivity(intent);
                        Toast.makeText(MainActivity4.this, title, Toast.LENGTH_LONG).show();
                    }else if (title.equals("设置")) {
                        Intent intent = new Intent(MainActivity4.this, SettingsActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity4.this, title, Toast.LENGTH_LONG).show();
                    }
                    return true;
               // }
                //return false;
            }
        });
    }

}
