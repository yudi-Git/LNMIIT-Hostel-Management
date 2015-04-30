package lnmiit.hostel.management;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoticeDetails extends Activity{
	TextView sub,desc,desig;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_details);
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
		
	}
}
