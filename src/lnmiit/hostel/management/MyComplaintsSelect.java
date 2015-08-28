package lnmiit.hostel.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyComplaintsSelect extends Activity {
	Button sign, anon ,logout;
	private SessionManagement session1;// global variable

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_complaints_choice);
		session1 = new SessionManagement(getApplicationContext()); // in
		sign = (Button) findViewById(R.id.mysigncomp);
		anon = (Button) findViewById(R.id.myanoncomp);
		logout = (Button) findViewById(R.id.logout37);
		sign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MyComplaintsSelect.this,MySignedCompDisp.class);
				startActivity(i);

			}
		});
		anon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MyComplaintsSelect.this,MyAnonCompDisp.class);
				startActivity(i);	
			}
		});
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				session1.setuserid(0);
				session1.setusertype("");
				Intent i = new Intent(MyComplaintsSelect.this, TabLayout.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});
	}

}
