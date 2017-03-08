package com.example.mark.scan;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;


import com.google.zxing.integration.android.IntentIntegrator;




public class MainActivity extends AppCompatActivity {

    private Document htmlDocument;
    private String htmlPageUrl = "http://express.giantpost.com.au/q?s=";
    private String htmlPageUrl2 ="";
    private WebView parsedHtmlNode;
    private String htmlContentInStringFormat;
    private com.victor.loading.book.BookLoading loding;
    private Elements newsHeadlines;
    boolean ani;





    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        parsedHtmlNode = (WebView)findViewById(R.id.WebView);
        Button htmlTitleButton = (Button)findViewById(R.id.button4);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loding= (com.victor.loading.book.BookLoading)findViewById(R.id.bookloading);
                if(ani!=true)
                {
                    loding.start();
                    ani=true;
                }
                else
                {
                    loding.stop();
                    ani=false;
                }

                //IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                //integrator.initiateScan();
            }
        });
    }



    private class JsoupAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... result) {
            try {
                htmlPageUrl2=htmlPageUrl+result[0];

                htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                //Log.d("diyi",  Arrays.toString(result));
                Log.d("diyi", htmlPageUrl2);

                newsHeadlines = htmlDocument.select(".page-contents");

                htmlContentInStringFormat = htmlDocument.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
           // parsedHtmlNode.    setText(newsHeadlines.html());

            parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);
        }
    }




}
