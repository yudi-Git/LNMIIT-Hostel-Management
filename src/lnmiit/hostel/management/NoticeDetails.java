package lnmiit.hostel.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NoticeDetails extends Activity{
	TextView sub,desc,desig;
	Button logout;
	private SessionManagement session1;// global variable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_details);
		session1 = new SessionManagement(getApplicationContext()); // in
		Bundle extras = getIntent().getExtras();
		String subj = extras.getString("subject");
		String descr= extras.getString("description");
		String designation= extras.getString("designation");
		sub = (TextView)findViewById(R.id.notSubj);
		desc = (TextView)findViewById(R.id.noticeDes1);
		desig = (TextView)findViewById(R.id.noticeDesig1);
		sub.setText(subj);
		desc.setText(descr);
		desig.setText(designation);
		logout = (Button)findViewById(R.id.logoutNotDet);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(NoticeDetails.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}
}
