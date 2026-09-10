package com.pijatin.rumahaja;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
public class MainActivity extends AppCompatActivity {
private WebView webView;
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
webView=findViewById(R.id.webview);
webView.getSettings().setJavaScriptEnabled(true);
webView.getSettings().setDomStorageEnabled(true);
webView.setWebViewClient(new WebViewClient(){
@Override
public boolean shouldOverrideUrlLoading(WebView view,String url){
if(url.contains("wa.me")){
Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
startActivity(intent);
return true;
}
return false;
}
});
webView.loadUrl("file:///android_asset/index.html");
}
@Override
public void onBackPressed(){
if(webView.canGoBack()) webView.goBack();
else super.onBackPressed();
}
}
