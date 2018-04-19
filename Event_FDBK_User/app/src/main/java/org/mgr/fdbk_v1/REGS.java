package org.mgr.fdbk_v1;

import java.util.HashMap;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cz.msebera.android.httpclient.Header;

public class REGS extends Activity {

	private EditText email,phno;
	private TextView t;
	String uid;
	Bundle b;
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(!(activeNetworkInfo != null && activeNetworkInfo.isConnected())){
	    	Log.e("Internet", "No Internet");
	    	return false;
	    }
	    return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regs);
		Intent i = getIntent();
		b = i.getExtras();
		uid = b.getString("uid");
		email = (EditText)findViewById(R.id.editText1);
		phno = (EditText)findViewById(R.id.editText2);
		//t = (TextView)findViewById(R.id.textVie1);
		//String blueString = getResources().getString(R.string.fdbk_title);
		//t.setText(Html.fromHtml(blueString));
		
	}
	
	public void storeData(View v){
		if(!isNetworkAvailable()){			
	    	Toast.makeText(getBaseContext(), "Please Connect to the Internet and then Start this Application", Toast.LENGTH_LONG).show();		
	    	new CountDownTimer(500,500) {
				
				@Override
				public void onTick(long millisUntilFinished) {
				}
				
				@Override
				public void onFinish() {
					Intent i = new Intent(REGS.this,HSA.class);
					startActivity(i);
					finish();
				}
			}.start();	    	
		}
		else{
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			HashMap<String, String> map = new HashMap<String,String>();
			map.put("email", email.getText().toString());
			map.put("PHNO", phno.getText().toString());
			map.put("uid", uid);
			Gson g = new Gson();
			String a = g.toJson(map);
			params.add("udata1", a);
			client.post("http://www.scholarcouncil.com/www/mysqlsqlitesync/regUser.php", params, new AsyncHttpResponseHandler(){

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					Log.e("User Registration", "Failed");
					Toast.makeText(getApplicationContext(), "Unable to Register\nPlease try again.", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
					Log.e("User Registration", "Success");
					Intent i = new Intent(REGS.this,F1.class);
					i.putExtras(b);
					startActivity(i);
					finish();
				}
			});			
		}
	}
}
