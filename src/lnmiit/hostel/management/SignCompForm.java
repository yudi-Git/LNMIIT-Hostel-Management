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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SignCompForm extends Activity {
	JSONParser jParser = new JSONParser();
	EditText ROOM_NUMBER;
	EditText COM_DESC, sub_desc;
	String room_number, com_desc, hostel, department, username,
			subject, status = "pending", userid;
	//private static String ip = "172.22.101.51";
	private static String ip = SessionManagement.ip;
	private static String url_sign_comp_details = "http://"+ip+":81/android_connect/create_signed_complaint.php";
	private static final String TAG_SUCCESS = "success";
	private RadioGroup radioGroup1,radioGroup;
	private SessionManagement session1;// global variable
	int selected1, selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_comp_form);
		session1 = new SessionManagement(getApplicationContext()); // in
																	// oncreate
		addListenerOnButton();
	}

	public void addListenerOnButton() {
		radioGroup1 = (RadioGroup) findViewById(R.id.radioSet1);
		selected1 = radioGroup1.getCheckedRadioButtonId();

		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				selected1 = radioGroup1.getCheckedRadioButtonId();
				if (checkedId == R.id.inventory) {
					department = "INVENTORY";
				} else if (checkedId == R.id.itdepartment) {
					department = "IT";
				} else if (checkedId == R.id.cleaning) {
					department = "CLEANING";
				} else if (checkedId == R.id.electrical) {
					department = "ELECTRICAL";
				}

			}

		});

		radioGroup = (RadioGroup) findViewById(R.id.radioSet);

		selected = radioGroup.getCheckedRadioButtonId();

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				selected = radioGroup.getCheckedRadioButtonId();
				if (checkedId == R.id.bh1) {
					hostel = "BH1";
				} else if (checkedId == R.id.bh2) {
					hostel = "BH2";
				} else if (checkedId == R.id.bh3) {
					hostel = "BH3";
				} else if (checkedId == R.id.gh) {
					hostel = "GH";
				}
			}

		});

		ROOM_NUMBER = (EditText) findViewById(R.id.room_number);
		COM_DESC = (EditText) findViewById(R.id.complaint_description);
		sub_desc = (EditText) findViewById(R.id.subject_description);

		userid = String.valueOf(session1.getuserid());

		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				/*
				 * Toast.makeText(getApplicationContext(), "check "+ selected ,
				 * Toast.LENGTH_LONG) .show();
				 */

				room_number = ROOM_NUMBER.getText().toString();
				com_desc = COM_DESC.getText().toString();
				subject = sub_desc.getText().toString();
				selected1 = radioGroup1.getCheckedRadioButtonId();
				selected = radioGroup.getCheckedRadioButtonId();
				if (room_number.equals("") || com_desc.equals("") || sub_desc.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Field(s) Vaccant !", Toast.LENGTH_LONG).show();
					return;
				} else if (selected1 == -1) {
					Toast.makeText(getApplicationContext(),
							"Department not selected", Toast.LENGTH_LONG)
							.show();
					return;
				} else if (selected == -1) {
					Toast.makeText(getApplicationContext(),
							"Hostel not selected", Toast.LENGTH_LONG).show();
					return;
				} else {
					new SubmitSignCompDetails().execute();
				}

			}

		});
		Button clear = (Button) findViewById(R.id.clear3);
		clear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				COM_DESC.setText("");
				ROOM_NUMBER.setText("");
				sub_desc.setText("");
				radioGroup.clearCheck();
				radioGroup1.clearCheck();
			}

		});
		Button logout = (Button) findViewById(R.id.sign_logout);
		logout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(SignCompForm.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}

		});
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class SubmitSignCompDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("department", department));
			params.add(new BasicNameValuePair("hostel", hostel));
			params.add(new BasicNameValuePair("room", room_number));
			params.add(new BasicNameValuePair("description", com_desc));
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("subject", subject));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_sign_comp_details, "POST",
					params);
			// check your log for json response
			Log.d("******* Sign Comp Details request send", json.toString());
			return json;
		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					COM_DESC.setText("");
					ROOM_NUMBER.setText("");
					sub_desc.setText("");
					radioGroup.clearCheck();
					radioGroup1.clearCheck();
					Toast.makeText(SignCompForm.this,
							"Signed Complaint Successfully submitted !",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(SignCompForm.this,
							"Signed Complaint NOT submitted !",
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
