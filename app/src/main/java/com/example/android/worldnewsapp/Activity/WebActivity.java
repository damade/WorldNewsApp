package com.example.android.worldnewsapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.android.worldnewsapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    public static final String NEWS_URL = "com.example.android.worldnewsapp.NEWS_URL";
    private ProgressDialog waitingDialog;
    private Toast startToast;
    private String webAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Bundle bundle = getIntent().getExtras();
        webAddress = bundle.getString(NEWS_URL);


        WebView myWebView = findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();

        waitingDialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        waitingDialog.setCancelable(false);

        startToast = Toast.makeText(this, R.string.start_loading, Toast.LENGTH_SHORT);

        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                waitingDialog.show();
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                startToast.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                waitingDialog.dismiss();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse
                    errorResponse) {
                Toast.makeText(view.getContext(), "HTTP error " + errorResponse.getStatusCode(), Toast.LENGTH_LONG).show();
            }

        });

        myWebView.loadUrl(webAddress);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WebActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_chrome) {
            String url = webAddress;
            Uri uri = Uri.parse(url);
            Intent view = new Intent(Intent.ACTION_VIEW, uri);
            if (view.resolveActivity(getPackageManager()) != null) {
                startActivity(view);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}