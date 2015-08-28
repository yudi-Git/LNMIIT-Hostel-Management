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

public class ApproveAnonCompStatus extends Activity {
	JSONParser jParser = new JSONParser();
	EditText status, appr_status;
	TextView sub, desc;
	String subj, descr, compid, status1, appr, userid;
	Button update,viewuser;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_approve_anon_comp_details = "http://" + ip
			+ ":81/android_connect/approve_anon_complaint_status.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("******* CLick 4","inside on click");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.approve_anon_comp);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		subj = extras.getString("subject");
		descr = extras.getString("description");
		compid = extras.getString("complaintid");
		userid = extras.getString("userid");
		status1 = extras.getString("status");
		appr = extras.getString("appr_status");

		sub = (TextView) findViewById(R.id.apprAnonSubj);
		desc = (TextView) findViewById(R.id.apprAnonDes);
		appr_status = (EditText) findViewById(R.id.AnonCompApprStatus2);
		update = (Button) findViewById(R.id.apprAnonStatus3);
		status = (EditText)findViewById(R.id.anonAddrStatus);
		
		sub.setText(subj);
		desc.setText(descr);
		appr_status.setText(appr);
		status.setText(status1);
		
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status1 = status.getText().toString();
				appr = appr_status.getText().toString();
				// Building Parameters
				if (appr.equals("Not approved") && status1.equalsIgnoreCase("addressed")) {
					status.setText("pending");
					Toast.makeText(getApplicationContext(),
							"Complaint is NOT yet approved", Toast.LENGTH_LONG).show();
					return;
				}
				else if (status1.equals("") || appr.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				}else {
					new UpdateAnonCompDetails().execute();
				}
			}
		});

		Button logout = (Button) findViewById(R.id.logout48);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ApproveAnonCompStatus.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

		});
		viewuser = (Button) findViewById(R.id.viewUser);
		viewuser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ApproveAnonCompStatus.this, ViewAnonUserDetails.class);
				i.putExtra("userid", userid);
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
			params.add(new BasicNameValuePair("appr_status", appr));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_approve_anon_comp_details,
					"POST", params);
			// check your log for json response
			Log.d("******* Approve Anon Comp Status request send",
					json.toString());
			return json;

		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Toast.makeText(ApproveAnonCompStatus.this,
							"Complaint Status Successfully updated !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ApproveAnonCompStatus.this,
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
