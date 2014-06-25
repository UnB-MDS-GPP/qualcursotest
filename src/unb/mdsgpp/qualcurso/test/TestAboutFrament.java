package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.AboutFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestAboutFrament extends AndroidTestCase {
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
	}

	public void testShouldGetNewInstanceOfCompareChooseFragment(){
		Fragment fragment = new AboutFragment();

		AboutFragment sbif = (AboutFragment) fragment;
		assertNotNull(sbif);
	}
}
