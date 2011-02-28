package com.TempAtNsu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

// used icon from Mike Beecham (http://mikebeecham.deviantart.com/)
// free for commercial use

public class TempAtNsu extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup v = new TableLayout(this);
        
        Button b = new Button(this);
        b.setText("Refresh");
        v.addView(b);
        
    	final TextView textView = new TextView(this);
        v.addView(textView);
        
        b.setOnClickListener(new View.OnClickListener() {
        	
        	final class RequestTemp extends AsyncTask<Void, Void, String> {
        		protected String doInBackground(Void... arg0) {
        			String str = new String();
        			try {
                		HttpClient hc = new DefaultHttpClient();
                		HttpPost post = new HttpPost(
                				"http://weather.nsu.ru/loadata.php?tick='0'&rand='0'&std=three'");

                		HttpResponse rp = hc.execute(post);
                		
                		if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                			str = EntityUtils.toString(rp.getEntity());
                			
                			Pattern p = Pattern.compile("L = '(.*?)&");
                			Matcher m = p.matcher(str);
                			if (m.find())
                				str = m.group(1);

                			str = "The temp at NSU is: " + str + "C";
                		}
                	} catch (Exception e) {
                		str = e.toString();
                	}
        			return str;
        		}

        		protected void onPostExecute(String result) {
        			textView.setText(result);
        		}
        	}
        	
            public void onClick(View v) {
            	// Perform action on click
            	textView.setText("Updating...");
            	new RequestTemp().execute();
            }
        });

        setContentView(v);
    }
}



	





/*WebView webview = new WebView(this);
webview.getSettings().setJavaScriptEnabled(true);
webview.loadUrl("http://weather.nsu.ru/");
setWebViewClient(WebViewClient client)*/
//class MyJavaScriptInterface{
//	public void showHTML(String html) {
//		
//		textView.setText(html);
//		/*String substring = "some keyword";
//		boolean found=false;
//		found=html.contains(substring);
//		textView.setText(html);
//		System.out.println("html content is "+html);
//		if(found){
//			System.out.println("Keyword is found");
//		} else{
//			System.out.println("Keyword not found");
//		}*/
//	}
//}
//
//
//WebSettings settings = objWebView.getSettings(); 
//settings.setPluginsEnabled(true);
//settings.setJavaScriptEnabled(true);
//settings.setJavaScriptCanOpenWindowsAutomatically(true);
//	
//objWebView.setWebChromeClient(new WebChromeClient()); 
//objWebView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
//
///* WebViewClient must be set BEFORE calling loadUrl! */  
//objWebView.setWebViewClient(new WebViewClient() {  
//    @Override  
//    public void onPageFinished(WebView view, String url)  
//    {  
//        /* This call inject JavaScript into the page which just finished loading. */  
//    	objWebView.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");  
//    }  
//});  
//
//objWebView.loadUrl("http://weather.nsu.ru/");