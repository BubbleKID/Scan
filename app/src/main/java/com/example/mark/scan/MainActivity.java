package com.example.mark.scan;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.app.ProgressDialog;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;


import com.google.zxing.integration.android.IntentIntegrator;




public class MainActivity extends AppCompatActivity {

    private Document htmlDocument;
    //private String htmlPageUrl = "http://express.giantpost.com.au/q?s=";


    /*获取Intent中的Bundle对象*/
    //Bundle bundle = this.getIntent().getExtras();

    /*获取Bundle中的数据，注意类型和key*/
    private String htmlPageUrl;//= intent.getStringExtra("");
    private String Courier;
    private String htmlPageUrl2;
    private WebView parsedHtmlNode;
    private String htmlContentInStringFormat;
    private com.victor.loading.book.BookLoading loding;
    private Elements newsHeadlines;
    boolean ani;
    private ProgressDialog pd;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parsedHtmlNode = (WebView)findViewById(R.id.WebView);
        //parsedHtmlNode.getSettings().setJavaScriptEnabled(true);
        //parsedHtmlNode.getSettings().setSupportZoom(true);
        //parsedHtmlNode.getSettings().setBuiltInZoomControls(true);



        Intent intent = getIntent();
        htmlPageUrl = intent.getStringExtra("hpUrl");
        Courier = intent.getStringExtra("Courier");

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(Courier);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //alertDialog.show();


        Button htmlTitleButton = (Button)findViewById(R.id.button4);

        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* loding= (com.victor.loading.book.BookLoading)findViewById(R.id.bookloading);
                if(ani!=true)
                {
                    loding.start();
                    ani=true;
                }
                else
                {
                    loding.stop();
                    ani=false;
                }*/

                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();


            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK)
                {
                        String _code = data.getStringExtra("SCAN_RESULT");
                        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                        Log.d("codezhi",_code);
                        jsoupAsyncTask.execute( _code);
                }

        }
    private class JsoupAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... result) {
            try {
                if(Courier.equals("giant"))
                {
                    htmlPageUrl2=htmlPageUrl+result[0];
                    htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                    newsHeadlines = htmlDocument.select(".page-contents");
                    htmlContentInStringFormat = htmlDocument.title();
                }
                else if(Courier.equals("blue"))
                {
                    htmlPageUrl2=htmlPageUrl+"&w=&cno="+result[0];//"TYM137887";//result[0];
                    //htmlPageUrl2="http://www.blueskyexpress.com.au/cgi-bin/GInfo.dll?EmmisTrack&w=&cno=TYM137887";
                    htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                    newsHeadlines = htmlDocument.select(".content");
                    htmlContentInStringFormat = htmlDocument.title();
                }
                else if(Courier.equals("emms"))
                {
                    htmlPageUrl2=htmlPageUrl+"&w=&cno="+result[0];//"TYM137887";//result[0];
                    //htmlPageUrl2="http://www.blueskyexpress.com.au/cgi-bin/GInfo.dll?EmmisTrack&w=&cno=TYM137887";
                    htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                    newsHeadlines = htmlDocument.select(".content");
                    htmlContentInStringFormat = htmlDocument.title();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {



            final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Loading...",true);
           // parsedHtmlNode.    setText(newsHeadlines.html());

            parsedHtmlNode.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if(pd!=null && pd.isShowing())
                    {
                        //parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);
                        pd.dismiss();
                    }
                }
            });
            //parsedHtmlNode.loadUrl("http://www.google.com");
           // setTitle("Result");

            parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);
        }
    }




}
