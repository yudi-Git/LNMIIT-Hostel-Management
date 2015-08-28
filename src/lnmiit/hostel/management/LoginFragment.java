package lnmiit.hostel.management;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import lnmiit.hostel.management.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();
	// url to get all products list
	//private static String ip = "172.22.101.51";
	private static String ip = SessionManagement.ip;
	private static String url_login_details = "http://"+ip+":81/android_connect/get_login_details.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ID = "id";
	private static final String TAG_PASSWORD = "password";
	private static final String TAG_LOGINDETAILS = "loginDetails";
	private static final String TAG_USERTYPE = "usertype";

	JSONArray products = null;
	String userName, password;

	private SessionManagement session1;// global variable

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_login, container,
				false);

		session1 = new SessionManagement(getActivity().getApplicationContext()); // in
																					// oncreate

		final EditText etusername = (EditText) rootView
				.findViewById(R.id.usernam);
		final EditText etpassword = (EditText) rootView.findViewById(R.id.pswd);
		Button btnSignIn = (Button) rootView.findViewById(R.id.signin);
		btnSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName = etusername.getText().toString();
				password = etpassword.getText().toString();
				if (userName == "" || password == "") {
					Toast.makeText(getActivity(), "Field(s) vacant !",
							Toast.LENGTH_LONG).show();	
				} else {
					new GetLoginDetails().execute();
				}
			}
		});
		return rootView;
	}

	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetLoginDetails extends AsyncTask<String, String, JSONObject> {
		/**
		 * Getting product details in background thread
		 * */
		protected JSONObject doInBackground(String... param1) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", userName));

			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_login_details, "GET", params);
			// check your log for json response
			Log.d("Login Details request send", json.toString());
			return json;
		}

		protected void onPostExecute(JSONObject json) {

			// json success tag
			int success;
			try {
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// successfully received product details
					JSONArray loginObj = json.getJSONArray(TAG_LOGINDETAILS); // JSON
					// get first product object from JSON Array
					JSONObject login = loginObj.getJSONObject(0);
					String pwd = login.getString(TAG_PASSWORD);
					if (pwd.equals(password)) {
						Toast.makeText(getActivity(), "Login Successfull !",
								Toast.LENGTH_LONG).show();
						String usertype = login.getString(TAG_USERTYPE);
						int userid = login.getInt(TAG_ID);
						session1.setusertype(usertype);
						session1.setuserid(userid);
						if (usertype.equals("superuser")) {
							Intent i = new Intent(getActivity(),
									StaffHome.class);
							startActivity(i);
						} else if (usertype.equals("hr")) {
							Intent i = new Intent(getActivity(),
									ResidentHome.class);
							startActivity(i);
						} else if (usertype.equals("hms")) {
							Intent i = new Intent(getActivity(),
									StaffHome.class);
							startActivity(i);
						}
					} else {
						Toast.makeText(getActivity(), "Incorrect Login Details !",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(getActivity(), "Incorrect Login Details !",
							Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
