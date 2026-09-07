package com.violets;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;

public class MainActivity extends Activity {
    private WebView webView;
    private ValueCallback<Uri[]> fileCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pakai layout bawaan biar gak crash
        webView = new WebView(this);
        setContentView(webView);
        try {
            WebSettings s = webView.getSettings();
            s.setJavaScriptEnabled(true);
            s.setDomStorageEnabled(true);
            s.setAllowFileAccess(true);
            s.setDatabaseEnabled(true);
            
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView v, String url) {
                    if (url.contains("wa.me") || url.contains("whatsapp") || url.startsWith("tel:")) {
                        try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); return true; } catch(Exception e){return false;}
                    }
                    return false;
                }
            });
            
            webView.setWebChromeClient(new WebChromeClient() {
                public boolean onShowFileChooser(WebView w, ValueCallback<Uri[]> cb, FileChooserParams p) {
                    fileCallback = cb;
                    try { startActivityForResult(p.createIntent(), 1); }
                    catch(Exception e) {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT); i.setType("image/*");
                        startActivityForResult(Intent.createChooser(i, "Pilih Foto"), 1);
                    }
                    return true;
                }
            });
            
            webView.loadUrl("file:///android_asset/index.html");
        } catch(Exception e) { e.printStackTrace(); }
    }
    
    protected void onActivityResult(int a, int b, Intent d) {
        super.onActivityResult(a,b,d);
        if(a==1 && fileCallback!=null) {
            Uri[] r = null;
            if(b==RESULT_OK && d!=null && d.getData()!=null) r = new Uri[]{d.getData()};
            fileCallback.onReceiveValue(r); fileCallback=null;
        }
    }
    
    public void onBackPressed() { if(webView.canGoBack()) webView.goBack(); else super.onBackPressed(); }
}
