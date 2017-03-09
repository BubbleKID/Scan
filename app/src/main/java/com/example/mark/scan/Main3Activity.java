package com.example.mark.scan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main3Activity extends AppCompatActivity {


    private String htmlPageUrl;
    private Document  htmlDocument;
    private Elements       newsHeadlines;
    private String htmlContentInStringFormat;
    private WebView parsedHtmlNode;
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        parsedHtmlNode= (WebView)findViewById(R.id.web1);
        WebSettings webSettings = parsedHtmlNode.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //webview.loadData();


        JsoupAsyncTask2 jsoupAsyncTask = new JsoupAsyncTask2();
        //Log.d("codezhi",_code);
        jsoupAsyncTask.execute("111");

    }
    private class JsoupAsyncTask2 extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... result) {
            try {
                htmlPageUrl="http://www.changjiangexpress.com/";

                htmlDocument = Jsoup.connect(htmlPageUrl).get();
                //Log.d("diyi",  Arrays.toString(result));
                //Log.d("diyi", htmlPageUrl);
                //Document doc = Jsoup.parse(html);


                newsHeadlines = htmlDocument.select("#sidebar");

                /*AlertDialog alertDialog = new AlertDialog.Builder(Main3Activity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage( newsHeadlines.html());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/

                htmlContentInStringFormat = htmlDocument.title();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // parsedHtmlNode.    setText(newsHeadlines.html());
            //parsedHtmlNode.set  setText(htmlContentInStringFormat);
            parsedHtmlNode.loadData( newsHeadlines.html(), "text/html; charset=UTF-8",null);
        }
    }


}
