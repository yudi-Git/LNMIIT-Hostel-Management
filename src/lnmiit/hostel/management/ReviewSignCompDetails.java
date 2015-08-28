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

public class ReviewSignCompDetails extends Activity {
	JSONParser jParser = new JSONParser();
	EditText status;
	TextView sub, room, desc, hostel, dept;
	String subj, deptmnt, hostelno, roomno, descr, compid, status1;
	Button update;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_update_sign_comp_details = "http://" + ip
			+ ":81/android_connect/update_signed_complaint_status.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_sign_comp_details);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		subj = extras.getString("subject");
		hostelno = extras.getString("hostel");
		roomno = extras.getString("room");
		descr = extras.getString("description");
		deptmnt = extras.getString("dept");
		compid = extras.getString("complaintid");
		status1 = extras.getString("status");

		sub = (TextView) findViewById(R.id.reviewsignSubj);
		dept = (TextView) findViewById(R.id.reviewsignDept);
		hostel = (TextView) findViewById(R.id.reviewsignHostel);
		room = (TextView) findViewById(R.id.revsignRoom);
		desc = (TextView) findViewById(R.id.revsignDes);
		update = (Button) findViewById(R.id.updateSignStatus);
		status = (EditText)findViewById(R.id.signCompStatus2);
		
		sub.setText(subj);
		dept.setText(deptmnt);
		hostel.setText(hostelno);
		room.setText(roomno);
		desc.setText(descr);
		status.setText(status1);
		
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				status1 = status.getText().toString();
				// Building Parameters
				if (status1.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				} else {
					new UpdateSignCompDetails().execute();
				}
			}
		});

		Button logout = (Button) findViewById(R.id.logout43);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ReviewSignCompDetails.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

		});
	}

	class UpdateSignCompDetails extends AsyncTask<String, String, JSONObject> {
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
			json = jParser.makeHttpRequest(url_update_sign_comp_details,
					"POST", params);
			// check your log for json response
			Log.d("******* Update Sign Comp Status request send",
					json.toString());
			return json;

		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Toast.makeText(ReviewSignCompDetails.this,
							"Complaint Status Successfully updated !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(ReviewSignCompDetails.this,
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
