package lnmiit.hostel.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoticeForm extends Activity {
	EditText SUBJECT;
	EditText  NOTICE_DESC, DESIG;
	String subject, notice_desc,userid, designation;
	//private static String ip = "172.22.101.51";
	private static String ip = "10.0.2.2";
	private static String url_notice_details = "http://"+ip+":81/android_connect/create_notice.php";
	private SessionManagement session1;// global variable
	JSONParser jParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_form);
		session1 = new SessionManagement(getApplicationContext()); // in
		addListenerOnButton();
	}
	public void addListenerOnButton() {
		SUBJECT =  (EditText) findViewById(R.id.subject1);
		NOTICE_DESC = (EditText) findViewById(R.id.complaint_desc1);
		DESIG = (EditText) findViewById(R.id.designation1);
		Button submit= (Button) findViewById(R.id.submit11);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				subject =SUBJECT.getText().toString();
				notice_desc = NOTICE_DESC.getText().toString();
				designation = DESIG.getText().toString();
				userid = String.valueOf(session1.getuserid());
				
				if(subject.equals("")||notice_desc.equals("")||designation.equals(""))
				{
					Toast.makeText(getApplicationContext(), "Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					new PostNoticeDetails().execute();
				}


			}

		});
		Button clear= (Button) findViewById(R.id.clear1);
		clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SUBJECT.setText("");
				NOTICE_DESC.setText("");
				DESIG.setText("");
				
			}

		});
		Button logout= (Button) findViewById(R.id.notice_logout);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(NoticeForm.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}

		});
	}
	/**
	 * Background Async Task to Get complete product details
	 * */
	class PostNoticeDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("description", notice_desc));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("designation", designation));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_notice_details, "POST",
					params);
			// check your log for json response
			Log.d("******* Notice Details request send", json.toString());
			return json;
		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					SUBJECT.setText("");
					NOTICE_DESC.setText("");
					DESIG.setText("");
					Toast.makeText(NoticeForm.this,
							"Notice Successfully posted !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(NoticeForm.this,
							"Notice NOT posted !",
							Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
