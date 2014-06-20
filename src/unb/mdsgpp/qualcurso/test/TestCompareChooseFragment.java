package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.CompareChooseFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestCompareChooseFragment extends AndroidTestCase {
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}
	
	public void testShouldGetNewInstanceOfCompareChooseFragment(){
		Fragment fragment = new CompareChooseFragment();
		
		CompareChooseFragment sbif = (CompareChooseFragment) fragment;
		assertNotNull(sbif);
	}
}
