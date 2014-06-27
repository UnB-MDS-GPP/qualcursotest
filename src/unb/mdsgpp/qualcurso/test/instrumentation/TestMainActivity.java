package unb.mdsgpp.qualcurso.test.instrumentation;

import models.Evaluation;
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
import android.widget.TabWidget;
import android.widget.TextView;
import unb.mdsgpp.qualcurso.CourseListFragment;
import unb.mdsgpp.qualcurso.EvaluationDetailFragment;
import unb.mdsgpp.qualcurso.InstitutionListFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.TabsFragment;

public class TestMainActivity extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Instrumentation mInstrumentation;

	public TestMainActivity() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);
		this.mActivity = getActivity();
		this.mInstrumentation = getInstrumentation();
	}
	
	public void setUpBeforeClass(){
		Evaluation evaluation = new Evaluation();
	}
	
	public void testPreConditions(){
		setUpBeforeClass();
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

	public void testShouldOnSectionAttachedSetTheActivityTitle() {
		this.mActivity.onSectionAttached(1);
		assertEquals(this.mActivity.getString(R.string.app_name).toString(), this.mActivity.getActionBar().getTitle().toString());
	}

	public void testShouldonNavigationDrawerItemSelectedSetInstitutionListFragment() {
		openDrawerOptionAt(0);
		assertEquals(TabsFragment.class, this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container).getClass());
	}
	
	
	public void testShouldOnClickChangeFragments(){
		this.mActivity = getActivity();
		openDrawerOptionAt(0);
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertTrue(fragment instanceof TabsFragment);
		ListView lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		assertEquals("ALFA", ((TextView)lv.getChildAt(0)).getText());
		TouchUtils.clickView(this, lv.getChildAt(0));
		this.mActivity = getActivity();
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertEquals(CourseListFragment.class, fragment.getClass());
	}
	
	public void testShouldOnSelectionsShowEvaluation(){
		this.mActivity = getActivity();
		openDrawerOptionAt(0);
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		ListView lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		TouchUtils.clickView(this, lv.getChildAt(0));
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		TouchUtils.clickView(this, lv.getChildAt(0));
		this.mActivity = getActivity();
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertEquals(EvaluationDetailFragment.class, fragment.getClass());
	}
	
	public void testShouldOnTabSelectionChangeFragment(){
		openDrawerOptionAt(0);
		Fragment tabs = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		TabWidget v = (TabWidget) tabs.getView().findViewById(android.R.id.tabs);
		v.getChildTabViewAt(0);
		TouchUtils.clickView(this, v.getChildTabViewAt(0));
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_1);
		assertTrue(fragment instanceof InstitutionListFragment);
		TouchUtils.clickView(this, v.getChildTabViewAt(1));
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_2);
		assertTrue(fragment instanceof CourseListFragment);
	}
	
	public void testShouldTestOnSavedInstanceState() throws InterruptedException{
		openDrawerOptionAt(0);
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
		assertTrue(fragment instanceof TabsFragment);
	}
	
	public void testShouldTestCourseListFragmentOnSavedInstanceState() throws InterruptedException{
		openDrawerOptionAt(0);
		Fragment tabs = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		TabWidget v = (TabWidget) tabs.getView().findViewById(android.R.id.tabs);
		v.getChildTabViewAt(0);
		TouchUtils.clickView(this, v.getChildTabViewAt(0));
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_1);
		assertTrue(fragment instanceof InstitutionListFragment);
		TouchUtils.clickView(this, v.getChildTabViewAt(1));
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_2);
		Fragment course = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
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
		assertTrue(fragment instanceof CourseListFragment);
	}
}
