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

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;

import static com.example.mark.scan.R.layout.scanpage;


public class ScanPage extends AppCompatActivity {

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
    private static final Logger log = Logger.getLogger(ScanPage.class.getName());

    //initialize variables to make them global

    private static final int SELECT_PHOTO = 100;
    private static final int CAPTURE_PHPTO = 2;
    //for easy manipulation of the result
    public String barcode;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(scanpage);
        parsedHtmlNode = (WebView)findViewById(R.id.WebView);
        //parsedHtmlNode.getSettings().setJavaScriptEnabled(true);
        parsedHtmlNode.getSettings().setSupportZoom(true);
        parsedHtmlNode.getSettings().setBuiltInZoomControls(true);



        final Intent intent = getIntent();
        htmlPageUrl = intent.getStringExtra("hpUrl");
        Courier = intent.getStringExtra("Courier");
/*
        AlertDialog alertDialog = new AlertDialog.Builder(ScanPage.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(Courier);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //alertDialog.show();
*/
        ImageButton Scan = (ImageButton)findViewById(R.id.imageButton1);
        //set a new custom listener
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v)
                {
                    Intent photoPic = new Intent(Intent.ACTION_PICK);
                    //photoPic.setAction(Intent.ACTION_GET_CONTENT);
                    photoPic.setType("image/*");
                    startActivityForResult(photoPic, SELECT_PHOTO);
                }

            });

        ImageButton htmlTitleButton = (ImageButton)findViewById(R.id.button4);
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanPage.this);

                Intent intent = integrator.createScanIntent();
                startActivityForResult(intent,CAPTURE_PHPTO);
                //integrator.initiateScan(); 旧方法
               // htmlPageUrl2="http://express.giantpost.com.au/q?s=gt04831512AU";
            }
        });
    }

        //@Override
        protected void onActivityResult2(int requestCode, int resultCode, Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);
            /*
            AlertDialog alertDialog = new AlertDialog.Builder(ScanPage.this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("123123");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            //alertDialog.show();*/
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
                      /*  AlertDialog alertDialog4 = new AlertDialog.Builder(ScanPage.this).create();
                        alertDialog4.setTitle("Alert");
                        alertDialog4.setMessage(Courier);
                        alertDialog4.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog4.show();

*/
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
                    else if(Courier.equals("eps"))
                    {
                        htmlPageUrl2="http://www.epspost.com/OrderQuery.asp?action=search&Ordernumber="+result[0];
                        //htmlPageUrl2="http://www.epspost.com/OrderQuery.asp?action=search&Ordernumber=EP1554000AU";
                        //e.g   http://www.epspost.com/OrderQuery.asp?action=search&Ordernumber=EP1554000AU

                        htmlDocument = Jsoup.connect(htmlPageUrl2).get();
                        newsHeadlines =   htmlDocument.select(".rong");
                        htmlContentInStringFormat = htmlDocument.title();

                    }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

           // final ProgressDialog pd = ProgressDialog.show(ScanPage.this, "", "Loading...",true);
           // parsedHtmlNode.    setText(newsHeadlines.html());
           /*
            parsedHtmlNode.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    if(pd!=null && pd.isShowing())
                    {   //parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);
                        pd.dismiss();
                    }
                }
            });*/

           if(!newsHeadlines.html().isEmpty())
           {
               parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);
           }
           else
           {
               parsedHtmlNode.loadData("未查询到结果", "text/html; charset=UTF-8",null);
           }

            //parsedHtmlNode.loadUrl("http://www.google.com");
           // setTitle("Result");

            //parsedHtmlNode.loadData(newsHeadlines.html(), "text/html; charset=UTF-8",null);


        }
    }

    //call the onactivity result method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    //doing some uri parsing
                    Uri selectedImage = imageReturnedIntent.getData();
                    String imgPath = selectedImage.getPath();
                    InputStream imageStream = null;
                    try {
                        //getting the image
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "File not found", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    //decoding bitmap
                    //Bitmap bMap = BitmapFactory.decodeStream(imageStream);
                    //calculateInSampleSize(options, 1024, 1024);
                    String path = "/external/images/media/17758";
                    //bMap = decodeSampledBitmapFromFile(path, 720, 720);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // First decode with inJustDecodeBounds=true to check dimensions

                    options.inJustDecodeBounds = true;
                    // BitmapFactory.decodeFile(imgPath, options);
                    // Calculate inSampleSize
                    options.inSampleSize = calculateInSampleSize(options, 1024, 1024);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    //bMap = BitmapFactory.decodeFile(imgPath, options);
                    Bitmap bMap = BitmapFactory.decodeStream(imageStream, null, options);

                    // Scale down the bitmap if it is bigger than we need.
                    int width = bMap.getWidth();
                    int height = bMap.getHeight();
                    final int targetWidth = 720, targetHeight = 720;
                    if (width > targetWidth || height > targetHeight) {
                        float scale = 0.0f;
                        if (width >= height) {
                            scale = (float) targetWidth / width;
                        } else {
                            scale = (float) targetHeight / height;
                        }
                        int w = Math.round(scale * width);
                        int h = Math.round(scale * height);
                        bMap = Bitmap.createScaledBitmap(bMap, w, h, true);
                    }
                    //Scan.setImageURI(selectedImage);// To display selected image in image view
                    int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
                    // copy pixel data from the Bitmap into the 'intArray' array

                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

                    // http://iluhcm.com/2016/01/08/scan-qr-code-and-recognize-it-from-picture-fastly-using-zxing/

                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                  // ChecksumException
                    try {
                        Reader reader = new MultiFormatReader();// use this otherwise
                        ReaderException savedException = null;

                        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
                        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
                        decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
                        decodeHints.put(DecodeHintType.CHARACTER_SET, Boolean.TRUE);
                        //Result result = reader.decode(bitmap);
                        Result result = reader.decode(bitmap, decodeHints);
                        //*I have created a global string variable by the name of barcode to easily manipulate data across the application*/
                    //barcode =  result.getText().toString();

                        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                        //Log.d("codezhi",_code);
                        jsoupAsyncTask.execute(result.toString());

                      /*
                        AlertDialog alertDialog2 = new AlertDialog.Builder(ScanPage.this).create();
                        alertDialog2.setTitle("Alert");
                        alertDialog2.setMessage(result.toString());
                        alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog2.show();*/
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
                                    Intent i = new Intent (getBaseContext(),scanpage.class);
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
                                    Intent i = new Intent (getBaseContext(),scanpage.class);
                                    startActivity(i);
                                }
                            });

                            alert1.setCanceledOnTouchOutside(false);

                            alert1.show();

                        }
                        //the end of do something with the button statement.
