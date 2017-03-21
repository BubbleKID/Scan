package com.example.mark.scan;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.MultiFormatReader.*;



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
    private Elements newsHeadlines;
    private Elements newsHeadlines2;
    boolean ani;
    private ProgressDialog pd;
    private ImageButton Scan;

    //initialize variables to make them global

    private static final int SELECT_PHOTO = 100;
    //for easy manipulation of the result
    public String barcode;

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

        ImageButton Scan = (ImageButton)findViewById(R.id.imageButton1);

        //set a new custom listener
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v)
                {


                    Intent photoPic = new Intent(Intent.ACTION_PICK);
                    photoPic.setType("image/*");
                    startActivityForResult(photoPic, SELECT_PHOTO);



                }

            });

        //launch gallery via intent
       // Intent photoPic = new Intent(Intent.ACTION_PICK);
       // photoPic.setType("image/*");
        //startActivityForResult(photoPic, SELECT_PHOTO);

        //launch gallery via intent
        //Intent photoPic = new Intent(Intent.ACTION_PICK);
        //photoPic.setType("image/*");
       // startActivityForResult(photoPic, SELECT_PHOTO);



        ImageButton htmlTitleButton = (ImageButton)findViewById(R.id.button4);


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

                //IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                //integrator.initiateScan();

                //htmlPageUrl2="http://express.giantpost.com.au/q?s=gt04831512AU";
            }
        });
    }
   /* //do necessary coding for each ID
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton1:
                //launch gallery via intent
                Intent photoPic = new Intent(Intent.ACTION_PICK);
                photoPic.setType("image/*");
                startActivityForResult(photoPic, SELECT_PHOTO);
                break;
        }
    }*/
        //@Override
        protected void onActivityResult2(int requestCode, int resultCode, Intent data)
        {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == RESULT_OK)
                {
                        String _code = data.getStringExtra("SCAN_RESULT");
                        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                        //Log.d("codezhi",_code);
                        jsoupAsyncTask.execute( _code);

                }

        }
    public static String scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
        //copy pixel data from the Bitmap into the 'intArray' array
        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            contents = result.getText();
        }
        catch (Exception e) {
            Log.e("QrTest", "Error decoding barcode", e);
        }
        return contents;
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
                        //htmlPageUrl2="http://express.giantpost.com.au/q?s=gt04831512AU";
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
                    else if(Courier.equals("polar"))
                    {
                        htmlPageUrl2="http://www.polarexpress.com.au/track?num="+result[0];
                        //htmlPageUrl2=htmlPageUrl+"TYM137887";//result[0];  eg ：pe000383537bd
                        htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                        newsHeadlines =   htmlDocument.select(".ft_tbody,.row");
                       // newsHeadlines2 = htmlDocument.select(".row")+ htmlDocument.select(".ft_tbody");

                        //newsHeadlines =   newsHeadlines+     htmlDocument.select(".ft_tbody");

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
           // parsedHtmlNode.loadData(newsHeadlines.html()+newsHeadlines2.html(), "text/html; charset=UTF-8",null);


        }
    }

    //call the onactivity result method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {





        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);



        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    //doing some uri parsing
                    Uri selectedImage = imageReturnedIntent.getData();


                    InputStream imageStream = null;
                    try {
                        //getting the image
                        imageStream = getContentResolver().openInputStream(selectedImage);

                        Log.d("resultCode1","resultCode1");
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    //decoding bitmap
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream);
                    //Scan.setImageURI(selectedImage);// To display selected image in image view
                    int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
                    // copy pixel data from the Bitmap into the 'intArray' array
                    Log.d("resultCode2","resultCode2");
                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(),
                            bMap.getHeight());

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(),
                            bMap.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Log.d("resultCode3","resultCode3");
                    Reader reader = new MultiFormatReader();// use this otherwise
                    // ChecksumException
                    try {
                        Log.d("resultCode4","resultCode4");
                        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
                        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                        decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
                        //Result result = reader.decode(bitmap);
                        Result result = reader.decode(bitmap, decodeHints);
                        //*I have created a global string variable by the name of barcode to easily manipulate data across the application*//
                        //barcode =  result.getText().toString();
                        Log.d("resultCode5","resultCode4");
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Alert");
                        alertDialog.setMessage(result.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
/*
                        //do something with the results for demo i created a popup dialog
                        if(barcode!=null)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Scan Result");
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setMessage("" + barcode);
                            AlertDialog alert1 = builder.create();
                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent (getBaseContext(),MainActivity.class);
                                    startActivity(i);
                                }
                            });
                            alert1.setCanceledOnTouchOutside(false);
                            alert1.show();
                        }
                        else
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Scan Result");
                            builder.setIcon(R.mipmap.ic_launcher);
                            builder.setMessage("Nothing found try a different image or try again");
                            AlertDialog alert1 = builder.create();
                            alert1.setButton(DialogInterface.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent (getBaseContext(),MainActivity.class);
                                    startActivity(i);
                                }
                            });

                            alert1.setCanceledOnTouchOutside(false);

                            alert1.show();

                        }
                        //the end of do something with the button statement.
*/
                    } catch (NotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Nothing Found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (ChecksumException e) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (FormatException e) {
                        Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
        }
    }

}


