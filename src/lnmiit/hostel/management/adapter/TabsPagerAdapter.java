package lnmiit.hostel.management.adapter;

import lnmiit.hostel.management.ContactFragment;
import lnmiit.hostel.management.LoginFragment;
import lnmiit.hostel.management.ManualFragment;
import lnmiit.hostel.management.NoticeFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Top Rated fragment activity
            return new LoginFragment();
        case 1:
            // Games fragment activity
            return new ContactFragment();
        case 2:
            // Movies fragment activity
            return new ManualFragment();
        
		}
 
        return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
}
