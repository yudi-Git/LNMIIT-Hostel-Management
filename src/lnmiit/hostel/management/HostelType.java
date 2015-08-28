package lnmiit.hostel.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HostelType extends Activity {
	Button bh1,bh2,bh3,gh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hostel_type);
		bh1 = (Button) findViewById(R.id.bh1);
		bh2 = (Button) findViewById(R.id.bh2);
		bh3 = (Button) findViewById(R.id.bh3);
		gh = (Button) findViewById(R.id.gh);
		bh1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HostelType.this,Department.class);
				String strName = "bh1";
				i.putExtra("hostel", strName);
				startActivity(i);

			}
		});
		bh2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HostelType.this,Department.class);
				String keyIdentifer  = null;
				String strName = "bh2";
				i.putExtra("hostel", strName);
				startActivity(i);
			}
		});
		bh3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HostelType.this,Department.class);
				String keyIdentifer  = null;
				String strName = "bh3";
				i.putExtra("hostel", strName);
				startActivity(i);
			}
		});
		gh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(HostelType.this,Department.class);
				String keyIdentifer 
				= null;
				String strName = "gh";
				i.putExtra("hostel", strName);
				startActivity(i);
			}
		});
	}

}
