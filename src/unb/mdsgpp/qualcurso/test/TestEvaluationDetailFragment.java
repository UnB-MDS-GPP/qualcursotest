package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.EvaluationDetailFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestEvaluationDetailFragment extends AndroidTestCase{
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		
	}
	
	public void testShouldGetNewInstanceOfEvaluationDetailFragment(){
		Fragment fragment = EvaluationDetailFragment.newInstance(1,2,2007);
		EvaluationDetailFragment edf = (EvaluationDetailFragment) fragment;
		assertEquals(1, edf.getArguments().getInt("idInstitution"));
		assertEquals(2, edf.getArguments().getInt("idCourse"));
	}
	
}