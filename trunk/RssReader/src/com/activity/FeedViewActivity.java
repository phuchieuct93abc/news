package com.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.phuchieu.news.R;

@EActivity(R.layout.view_swipe)
public class FeedViewActivity extends FragmentActivity {

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	@ViewById
	ViewPager pager;

	@AfterViews
	void run() {
		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(
				getSupportFragmentManager());

		mDemoCollectionPagerAdapter.setContext(getApplicationContext());
		pager.setAdapter(mDemoCollectionPagerAdapter);
	}

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
	public DemoCollectionPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	Context context;

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new FeedViewFragment_();
		((FeedViewFragment) fragment).setContext(this.context);
		// Bundle args = new Bundle();
		// Our object is just an integer :-P
		// args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
		// fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "OBJECT " + (position + 1);
	}
}

/*class DemoObjectFragment extends Fragment {
	public static final String ARG_OBJECT = "object";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// The last two arguments ensure LayoutParams are inflated
		// properly.
		View rootView = inflater.inflate(R.layout.view, container, false);
		Bundle args = getArguments();
		((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer
				.toString(args.getInt(ARG_OBJECT)));
		return rootView;
	}
}*/

// Instances of this class are fragments representing a single
// object in our collection.

