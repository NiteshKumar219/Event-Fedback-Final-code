package org.mgr.eventapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class ResultViewActivity extends Activity {

	Spinner dataspin,chartspin;	
	WebView web;
	RelativeLayout r1,r2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_view);
		r1=(RelativeLayout)findViewById(R.id.befr);
		r2=(RelativeLayout)findViewById(R.id.after);
		dataspin=(Spinner)findViewById(R.id.data_spin);
		chartspin=(Spinner)findViewById(R.id.chart_spin);
		web=(WebView)findViewById(R.id.webView1);
		List<String>col_data = new ArrayList<String>();
		col_data.add("OverAll Rating");
		col_data.add("Speaker Rating");
		col_data.add("Overall Organization");
		col_data.add("Venue Hospitality");
		col_data.add("Event OnTime");
		col_data.add("Announcement");
		col_data.add("Person Type");
		ArrayAdapter<String> data_adpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,col_data);
		data_adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataspin.setAdapter(data_adpt);
		List<String>chart_data = new ArrayList<String>();
		chart_data.add("Bar Chart");
		chart_data.add("Pie Chart");
		ArrayAdapter<String> chart_adpt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,chart_data);
		chart_adpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		chartspin.setAdapter(chart_adpt);
		r1.setVisibility(View.VISIBLE);
		r2.setVisibility(View.GONE);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}
	
	public void generate(View v){		
		HashMap<String, String>map=new HashMap<String,String>();
		String x,col = (String)dataspin.getSelectedItem(),col1;
		col1 = (String)chartspin.getSelectedItem();
		if(col.equals("OverAll Rating")){
			x = "overall_rating";
		}
		else if(col.equals("Speaker Rating")){
			x = "speaker_rating";
		}
		else if(col.equals("Overall Organization")){
			x = "overall_eventorganization_satisfied";
		}
		else if(col.equals("Venue Hospitality")){
			x = "eventvenue_hospitality";
		}
		else if(col.equals("Event Ontime")){
			x = "event_started_ontime";
		}
		else if(col.equals("Announcement")){
			x = "how_know_event";
		}
		else{
			x = "person_identity";
		}
		if(col1.equals("Bar Chart")){
			map.put("ChartType","column2d");
		}
		else{
			map.put("ChartType", "pie3d");
		}
		map.put("ColName", x);
		String a;
		Gson g = new Gson();
		a = g.toJson(map);
		r1.setVisibility(View.GONE);
		r2.setVisibility(View.VISIBLE);
		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setSupportZoom(true);
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl("http://www.scholarcouncil.com/www/mysqlsqlitesync/graph_data.php?coldata="+a);
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
