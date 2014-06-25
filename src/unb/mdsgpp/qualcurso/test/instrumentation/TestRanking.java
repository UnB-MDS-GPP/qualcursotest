package unb.mdsgpp.qualcurso.test.instrumentation;

import java.util.HashMap;

import unb.mdsgpp.qualcurso.EvaluationDetailFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.RankingFragment;
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

public class TestRanking  extends ActivityInstrumentationTestCase2<MainActivity>  {
	private MainActivity mActivity;
	private Instrumentation mInstrumentation;

	public TestRanking() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
		this.mInstrumentation = getInstrumentation();
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

	public void testShouldNotAllowRankWithoutIndicator() {
		openDrawerOptionAt(2);
		Fragment rank = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		
		final AutoCompleteTextView course = (AutoCompleteTextView) rank.getView().findViewById(R.id.autoCompleteTextView);
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		course.requestFocus();
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("engenharia");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
		mInstrumentation.waitForIdleSync();

		ListView evaluationList = (ListView) rank.getView().findViewById(R.id.evaluationList);
		assertNull(evaluationList.getAdapter());
	}
	public void testShouldShowRank(){
		openDrawerOptionAt(2);
		Fragment rank = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		final Spinner indicatorSpinner = (Spinner) rank.getView().findViewById(R.id.field);
		final Spinner yearSpinner = (Spinner) rank.getView().findViewById(R.id.year);
		final AutoCompleteTextView course = (AutoCompleteTextView) rank.getView().findViewById(R.id.autoCompleteTextView);
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				indicatorSpinner.setSelection(1);
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
		mInstrumentation.sendStringSync("engenharia");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
		mInstrumentation.waitForIdleSync();
		ListView evaluationList = (ListView) rank.getView().findViewById(R.id.evaluationList);
		assertNotNull(evaluationList.getAdapter());
		assertEquals("UFRN",((HashMap<String, String>)evaluationList.getAdapter().getItem(0)).get("acronym"));
		
	}
	public void testShouldSeeEvaluationListFragmentOnItemSelected(){
		openDrawerOptionAt(2);
		Fragment rank = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		final Spinner indicatorSpinner = (Spinner) rank.getView().findViewById(R.id.field);
		final Spinner yearSpinner = (Spinner) rank.getView().findViewById(R.id.year);
		final AutoCompleteTextView course = (AutoCompleteTextView) rank.getView().findViewById(R.id.autoCompleteTextView);
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				indicatorSpinner.setSelection(1);
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
		mInstrumentation.sendStringSync("engenharia");
		mInstrumentation.waitForIdleSync();
		if(!course.isFocused()){
			TouchUtils.clickView(this, course);
			mInstrumentation.waitForIdleSync();
		}
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_DOWN);
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER);
		
		mInstrumentation.waitForIdleSync();
		ListView evaluationList = (ListView) rank.getView().findViewById(R.id.evaluationList);
		assertNotNull(evaluationList.getAdapter());
		assertEquals("UFRN",((HashMap<String, String>)evaluationList.getAdapter().getItem(0)).get("acronym"));
		View v = evaluationList.getChildAt(0);
		TouchUtils.clickView(this, v);
		Fragment evaluation = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertTrue(evaluation instanceof EvaluationDetailFragment);		
	}
	
	public void testShouldTestOnSavedInstanceState() throws InterruptedException{
		openDrawerOptionAt(2);
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
		assertTrue(fragment instanceof RankingFragment);
	}
}
