package com.example.mark.scan;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static android.R.attr.value;

public class MainActivity2 extends AppCompatActivity {

    private TextView mTextMessage;
    private String Url;
    private String Courier;
    private MenuItem lastItem; // 上一个选中的item



    private ArrayList<String>  someVariable;
    public ArrayList<String>  getSomeVariable() {
        return someVariable;
    }
    public void setSomeVariable(ArrayList<String> someVariable) {
        this.someVariable = someVariable;
    }



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
        setContentView(R.layout.activity_main2);
        GridView gridview = (GridView) findViewById(R.id.grid1);

        //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getSupportActionBar().setCustomView(R.layout.abs_layout);
        initView();

        gridview.setAdapter(new ImageAdapter(this));
       // gridview.setAdapter();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                        switch (position) {
                            case 0:
                                Url="http://express.giantpost.com.au/q?s=";
                                Courier="giant"; //捷安达快递
                                Toast.makeText(MainActivity2.this, "" + "捷安达快递",
                                Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Url="http://www.blueskyexpress.com.au/cgi-bin/GInfo.dll?EmmisTrack";
                                Courier="blue"; //蓝天快递
                                Toast.makeText(MainActivity2.this, "" + "蓝天快递",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Url="http://120.25.248.148/cgi-bin/GInfo.dll?EmmisTrack";
                                Courier="emms";//顺丰快递
                                Toast.makeText(MainActivity2.this, "" + "顺丰快递",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Url="http://www.polarexpress.com.au/track?num=";
                                Courier="polar"; //极地速递
                                Toast.makeText(MainActivity2.this, "" + "极地速递",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity2.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage( Url);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                //alertDialog.show();



                //Toast.makeText(MainActivity2.this, "" + position,
                        //Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                /* 通过Bundle对象存储需要传递的数据 */
                Bundle bundle = new Bundle();
                /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
                bundle.putString("hpUrl",Url);
                bundle.putString("Courier",Courier);
                /*把bundle对象assign给Intent*/
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

    private void initView() {
        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.navigation1);
        //拿到默认选中的item
        lastItem = bnv.getMenu().getItem(0);
        //点击选择item
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (lastItem != item) { // 判断当前点击是否为item自身
                    lastItem = item;
                    String title = item.getTitle().toString();

                    if (title.equals("主页")) {
                        //Intent intent = new Intent(MainActivity2.this, MainActivity2.class);
                        //startActivity(intent);
                        Toast.makeText(MainActivity2.this, title, Toast.LENGTH_LONG).show();
                    }
                    else if (title.equals("查询历史")) {
                        Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity2.this, title, Toast.LENGTH_LONG).show();
                    }else if (title.equals("设置")) {
                        Intent intent = new Intent(MainActivity2.this, SettingsActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity2.this, title, Toast.LENGTH_LONG).show();
                    }

                    //Intent intent = new Intent(MainActivity2.this, MainActivity4.class);
                    //startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }

}
