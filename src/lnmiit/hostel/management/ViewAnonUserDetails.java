package lnmiit.hostel.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewAnonUserDetails extends Activity {

	String userid, uname;
	TextView user;
	Button logout;
	private SessionManagement session1;// global variable
	private static String ip = SessionManagement.ip;
	private static String url_user_details = "http://" + ip
			+ ":81/android_connect/get_user_details.php";
	JSONParser jParser = new JSONParser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("******* CLick 5", "inside on click");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_anon_user_details);
		session1 = new SessionManagement(getApplicationContext()); // in
		user = (TextView) findViewById(R.id.anonusername);

		Bundle extras = getIntent().getExtras();
		userid = extras.getString("userid");
		logout = (Button)findViewById(R.id.logout49);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ViewAnonUserDetails.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		new GetUserDetails().execute();
	}

	class GetUserDetails extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_user_details, "GET", params);
			// check your log for json response
			Log.d("******* Get User Details request send", json.toString());
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// JSONObject jsonResultObject = null;
			JSONArray anoncompArray = null;
			JSONObject jobj = null;
			try {
				anoncompArray = jsonObject.getJSONArray("userdetails");
				jobj = anoncompArray.getJSONObject(0);
				uname = jobj.getString("username");
				user.setText(uname);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
