package unb.mdsgpp.qualcurso.test;

import android.test.ActivityInstrumentationTestCase2;
import unb.mdsgpp.qualcurso.InstitutionListFragment;
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
		assertEquals(this.mActivity.getString(R.string.app_name), this.mActivity.getActionBar().getTitle());
		
	}

	public void testShouldonNavigationDrawerItemSelectedSetInstitutionListFragment() {
		//this.mActivity.onNavigationDrawerItemSelected(0);
		assertEquals(InstitutionListFragment.class, this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container).getClass());
	}
}
