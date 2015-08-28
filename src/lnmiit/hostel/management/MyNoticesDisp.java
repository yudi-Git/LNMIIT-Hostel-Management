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

import lnmiit.hostel.management.ViewNotice.GetNoticeDetails;

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

public class MyNoticesDisp extends Activity{
	private static String ip = SessionManagement.ip;
	private static String url_my_notice_details = "http://" + ip
			+ ":81/android_connect/get_my_notice_details.php";
	private static final String TAG_MYNOTICE = "mynotice";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_DESIGNATION = "designation";
	private static final String TAG_NOTICEID = "noticeid";
	
	JSONParser jParser = new JSONParser();

	String num = "15";

	ListView listView;
	List<String> items;
	Button logout;
	private SessionManagement session1;// global variable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_notices_disp);
		session1 = new SessionManagement(getApplicationContext()); // in
		logout = (Button)findViewById(R.id.logout35);
		listView = (ListView) findViewById(R.id.listViewMyNotice);
		session1 = new SessionManagement(getApplicationContext()); // in
		new GetMyNoticeDetails().execute();
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(MyNoticesDisp.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
	class GetMyNoticeDetails extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String userid = String.valueOf(session1.getuserid());
			params.add(new BasicNameValuePair("userid", userid));
			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_my_notice_details, "GET",
					params);
			// check your log for json response
			Log.d("******* Get My Notice Details request send", json.toString());
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// JSONObject jsonResultObject = null;
			JSONArray myNoticeArray = null;
			JSONObject jobj = null;
			final JSONObject jobj1 = jsonObject;
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			try {
				myNoticeArray = jsonObject.getJSONArray(TAG_MYNOTICE);
				for (int i = (myNoticeArray.length() - 1); i >= 0; i--) {
					Map<String, String> datum = new HashMap<String, String>(2);
					jobj = myNoticeArray.getJSONObject(i);
					String subject = jobj.getString(TAG_TITLE);
					String desc = jobj.getString(TAG_DESCRIPTION);
					datum.put("subject", subject);
					datum.put("desc", desc);
					// datum.put("designation", desig);
					data.add(datum);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			SimpleAdapter adapter = new SimpleAdapter(MyNoticesDisp.this, data,
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
						int id = (int) arg3;
						Intent i = new Intent(MyNoticesDisp.this,
								UpdateMyNotice.class);
						JSONArray myNoticeArray = null;
						try {
							myNoticeArray = jobj1.getJSONArray(TAG_MYNOTICE);
							int id1 = myNoticeArray.length() - (id+1);
							JSONObject j = myNoticeArray.getJSONObject(id1);
							String subject = j.getString(TAG_TITLE);
							String desc = j.getString(TAG_DESCRIPTION);
							String desig = j.getString(TAG_DESIGNATION);
							String notid = j.getString(TAG_NOTICEID);
							i.putExtra("title",subject);
							i.putExtra("description", desc);
							i.putExtra("noticeid", notid);
							i.putExtra("designation", desig);
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
