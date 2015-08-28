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

public class UpdateMyNotice extends Activity {
	JSONParser jParser = new JSONParser();
	EditText sub, desc, desig;
	String subj, descr, notid, design;
	Button update;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_update_my_notice_details = "http://" + ip
			+ ":81/android_connect/update_notice.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_my_notice);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		subj = extras.getString("title");
		descr = extras.getString("description");
		design = extras.getString("designation");
		notid = extras.getString("noticeid");

		sub = (EditText) findViewById(R.id.myNoticeSubj);
		desc = (EditText) findViewById(R.id.myNoticeDes);
		desig = (EditText) findViewById(R.id.myNoticeDesig);
		update = (Button) findViewById(R.id.updatemynotice);

		sub.setText(subj);
		desc.setText(descr);
		desig.setText(design);

		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subj = sub.getText().toString();
				descr = desc.getText().toString();
				design = desig.getText().toString();
				// Building Parameters
				if (descr.equals("") || design.equals("") || subj.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vacant !", Toast.LENGTH_LONG).show();
					return;
				} else {
					new UpdateNoticeDetails().execute();
				}
			}
		});

		Button logout = (Button) findViewById(R.id.logout36);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(UpdateMyNotice.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

		});
	}

	class UpdateNoticeDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("noticeid", notid));
			params.add(new BasicNameValuePair("description", descr));
			params.add(new BasicNameValuePair("designation", design));
			params.add(new BasicNameValuePair("title", subj));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_update_my_notice_details,
					"POST", params);
			// check your log for json response
			Log.d("******* Update Notice Details request send",
					json.toString());
			return json;

		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Toast.makeText(UpdateMyNotice.this,
							"Notice Successfully updated !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(UpdateMyNotice.this,
							"Notice NOT updated !", Toast.LENGTH_LONG)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
