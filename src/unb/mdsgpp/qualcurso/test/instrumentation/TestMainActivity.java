package unb.mdsgpp.qualcurso.test.instrumentation;

import java.util.ArrayList;

import models.Evaluation;
import models.Institution;
import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import unb.mdsgpp.qualcurso.CourseListFragment;
import unb.mdsgpp.qualcurso.EvaluationDetailFragment;
import unb.mdsgpp.qualcurso.InstitutionListFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.SearchByIndicatorFragment;
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

	public void testShouldOnSectionAttachedSetTheActivityTitle() {
		this.mActivity.onSectionAttached(1);
		assertEquals(this.mActivity.getString(R.string.app_name), this.mActivity.getActionBar().getTitle());
	}

	public void testShouldonNavigationDrawerItemSelectedSetInstitutionListFragment() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView lv = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		TouchUtils.clickView(this, lv.getChildAt(0));
		assertEquals(TabsFragment.class, this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container).getClass());
	}
	
	
	public void testShouldOnClickChangeFragments(){
		this.mActivity = getActivity();
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		TouchUtils.clickView(this, nl.getChildAt(0));
		Fragment fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertTrue(fragment instanceof TabsFragment);
		ListView lv = (ListView)fragment.getView().findViewById(android.R.id.list);
		assertEquals("UFBA", ((TextView)lv.getChildAt(0)).getText());
		TouchUtils.clickView(this, lv.getChildAt(0));
		this.mActivity = getActivity();
		fragment = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		assertEquals(CourseListFragment.class, fragment.getClass());
	}
	
	public void testShouldOnSelectionsShowEvaluation(){
		this.mActivity = getActivity();
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		TouchUtils.clickView(this, nl.getChildAt(0));
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
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		TouchUtils.clickView(this, nl.getChildAt(0));
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
}
