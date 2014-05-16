package unb.mdsgpp.qualcurso.test;

import android.test.ActivityInstrumentationTestCase2;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;

public class TestMainActivity extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;

	public TestMainActivity() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
	}

	public void testShouldOnSectionAttachedSetTheActivityTitle() {
		this.mActivity.onSectionAttached(1);
		assertEquals(this.mActivity.getActionBar().getTitle(), this.mActivity.getString(R.string.title_section1));

		this.mActivity.onSectionAttached(2);
		assertEquals(this.mActivity.getActionBar().getTitle(), this.mActivity.getString(R.string.title_section2));

		this.mActivity.onSectionAttached(3);
		assertEquals(this.mActivity.getActionBar().getTitle(), this.mActivity.getString(R.string.title_section3));
	}

	/*public void testShouldonNavigationDrawerItemSelectedSetInstitutionListFragment() {
		this.mActivity.onNavigationDrawerItemSelected(0);

		this.mActivity.getFragmentManager().
	}*/
}
