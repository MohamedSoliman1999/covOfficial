package com.example.covofficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Bundle;
import android.se.omapi.SEService;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.example.covofficial.webViewClasses.FawryWebViewClient;
import com.example.covofficial.webViewClasses.NewsClassWebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

public class FawryServiceActivity extends AppCompatActivity {


    WebView fawryWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fawry_service);
        fawryWebView=findViewById(R.id.fawry_services_webView);
        fawryWebView.setWebViewClient(new FawryWebViewClient(){

            @Override public void onPageFinished(WebView view, String url) {


                fawryWebView.loadUrl("javascript:document.getElementByName('username').value = 'username'");

            }

        });


        fawryWebView.getSettings().setJavaScriptEnabled(true);
        fawryWebView.loadUrl("https://myfawry.com/MyFawryWeb/home.jsp#/");
        fawryWebView.loadUrl("javascript:document.getElementsByName('username').value = 'username'");
        fawryWebView.loadUrl("javascript:document.getElementsByName('password').value = 'password'");
        fawryWebView.loadUrl("javascript:document.forms['login'].submit()");


    }


    @Override
    public void onBackPressed() {
        if(fawryWebView.canGoBack())
        {

            fawryWebView.goBack();
        }  else
        {
            startActivity(new Intent(FawryServiceActivity.this, SrevicesActivity.class));
            finish();
        }



    }


}
