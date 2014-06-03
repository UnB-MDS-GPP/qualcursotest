package unb.mdsgpp.qualcurso.test;

import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestRankingFragment extends AndroidTestCase {
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}

	public void testShouldGetNewInstanceOfRankingFragment(){
		Fragment fragment = new RankingFragment();
		
		TestRankingFragment sbif = (TestRankingFragment) fragment;
		assertNotNull(sbif);
	}
}
