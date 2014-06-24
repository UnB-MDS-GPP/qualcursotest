package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.CompareShowFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestCompareShowFragment extends AndroidTestCase {
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}
	
	public void testShouldGetNewInstanceOfCompareShowFragment(){
		Fragment fragment = new CompareShowFragment();
		
		CompareShowFragment sbif = (CompareShowFragment) fragment;
		assertNotNull(sbif);
	}
}
