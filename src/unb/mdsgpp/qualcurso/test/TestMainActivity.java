package unb.mdsgpp.qualcurso.test;

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

	public void testShoudOpenSearchByIndicatorFragment() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);

		TouchUtils.clickView(this, nl.getChildAt(1));
		Fragment search = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		assertTrue(search instanceof SearchByIndicatorFragment);
	}

	public void testShoudSearchByIndicatorFragmentDisableUpperlimitWhenMaxCheckboxIsEnable() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);

		TouchUtils.clickView(this, nl.getChildAt(1));
		Fragment search = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		View maximumCheckbox = search.getView().findViewById(R.id.maximum);
		EditText secondNumber = (EditText) search.getView().findViewById(R.id.secondNumber);

		TouchUtils.clickView(this, maximumCheckbox);

		assertFalse(secondNumber.isEnabled());
	}

	public void testShoudSearchByIndicatorFragmentSetZeroToFirstNumberAndCheckMaximumIfTheUserJustPressSearch() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);

		TouchUtils.clickView(this, nl.getChildAt(1));
		Fragment search = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		View buttonSearch = search.getView().findViewById(R.id.buttonSearch);

		CheckBox maximumCheckbox = (CheckBox) search.getView().findViewById(R.id.maximum);
		EditText firstNumber = (EditText) search.getView().findViewById(R.id.firstNumber);

		TouchUtils.clickView(this, buttonSearch);

		assertEquals("0", firstNumber.getText().toString());
		assertTrue(maximumCheckbox.isChecked());
	}

	public void testShoudSearchByIndicatorFragmentSetListToInstitutionAndYearTo2010IfOnlyTheIndicatorIsSet() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);

		TouchUtils.clickView(this, nl.getChildAt(1));
		Fragment search = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		View buttonSearch = search.getView().findViewById(R.id.buttonSearch);

		Spinner listSelectionSpinner = (Spinner) search.getView().findViewById(R.id.course_institution);
		Spinner yearSpinner = (Spinner) search.getView().findViewById(R.id.year);
		final Spinner filterFieldSpinner = (Spinner) search.getView().findViewById(R.id.field);
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				filterFieldSpinner.requestFocus();
				filterFieldSpinner.setSelection(4);
			}
		});
		

		TouchUtils.clickView(this, buttonSearch);
		
		assertEquals(listSelectionSpinner.getAdapter().getCount()-1 , listSelectionSpinner.getSelectedItemPosition());
		assertEquals(yearSpinner.getAdapter().getCount()-1, yearSpinner.getSelectedItemPosition());
	}

	public void testShoudSearchByIndicatorFragmentListCoursesWithInstitutionsByItsFilters() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);

		TouchUtils.clickView(this, nl.getChildAt(1));
		Fragment search = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		final Spinner listSelectionSpinner = (Spinner) search.getView().findViewById(R.id.course_institution);
		final Spinner yearSpinner = (Spinner) search.getView().findViewById(R.id.year);
		final Spinner filterFieldSpinner = (Spinner) search.getView().findViewById(R.id.field);
		final EditText firstNumber = (EditText) search.getView().findViewById(R.id.firstNumber);
		final EditText secondNumber = (EditText) search.getView().findViewById(R.id.secondNumber);
		View buttonSearch = search.getView().findViewById(R.id.buttonSearch);
		

		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listSelectionSpinner.requestFocus();
				listSelectionSpinner.setSelection(1); // Course

				yearSpinner.requestFocus();
				yearSpinner.setSelection(2); // 2010

				filterFieldSpinner.requestFocus();
				filterFieldSpinner.setSelection(1); // tienial evaluation

				firstNumber.requestFocus();
				firstNumber.setText("5");

				secondNumber.requestFocus();
				secondNumber.setText("7");
			}
		});

		TouchUtils.clickView(this, buttonSearch);
		Fragment searchList = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.search_list);
		ListView resultList = (ListView) searchList.getView().findViewById(android.R.id.list);
		TouchUtils.clickView(this, resultList.getChildAt(0));

		this.mActivity = getActivity();
		searchList = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		resultList = (ListView) searchList.getView().findViewById(android.R.id.list);

		// TODO test if it is an Institution list
		assertTrue(searchList instanceof InstitutionListFragment);
	}
	
	public void testShould() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		
		TouchUtils.clickView(this, nl.getChildAt(2));
		Fragment rank = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		
		AutoCompleteTextView course = (AutoCompleteTextView) rank.getView().findViewById(R.id.autoCompleteTextView);
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				
			}
		});
	}
}
