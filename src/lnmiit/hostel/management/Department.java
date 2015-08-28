package lnmiit.hostel.management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Department extends Activity {
	Button inventory,electrical,cleaning,it;
	String hostel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.department);
		Bundle extras = getIntent().getExtras();
		hostel = extras.getString("hostel");
		inventory = (Button) findViewById(R.id.inventory);
		electrical = (Button) findViewById(R.id.electrical);
		it = (Button) findViewById(R.id.IT);
		cleaning = (Button) findViewById(R.id.cleaning);
		inventory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Department.this,ReviewSignCompDisp.class);
				String keyIdentifer  = "inventory";
				i.putExtra("dept", keyIdentifer);
				i.putExtra("hostel", hostel);
				startActivity(i);

			}
		});
		electrical.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Department.this,ReviewSignCompDisp.class);
				String keyIdentifer  = "electrical";
				i.putExtra("hostel", hostel);
				i.putExtra("dept", keyIdentifer);
				startActivity(i);
			}
		});
		it.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Department.this,ReviewSignCompDisp.class);
				String keyIdentifer  = "it";
				i.putExtra("hostel", hostel);
				i.putExtra("dept", keyIdentifer);
				startActivity(i);
			}
		});
		cleaning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Department.this,ReviewSignCompDisp.class);
				String keyIdentifer  = "cleaning";
				i.putExtra("hostel", hostel);
				i.putExtra("dept", keyIdentifer);
				startActivity(i);
			}
		});
	}

}
