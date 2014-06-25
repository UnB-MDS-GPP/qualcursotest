package unb.mdsgpp.qualcurso.test.instrumentation;

import helpers.Indicator;

import java.sql.Date;
import java.util.Calendar;

import models.Search;
import unb.mdsgpp.qualcurso.HistoryFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.SearchListFragment;
import android.app.Instrumentation;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.Surface;
import android.view.View;
import android.widget.ListView;

public class TestSearchHistory extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Instrumentation mInstrumentation;

	public TestSearchHistory() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
		this.mInstrumentation = getInstrumentation();
	}

	public void setUpBeforeClass() {
		Calendar c = Calendar.getInstance();
		Search s = new Search();
		s.setIndicator(Indicator.getIndicatorByValue("triennial_evaluation"));
		s.setMaxValue(10);
		s.setMinValue(5);
		s.setOption(1);
		s.setYear(2010);
		s.setDate(new Date(c.getTime().getTime()));
		s.save();
		s = new Search();
		s.setIndicator(Indicator.getIndicatorByValue("theses"));
		s.setMaxValue(20);
		s.setMinValue(10);
		s.setOption(0);
		s.setYear(2007);
		s.setDate(new Date(c.getTime().getTime()));
		s.save();
	}

	public void testPreConditions() {
		setUpBeforeClass();
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

	public void testShouldShowLastSearchesMade() {
		openDrawerOptionAt(3);
		Fragment history = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		ListView historyList = (ListView) history.getView().findViewById(
				R.id.listHistory);

		Search s = (Search) historyList.getAdapter()
				.getItem(0);
		assertEquals(s.getId(), Search.last().getId());
	}

	public void testShouldTheTenthSearchMade() {
		openDrawerOptionAt(3);
		Fragment history = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		ListView historyList = (ListView) history.getView().findViewById(
				R.id.listHistory);
		if (historyList.getAdapter().getCount() == 10 && Search.count() == 10) {
			Search s = (Search) historyList.getAdapter().getItem(9);
			assertEquals(s.getId(), Search.first().getId());
		}
	}

	public void testShouldShowIntensSearch() {
		openDrawerOptionAt(3);
		Fragment history = this.mActivity.getSupportFragmentManager()
				.findFragmentById(R.id.container);
		ListView historyList = (ListView) history.getView().findViewById(
				R.id.listHistory);
		TouchUtils.clickView(this, historyList);
		Fragment course_institution = this.mActivity
				.getSupportFragmentManager().findFragmentById(R.id.container);
		assertTrue(course_institution instanceof SearchListFragment);

	}
	
	public void testShouldTestOnSavedInstanceState() throws InterruptedException{
		openDrawerOptionAt(3);
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
		assertTrue(fragment instanceof HistoryFragment);
	}
}
