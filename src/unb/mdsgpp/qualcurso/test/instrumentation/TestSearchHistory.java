package unb.mdsgpp.qualcurso.test.instrumentation;

import helpers.Indicator;

import java.sql.Date;
import java.util.Calendar;

import models.Evaluation;
import models.Search;
import unb.mdsgpp.qualcurso.MainActivity;
import unb.mdsgpp.qualcurso.R;
import android.app.Instrumentation;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.ListView;

public class TestSearchHistory extends ActivityInstrumentationTestCase2<MainActivity>{

		private MainActivity mActivity;
		private Instrumentation mInstrumentation;

		public TestSearchHistory(){
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
		
		public void testShouldShowLastSearchesMade(){
			openDrawerOptionAt(3);
			Fragment history = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
			ListView historyList = (ListView)history.getView().findViewById(R.id.listHistory);
			int t;
			Search s = (Search)historyList.getAdapter().getItem(Search.count()-1);
			assertEquals(s.getId(), Search.last().getId());
		}
		
		public void testShouldTheTenthSearchMade(){
			openDrawerOptionAt(3);
			Fragment history = this.mActivity.getSupportFragmentManager().findFragmentById(R.id.container);
			ListView historyList = (ListView)history.getView().findViewById(R.id.listHistory);
			if(historyList.getAdapter().getCount() == 10){
				Search s = (Search)historyList.getAdapter().getItem(9);
				assertEquals(s.getId(), Search.last().getId());
			}
		}
}
