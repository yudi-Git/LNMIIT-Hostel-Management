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

public class UpdateMyAnonComp extends Activity {
	JSONParser jParser = new JSONParser();
	EditText sub, desc;
	String subj, descr, compid, status1, appstatus1;
	Button update;
	TextView status, appstatus;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_update_anon_comp_details = "http://" + ip
			+ ":81/android_connect/update_anon_complaint.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_my_anon_comp);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		subj = extras.getString("subject");
		descr = extras.getString("description");
		compid = extras.getString("complaintid");
		appstatus1 = extras.getString("appr_status");
		status1 = extras.getString("status");

		sub = (EditText) findViewById(R.id.myanonSubj);
		desc = (EditText) findViewById(R.id.myanonDes);
		update = (Button) findViewById(R.id.updatemyanon);
		status = (TextView) findViewById(R.id.myanonstatus);
		appstatus = (TextView) findViewById(R.id.myanonApprStaus);
		
		sub.setText(subj);
		desc.setText(descr);
		status.setText(status1);
		appstatus.setText(appstatus1);
		
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subj = sub.getText().toString();
				descr = desc.getText().toString();
				// Building Parameters
				if (descr.equals("") || subj.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				} else {
					new UpdateAnonCompDetails().execute();
				}
			}
		});

		Button logout = (Button) findViewById(R.id.logout34);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(UpdateMyAnonComp.this, TabLayout.class);
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
			params.add(new BasicNameValuePair("description", descr));
			params.add(new BasicNameValuePair("subject", subj));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_update_anon_comp_details,
					"POST", params);
			// check your log for json response
			Log.d("******* Update Anon Comp Details request send",
					json.toString());
			return json;

		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Toast.makeText(UpdateMyAnonComp.this,
							"Anon Complaint Successfully updated !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(UpdateMyAnonComp.this,
							"Anon Complaint NOT updated !", Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
