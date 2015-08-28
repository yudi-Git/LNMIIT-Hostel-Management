package lnmiit.hostel.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ResidentHome extends Activity {

	Button b1, b2, b3, b4, b5;
	JSONParser jParser = new JSONParser();
	private static String ip = SessionManagement.ip;
	private static String url_notice_details = "http://"+ip+":81/android_connect/get_notice_details.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_DESIG = "designation";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_NOTICEDETAILS = "noticeDetails";
	private static final String TAG_description = "description";
	private SessionManagement session1;// global variable
	String noticeid = "10";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resident_home);
		session1 = new SessionManagement(getApplicationContext()); // in
		b1 = (Button) findViewById(R.id.signcomp);
		b2 = (Button) findViewById(R.id.anoncomp);
		b3 = (Button) findViewById(R.id.viewnotices);
		b4 = (Button) findViewById(R.id.mycomp);
		b5 = (Button) findViewById(R.id.logout2);

		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ResidentHome.this, SignCompForm.class);
				startActivity(i);
			}
		});

		b2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ResidentHome.this, AnonCompForm.class);
				startActivity(i);
			}
		});
		
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ResidentHome.this,ViewNotice.class);
				startActivity(i);
				
			}
		});
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ResidentHome.this,MyComplaintsSelect.class);
				startActivity(i);
			}
		});
		b5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ResidentHome.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
				//finish();
			}
		});

	}
	}
