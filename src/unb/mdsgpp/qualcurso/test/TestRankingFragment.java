package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.RankingFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestRankingFragment extends AndroidTestCase {
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}

	public void testShouldGetNewInstanceOfRankingFragment(){
		Fragment fragment = new RankingFragment();
		
		RankingFragment sbif = (RankingFragment) fragment;
		assertNotNull(sbif);
	}
}
