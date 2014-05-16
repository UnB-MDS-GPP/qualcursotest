package unb.mdsgpp.qualcurso.test;

import unb.mdsgpp.qualcurso.CourseListFragment;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;

public class TestCourseListFragment extends AndroidTestCase{
	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		
	}
	
	public void testShouldGetNewInstanceOfCourseListFragment(){
		Fragment fragment = CourseListFragment.newInstance(1);
		CourseListFragment clf = (CourseListFragment) fragment;
		assertEquals(1, clf.getArguments().getInt("idInstitution"));
	}
	
}
