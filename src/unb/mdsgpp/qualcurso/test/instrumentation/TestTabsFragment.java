package unb.mdsgpp.qualcurso.test.instrumentation;

import java.util.concurrent.TimeoutException;

import unb.mdsgpp.qualcurso.CourseListFragment;
import unb.mdsgpp.qualcurso.InstitutionListFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.TabsFragment;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;

public class TestTabsFragment extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;
	private Instrumentation mInstrumentation;
	
	public TestTabsFragment() {
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
	
	public void testShouldSearchAnInstitution(){
		openDrawerOptionAt(0);
		mInstrumentation.invokeMenuActionSync(mActivity, R.id.action_search, 0);
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("USP");
		mInstrumentation.waitForIdleSync();
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_1);
		ListView lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		assertEquals("USP", ((TextView)lv.getChildAt(0)).getText());
	}
	
	public void testShouldSearchACourse() throws InterruptedException{
		openDrawerOptionAt(0);
		Fragment tabs = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		TabWidget v = (TabWidget) tabs.getView().findViewById(android.R.id.tabs);
		TouchUtils.clickView(this, v.getChildTabViewAt(1));
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_2);
		assertTrue(fragment instanceof CourseListFragment);
		mInstrumentation.invokeMenuActionSync(mActivity, R.id.action_search, 0);
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_UP);
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_LEFT);
		mInstrumentation.waitForIdleSync();
		mInstrumentation.sendStringSync("eng");
		mInstrumentation.waitForIdleSync();
		Thread.sleep(500);
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.tab_2);
		ListView lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		
			assertEquals("ENGENHARIA", ((TextView)lv.getChildAt(0)).getText());
	}
	
	
}
