package unb.mdsgpp.qualcurso.test.instrumentation;

import unb.mdsgpp.qualcurso.CompareChooseFragment;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import unb.mdsgpp.qualcurso.SearchByIndicatorFragment;
import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

public class TestCompare  extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;
	private Instrumentation mInstrumentation;
	
	public TestCompare(){
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
	
	public void testShoudOpenCompareChooseFragment() {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);

		assertTrue(compare instanceof CompareChooseFragment);
	}
	
	public void testShouldNotAllowCompareWithoutYear() {
		openDrawerOptionAt(4);
		Fragment compare = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		
		final AutoCompleteTextView course = (AutoCompleteTextView) compare.getView().findViewById(R.id.autoCompleteTextView);
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				course.setText("engenharia");
				course.requestFocus();

			}
		});
	}
}
