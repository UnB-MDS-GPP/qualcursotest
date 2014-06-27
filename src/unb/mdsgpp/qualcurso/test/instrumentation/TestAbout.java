package unb.mdsgpp.qualcurso.test.instrumentation;

import unb.mdsgpp.qualcurso.AboutFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.widget.ListView;
import unb.mdsgpp.qualcurso.R;


public class TestAbout extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Instrumentation mInstrumentation;

	public TestAbout() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
		this.mInstrumentation = getInstrumentation();
	}

	public void testShouldOpenAboutFragmentOnMenu() throws InterruptedException {
		openDrawerOptionAt(0);

		// Click the menu option
		sendKeys(KeyEvent.KEYCODE_MENU);
		mInstrumentation.invokeMenuActionSync(mActivity, R.id.action_about, 0);

		mInstrumentation.waitForIdleSync();

		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		if(mActivity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mInstrumentation.waitForIdleSync();
			mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mInstrumentation.waitForIdleSync();
		} else {
			mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			mInstrumentation.waitForIdleSync();
			mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			mInstrumentation.waitForIdleSync();
		}

		while(mActivity.getWindowManager().getDefaultDisplay().getRotation() != Surface.ROTATION_0){
			Thread.sleep(500);
		}

		mInstrumentation.waitForIdleSync();
		assertTrue(fragment instanceof AboutFragment);
	}

	public void openDrawerOptionAt(int position){
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		DrawerLayout mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);

		if(!mDrawerLayout.isDrawerOpen(GravityCompat.START)){
			View v = nd.getView().focusSearch(View.FOCUS_FORWARD);
			TouchUtils.clickView(this, v);
		}
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		TouchUtils.clickView(this, nl.getChildAt(position));
	}
}
