package unb.mdsgpp.qualcurso.test.instrumentation;

import unb.mdsgpp.qualcurso.AboutFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.Surface;
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
}
