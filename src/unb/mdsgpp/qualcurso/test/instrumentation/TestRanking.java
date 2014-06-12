package unb.mdsgpp.qualcurso.test.instrumentation;

import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

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

	public void testShouldNotAllowRankWithoutIndicator() {
		Fragment nd = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		ListView nl = (ListView)nd.getView().findViewById(R.id.navigation_list_view);
		
		TouchUtils.clickView(this, nl.getChildAt(2));
		Fragment rank = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
		
		final AutoCompleteTextView course = (AutoCompleteTextView) rank.getView().findViewById(R.id.autoCompleteTextView);
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				course.setText("eng");
			}
		});
		View v = course.getTouchables().get(0);
		TouchUtils.clickView(this, v);
		ListView evaluationList = (ListView) rank.getView().findViewById(R.id.evaluationList);
		assertNull(evaluationList.getAdapter());
	}
}
