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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ReviewSignCompDisp extends Activity{
	private static String ip = SessionManagement.ip;
	private static String url_sign_comp_details = "http://" + ip
			+ ":81/android_connect/get_sign_comp_details.php";
	private static final String TAG_SIGNCOMP = "signcomp";
	private static final String TAG_SUBJECT = "subject";
	private static final String TAG_DEPARTMENT = "department";
	private static final String TAG_HOSTEL = "hostel";
	private static final String TAG_ROOM = "room";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_COMPLAINTID = "complaintid";
	
	JSONParser jParser = new JSONParser();
	
	ListView listView;
	List<String> items;
	Button logout;
	String hostel,dept;
	private SessionManagement session1;// global variable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_sign_comp_disp);
		session1 = new SessionManagement(getApplicationContext()); // in
		logout = (Button)findViewById(R.id.logout42);
		listView = (ListView) findViewById(R.id.listViewSignComp);
		Bundle extras = getIntent().getExtras();
		hostel = extras.getString("hostel");
		dept = extras.getString("dept");
		new GetSignCompDetails().execute();
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ReviewSignCompDisp.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
	class GetSignCompDetails extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("hostel", hostel));
			params.add(new BasicNameValuePair("dept", dept));
			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_sign_comp_details, "GET",
					params);
			// check your log for json response
			Log.d("******* Get Signed Comp Details request send", json.toString());
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// JSONObject jsonResultObject = null;
			JSONArray signcompArray = null;
			JSONObject jobj = null;
			final JSONObject jobj1 = jsonObject;
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			try {
				signcompArray = jsonObject.getJSONArray(TAG_SIGNCOMP);
				for (int i = (signcompArray.length() - 1); i >= 0; i--) {
					Map<String, String> datum = new HashMap<String, String>(2);
					jobj = signcompArray.getJSONObject(i);
					String subject = jobj.getString(TAG_SUBJECT);
					String desc = jobj.getString(TAG_DESCRIPTION);
					datum.put("subject", subject);
					datum.put("desc", desc);
					// datum.put("designation", desig);
					data.add(datum);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			SimpleAdapter adapter = new SimpleAdapter(ReviewSignCompDisp.this, data,
					android.R.layout.simple_list_item_2, new String[] {
							"subject", "desc" }, new int[] { android.R.id.text1,
							android.R.id.text2 });
			listView.setAdapter(adapter);
			/*
			 * try { Thread.sleep(5000); } catch (InterruptedException e1) { //
			 * TODO Auto-generated catch block e1.printStackTrace(); }
			 */
			try {
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						//Toast.makeText(getApplicationContext(),
							//	"id " + (int) arg3, Toast.LENGTH_LONG).show();
						Log.d("******* CLick","inside on click");
						int id = (int) arg3;
						Intent i = new Intent(ReviewSignCompDisp.this,
								ReviewSignCompDetails.class);
						JSONArray signcompArray = null;
						try {
							signcompArray = jobj1.getJSONArray(TAG_SIGNCOMP);
							int id1 = signcompArray.length() - (id+1);
							JSONObject j = signcompArray.getJSONObject(id1);
							String subject = j.getString(TAG_SUBJECT);
							String dept = j.getString(TAG_DEPARTMENT);
							String hostel = j.getString(TAG_HOSTEL);
							String room = j.getString(TAG_ROOM);
							String desc = j.getString(TAG_DESCRIPTION);
							String compid = j.getString(TAG_COMPLAINTID);
							String status = j.getString("status");
							i.putExtra("subject",subject);
							i.putExtra("description", desc);
							i.putExtra("dept", dept);
							i.putExtra("hostel", hostel);
							i.putExtra("room", room);
							i.putExtra("status", status);
							i.putExtra("complaintid", compid);
							Log.d("******* CLick 2","inside on click");
							startActivity(i);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
