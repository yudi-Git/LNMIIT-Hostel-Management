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

public class AnonCompForm extends Activity {
	EditText SUBJECT;
	EditText COM_DESC;
	String subject, com_desc, userid, status = "pending",
			appr_status = "Not approved";
	//private static String ip = "172.22.101.51";
	private static String ip = SessionManagement.ip;
	private static String url_anon_comp_details = "http://"+ip+":81/android_connect/create_anon_complaint.php";
	private SessionManagement session1;// global variable
	JSONParser jParser = new JSONParser();
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anon_comp_form);
		session1 = new SessionManagement(getApplicationContext()); // in
		addListenerOnButton();
	}

	public void addListenerOnButton() {
		SUBJECT = (EditText) findViewById(R.id.subject);
		COM_DESC = (EditText) findViewById(R.id.complaint_desc);

		Button submit = (Button) findViewById(R.id.submit1);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				subject = SUBJECT.getText().toString();
				com_desc = COM_DESC.getText().toString();
				userid = String.valueOf(session1.getuserid());

				if (subject.equals("") || com_desc.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				} else {
					new SubmitAnonCompDetails().execute();
				}

			}

		});
		Button clear = (Button) findViewById(R.id.clear2);
		clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SUBJECT.setText("");
				COM_DESC.setText("");

			}

		});
		Button logout = (Button) findViewById(R.id.anon_logout);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(AnonCompForm.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}

		});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class SubmitAnonCompDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("description", com_desc));
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("appr_status", appr_status));
			params.add(new BasicNameValuePair("subject", subject));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_anon_comp_details, "POST",
					params);
			// check your log for json response
			Log.d("******* Anon Comp Details request send", json.toString());
			return json;
		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					SUBJECT.setText("");
					COM_DESC.setText("");
					Toast.makeText(AnonCompForm.this,
							"Anon Complaint Successfully submitted !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(AnonCompForm.this,
							"Anon Complaint NOT submitted !", Toast.LENGTH_LONG)
							.show();
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
