package com.example.android_instructor.and401_lab5;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String LOG = MainActivity.class.getSimpleName();

    private Context context;
    private Button agregarButton;
    private WebView webView;

    private static final int RC_ABRIR_ACTIVITY = 156;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        agregarButton = (Button)findViewById(R.id.agregarButton);
        webView = (WebView)findViewById(R.id.webView);

        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SecondActivity.class);
                startActivityForResult(intent,RC_ABRIR_ACTIVITY);
            }
        });

        Intent intent = getIntent();
        String accion = intent.getAction();
        Uri data = intent.getData();

        URL url = null;
        try {
            //Schema Protocol
            //Host servidor
            //Path direccion
            url = new URL(data.getScheme(), data.getHost(), data.getPath());
            webView.loadUrl(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_ABRIR_ACTIVITY){
            if(resultCode == RESULT_OK){
                if(data.hasExtra("url")){
                    String url = data.getStringExtra("url");
                    Log.i(LOG,url);
                    mostrarWeb(url);
                }
            }
        }
    }

    private void mostrarWeb(String url) {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
