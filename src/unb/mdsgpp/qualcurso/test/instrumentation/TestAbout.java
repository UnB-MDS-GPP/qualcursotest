package unb.mdsgpp.qualcurso.test.instrumentation;

import unb.mdsgpp.qualcurso.AboutFragment;
import unb.mdsgpp.qualcurso.CompareChooseFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.QualCurso;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.text.method.Touch;
import android.view.KeyEvent;
import android.view.Menu;
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

		assertTrue(fragment instanceof AboutFragment);
	}
}
