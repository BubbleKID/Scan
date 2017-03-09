package com.example.mark.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import static android.R.attr.value;

public class MainActivity2 extends AppCompatActivity {

    private TextView mTextMessage;
    private String Url;
    private String Courier;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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

        gridview.setAdapter(new ImageAdapter(this));
       // gridview.setAdapter();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                        switch (position) {
                            case 0:
                                Url="http://express.giantpost.com.au/q?s=";
                                Courier="giant"; //捷安达快递
                                break;
                            case 1:
                                Url="http://www.blueskyexpress.com.au/cgi-bin/GInfo.dll?EmmisTrack";
                                Courier="blue"; //蓝天快递
                                break;
                            case 2:
                                Url="http://120.25.248.148/cgi-bin/GInfo.dll?EmmisTrack";
                                Courier="emms";//顺丰快递
                                break;
                            case 3:
                                Url="http://www.polarexpress.com.au/track?num=";
                                Courier="polar"; //极地速递
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



                Toast.makeText(MainActivity2.this, "" + position,
                        Toast.LENGTH_SHORT).show();

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

}
