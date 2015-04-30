package lnmiit.hostel.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StaffHome extends Activity{

	Button b1,b2,b3,b4;
	private SessionManagement session1;// global variable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_home);
		session1 = new SessionManagement(getApplicationContext()); // in
		b1 = (Button)findViewById(R.id.postnotice);
		b2 = (Button)findViewById(R.id.review);
		b3 = (Button)findViewById(R.id.mynotices);
		b4 = (Button)findViewById(R.id.staff_logout);
		
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(StaffHome.this,NoticeForm.class);
				startActivity(i);
			}
		});
		
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(StaffHome.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
		
	}
}
