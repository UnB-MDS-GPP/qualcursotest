package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.SearchByIndicatorFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestSearchByIndicatorFragment extends AndroidTestCase {
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}

	public void testShouldGetNewInstanceOfCourseListFragment(){
		Fragment fragment = new SearchByIndicatorFragment();
		
		SearchByIndicatorFragment sbif = (SearchByIndicatorFragment) fragment;
		assertNotNull(sbif);
	}
}
