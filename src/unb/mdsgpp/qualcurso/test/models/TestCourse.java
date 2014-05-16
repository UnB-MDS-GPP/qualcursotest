package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import unb.mdsgpp.qualcurso.QualCurso;
import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;
import models.Course;
import models.Institution;

public class TestCourse extends AndroidTestCase{

	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		db.initDB();
		Course course = new Course();
		course.setName("one");
		course.save();

		course = new Course();
		course.setName("two");
		course.save();
	}


	@Override
	protected void tearDown() throws Exception{

	}



	
	public void testShouldCreateNewCourseOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Course.count();

		Course course = new Course();
		course.setName("A new course");

		assertEquals(true, course.save());
		assertEquals(initialCount, Course.count()-1);
		course.delete();
	}

	
	public void testShouldCountCoursesOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Course.count();
		Course course = new Course();
		course.setName("Course");
		course.save();
		assertEquals(initialCount+1, (int)Course.count());
		assertEquals(Course.getAll().size(), (int)Course.count());
		course.delete();
	}

	
	public void testShouldGetCourseOnDataBase() throws ClassNotFoundException, SQLException {
		Course course1 = new Course();
		course1.setName("A new course");
		course1.save();

		Course course2 = Course.get(Course.last().getId());

		assertEquals(course1.getName(), course2.getName());
		course1.delete();
	}

	
	public void testShouldGetAllCoursesOnDataBase() throws ClassNotFoundException, SQLException {
		int total = Course.count();
		assertEquals(total, Course.getAll().size());
		assertEquals("", Course.first().get("test"));
	}
	
	
	public void testShouldGetTheFirstCourseOnDataBase() throws ClassNotFoundException, SQLException {
		Course first = Course.first();
		assertEquals(first.getName(), Course.getAll().get(0).getName());
		assertEquals("one", Course.first().toString());
	}

	
	public void testShouldGetTheLastCourseOnDataBase() throws ClassNotFoundException, SQLException {
		Course last = Course.last();

		ArrayList<Course> courses = Course.getAll();
		assertEquals(last.getName(), courses.get(courses.size()-1).getName());
	}
	
	
	public void testShouldGetCourseInstitutionsOnDataBase()
			throws ClassNotFoundException, SQLException {
		Course course = new Course();
		course.setName("Java");
		course.save();
		Institution institution = new Institution();
		institution.setAcronym("one");
		institution.save();
		Institution institution1 = new Institution();
		institution1.setAcronym("two");
		institution1.save();
		course.addInstitution(institution);
		course.addInstitution(institution1);
		assertEquals("one", Course.last().getInstitutions().get(0).getAcronym());
		assertEquals("two", Course.last().getInstitutions().get(1).getAcronym());
		assertEquals(2, Course.last().getInstitutions().size());
		course.delete();
		institution.delete();
		institution1.delete();
	}
	
	
	public void testShouldCreateCourseInstitutionOnDataBase()
			throws ClassNotFoundException, SQLException {
		Course course = new Course();
		course.setName("Java");
		course.save();
		Institution institution = new Institution();
		institution.setAcronym("one");
		institution.save();
		course.addInstitution(institution);
		assertEquals("one", Course.last().getInstitutions().get(0).getAcronym());
		assertEquals(course.getId(), Institution.last().getCourses().get(0).getId());
		course.delete();
		institution.delete();
	}
	
	
	public void testShouldGetCoursesWithWhereOnDataBase() throws ClassNotFoundException, SQLException {
		Course course = new Course();
		course.setName("Course AA");
		course.save();

		Course course1 = new Course();
		course1.setName("Course BB");
		course1.save();

		ArrayList<Course> courses1 = Course.getWhere("name", "Course", true);

		ArrayList<Course> courses2 = Course.getWhere("name", "AA", true);

		assertEquals(2, courses1.size());
		assertEquals(1, courses2.size());
		course.delete();
		course1.delete();
	}
}
