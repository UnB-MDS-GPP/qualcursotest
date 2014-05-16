package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.InstitutionListFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestInstitutionListFragment extends AndroidTestCase{
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		
	}
	
	public void testShouldGetNewInstanceOfInstitutionListFragment(){
		Fragment fragment = InstitutionListFragment.newInstance(1);
		InstitutionListFragment ilf = (InstitutionListFragment) fragment;
		assertEquals(1, ilf.getArguments().getInt("idCourse"));
	}
	
}
