package com.example.covofficial;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.covofficial.webViewClasses.NewsClassWebViewClient;

public class NewsActivity extends AppCompatActivity {

    public void onBackPressed() {
        startActivity(new Intent(NewsActivity.this,MainPageActivity.class));
        finish();
    }


    public static final String TAG = "NewsActivity";
    private static final String baseURl = "http://twitter.com";
    private static final String widgetInfo="<a class=\"twitter-timeline\" data-theme=\"light\" href=\"https://twitter.com/mohpegypt?ref_src=twsrc%5Etfw\"></a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        WebView webView=findViewById(R.id.webview);
        webView.setWebViewClient(new NewsClassWebViewClient());

        progressdilaogFun();

        if(isConnectedToInternet()){
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(baseURl,widgetInfo,"text/html","UTF-8", null);
        }
        else{
            //display dialog
            new TTFancyGifDialog.Builder(NewsActivity.this)
                    .setPositiveBtnText((getString(R.string.TTF_newsActivity_posBtn)))
                    .setMessage(getString(R.string.TTF_newsActivity_message))
                    .setGifResource(R.drawable.internet_connection)
                    .setPositiveBtnText(getString(R.string.TTF_newsActivity_posBtnTxt))
                    .setPositiveBtnBackground("#22b573")
                    .isCancellable(true)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            startActivity(new Intent(NewsActivity.this,NewsActivity.class));
                            finish();
                        }
                    }).build();
        }



    }

    //check internet connection
   public boolean isConnectedToInternet() {
        boolean connected=false;

        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;


    }

    //Dialog fun
    private void progressdilaogFun() {

        final ProgressDialog progressDialog=new ProgressDialog(NewsActivity.this);
        progressDialog.setMessage(getString(R.string.progFunMss_NewsActivity));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}