*/
                   } catch (NotFoundException e)
                    {
                        Toast.makeText(getApplicationContext(), "未查询到条形码", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (ChecksumException e)
                    {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (FormatException e)
                    {
                        Toast.makeText(getApplicationContext(), "Wrong Barcode/QR format", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (NullPointerException e)
                    {
                        Toast.makeText(getApplicationContext(), "Something weird happen, i was probably tired to solve this issue", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                //break;

            case 2:
                if (resultCode == RESULT_OK) {
                    String _code =  imageReturnedIntent.getStringExtra("SCAN_RESULT");
                    JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                    //Log.d("codezhi",_code);
                    jsoupAsyncTask.execute(_code);
/*
                    AlertDialog alertDialog = new AlertDialog.Builder(ScanPage.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage(_code );
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    */

                }
               //break;

        }
    }


    /**
     * 根据给定的宽度和高度动态计算图片压缩比率
     *
     * @param options Bitmap配置文件
     * @param reqWidth 需要压缩到的宽度
     * @param reqHeight 需要压缩到的高度
     * @return 压缩比
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 将图片根据压缩比压缩成固定宽高的Bitmap，实际解析的图片大小可能和#reqWidth、#reqHeight不一样。
     *
     * @param imgPath 图片地址
     * @param reqWidth 需要压缩到的宽度
     * @param reqHeight 需要压缩到的高度
     * @return Bitmap
     */
    public static Bitmap decodeSampledBitmapFromFile(String imgPath, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imgPath, options);
    }
    /**
     * Alert 消息提示功能
     *

     */
    public void alert(String Message)
    {


    }


}





