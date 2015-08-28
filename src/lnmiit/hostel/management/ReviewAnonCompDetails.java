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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReviewAnonCompDetails extends Activity {
	JSONParser jParser = new JSONParser();
	EditText status;
	TextView sub, desc, apprstatus;
	String subj, descr, compid, status1, appr;
	Button update;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_update_anon_comp_details = "http://" + ip
			+ ":81/android_connect/update_anon_complaint_status.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_anon_comp_details);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		subj = extras.getString("subject");
		descr = extras.getString("description");
		compid = extras.getString("complaintid");
		status1 = extras.getString("status");
		appr = extras.getString("appr_status");

		sub = (TextView) findViewById(R.id.revAnonSubj);
		desc = (TextView) findViewById(R.id.revAnonDes);
		apprstatus = (TextView) findViewById(R.id.ApprStatusTxtView);
		update = (Button) findViewById(R.id.updateRevAnonStatus);
		status = (EditText)findViewById(R.id.AnonCompStatus2);
		
		sub.setText(subj);
		desc.setText(descr);
		apprstatus.setText(appr);
		status.setText(status1);
		
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status1 = status.getText().toString();
				// Building Parameters
				if (appr.equals("Not approved") && status1.equalsIgnoreCase("addressed")) {
					status.setText("pending");
					Toast.makeText(getApplicationContext(),
							"Complaint is NOT yet approved", Toast.LENGTH_LONG).show();
					return;
				}
				else if (status1.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				}else {
					new UpdateAnonCompDetails().execute();
				}
			}
		});

		Button logout = (Button) findViewById(R.id.logout47);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ReviewAnonCompDetails.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

		});
	}

	class UpdateAnonCompDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("complaintid", compid));
			params.add(new BasicNameValuePair("status", status1));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_update_anon_comp_details,
					"POST", params);
			// check your log for json response
			Log.d("******* Update Anon Comp Status request send",
					json.toString());
			return json;

		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Toast.makeText(ReviewAnonCompDetails.this,
							"Complaint Status Successfully updated !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ReviewAnonCompDetails.this,
							"Complaint Status NOT updated !", Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
