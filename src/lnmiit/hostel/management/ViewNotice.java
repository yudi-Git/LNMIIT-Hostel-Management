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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ViewNotice extends Activity {

	private static String ip = "10.0.2.2";
	private static String url_view_notice_details = "http://" + ip
			+ ":81/android_connect/get_notice_details.php";
	private static final String TAG_NOTICE = "notice";
	private static final String TAG_TITLE = "title";
	private static final String TAG_DESIGNATION = "designation";
	private static final String TAG_DESCRIPTION = "description";
	JSONParser jParser = new JSONParser();

	String num = "15";

	ListView listView;
	List<String> items;
	Button logout;
	private SessionManagement session1;// global variable

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_notice2);
		listView = (ListView) findViewById(R.id.listViewNotice);
		session1 = new SessionManagement(getApplicationContext()); // in
		new GetNoticeDetails().execute();
		logout = (Button) findViewById(R.id.logout_notic);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(ViewNotice.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

	class GetNoticeDetails extends AsyncTask<String, String, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONObject json = null;
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("num", num));
			// getting login details by making HTTP request
			// Note that login details url will use GET request
			json = jParser.makeHttpRequest(url_view_notice_details, "GET",
					params);
			// check your log for json response
			Log.d("******* Get Notice Details request send", json.toString());
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObject) {
			// JSONObject jsonResultObject = null;
			JSONArray noticeArray = null;
			JSONObject jobj = null;
			final JSONObject jobj1 = jsonObject;
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			try {
				noticeArray = jsonObject.getJSONArray(TAG_NOTICE);
				for (int i = (noticeArray.length() - 1); i >= 0; i--) {
					Map<String, String> datum = new HashMap<String, String>(2);
					jobj = noticeArray.getJSONObject(i);
					String title = jobj.getString(TAG_TITLE);
					String desig = jobj.getString(TAG_DESIGNATION);
					String desc = jobj.getString(TAG_DESCRIPTION);
					String combine = desig + "  : " + desc;
					datum.put("title", title);
					datum.put("desc", combine);
					// datum.put("designation", desig);
					data.add(datum);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			SimpleAdapter adapter = new SimpleAdapter(ViewNotice.this, data,
					android.R.layout.simple_list_item_2, new String[] {
							"title", "desc" }, new int[] { android.R.id.text1,
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
						Intent i = new Intent(ViewNotice.this,
								NoticeDetails.class);
						JSONArray noticeArray = null;
						try {
							noticeArray = jobj1.getJSONArray(TAG_NOTICE);
							int id1 = noticeArray.length() - (id+1);
							JSONObject j = noticeArray.getJSONObject(id1);
							String title = j.getString(TAG_TITLE);
							String desig = j.getString(TAG_DESIGNATION);
							String desc = j.getString(TAG_DESCRIPTION);
							i.putExtra("subject", title);
							i.putExtra("description", desc);
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
