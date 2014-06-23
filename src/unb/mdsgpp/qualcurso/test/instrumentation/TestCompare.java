package unb.mdsgpp.qualcurso.test.instrumentation;

import java.util.HashMap;

import models.Institution;
import unb.mdsgpp.qualcurso.CompareChooseFragment;
import unb.mdsgpp.qualcurso.CompareShowFragment;
import unb.mdsgpp.qualcurso.EvaluationDetailFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.RankingFragment;
import unb.mdsgpp.qualcurso.SearchByIndicatorFragment;
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
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Spinner;

public class TestCompare extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Instrumentation mInstrumentation;

	public TestCompare() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
		this.mInstrumentation = getInstrumentation();
	}

	public void openDrawerOptionAt(int position) {
		Fragment nd = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		DrawerLayout mDrawerLayout = (DrawerLayout) mActivity
				.findViewById(R.id.drawer_layout);

		if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			View v = nd.getView().focusSearch(View.FOCUS_FORWARD);
			TouchUtils.clickView(this, v);
		}
		ListView nl = (ListView) nd.getView().findViewById(
				R.id.navigation_list_view);
		TouchUtils.clickView(this, nl.getChildAt(position));
	}

	public void testShoudOpenCompareChooseFragment() {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);

		assertTrue(compare instanceof CompareChooseFragment);
	}

	public void testShouldShowInstitutionListWithoutYear() {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);

		final AutoCompleteTextView course = (AutoCompleteTextView) compare
				.getView().findViewById(R.id.autoCompleteTextView);

		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		course.requestFocus();
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("artes");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
		mInstrumentation.waitForIdleSync();

		ListView institutionList = (ListView) compare.getView().findViewById(
				R.id.institutionList);
		assertNotNull(institutionList.getAdapter());
	}

	public void testShouldShowCompareFragmentWithTwoInstitutions() {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		final Spinner yearSpinner = (Spinner) compare.getView().findViewById(
				R.id.compare_year);
		final AutoCompleteTextView course = (AutoCompleteTextView) compare
				.getView().findViewById(R.id.autoCompleteTextView);

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				yearSpinner.setSelection(1);
			}
		});
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		course.requestFocus();
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("artes");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);

		mInstrumentation.waitForIdleSync();
		ListView institutionList = (ListView) compare.getView().findViewById(
				R.id.institutionList);
		assertNotNull(institutionList.getAdapter());
		assertEquals("UFBA", ((Institution) institutionList.getAdapter()
				.getItem(0)).getAcronym());
		View v1 = institutionList.getChildAt(0);
		View v2 = institutionList.getChildAt(1);
		TouchUtils.clickView(this, v1);
		mInstrumentation.waitForIdleSync();
		TouchUtils.clickView(this, v1);
		mInstrumentation.waitForIdleSync();
		TouchUtils.clickView(this, v1);
		mInstrumentation.waitForIdleSync();
		TouchUtils.clickView(this, v2);
		Fragment compare2 = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		assertTrue(compare2 instanceof CompareShowFragment);
	}

	public void testShouldSetLastYearIfJustAutoCompleteWasWrite() throws InterruptedException {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		Spinner yearSpinner = (Spinner) compare.getView().findViewById(
				R.id.compare_year);
		final AutoCompleteTextView course = (AutoCompleteTextView) compare
				.getView().findViewById(R.id.autoCompleteTextView);

		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		course.requestFocus();
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("artes");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		Thread.sleep(100);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
		mInstrumentation.waitForIdleSync();

		assertEquals(
				yearSpinner.getAdapter()
						.getItem(yearSpinner.getAdapter().getCount() - 1)
						.toString(), yearSpinner.getSelectedItem().toString());
	}
	
	public void testShouldTestOnSavedInstanceState() throws InterruptedException{
		openDrawerOptionAt(4);
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
		assertTrue(fragment instanceof CompareChooseFragment);
	}
}
