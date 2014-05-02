package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;

import java.util.ArrayList;

import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Course;
import models.Institution;


public class TestInstitution extends TestCase{

	@Override
    protected void setUp() throws Exception {
		DataBaseStructures db = new DataBaseStructures();
		db.initDB();
		Institution institution = new Institution();
		institution.setAcronym("one");
		institution.save();
		institution = new Institution();
		institution.setAcronym("two");
		institution.save();
	}

	@Override
	protected void tearDown() throws Exception {
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
	}


	
	public void testShouldCreateNewInstitutionOnDataBase()
			throws ClassNotFoundException, SQLException {
		Institution institution = new Institution();
		institution.setAcronym("three");
		institution.save();
		assertEquals("three", Institution.last().getAcronym());
		institution = Institution.last();
		institution.delete();
	}

	
	public void testShouldCountAllInstitutionsOnDataBase()
			throws ClassNotFoundException, SQLException {
		int initialCount = Institution.count();
		Institution institution = new Institution();
		institution.setAcronym("other");
		institution.save();
		assertEquals(initialCount+1, Institution.count());
		assertEquals(Institution.getAll().size(), Institution.count());
		institution.delete();
	}
	
	
	public void testShouldGetAllInstitutionsOnDataBase()
			throws ClassNotFoundException, SQLException {
		assertEquals("one", Institution.getAll().get(0).getAcronym());
		assertEquals("two", Institution.getAll().get(1).getAcronym());
	}

	
	public void testShouldGetInstitutionOnDataBase()
			throws ClassNotFoundException, SQLException {
		assertEquals("one", Institution.get(1).getAcronym());
		assertEquals(Institution.getAll().get(0).getAcronym(), Institution.get(1).getAcronym());
	}
	
	
	public void testShouldGetFirstInstitutionOnDataBase()
			throws ClassNotFoundException, SQLException {
		assertEquals("one", Institution.first().getAcronym());
		assertEquals(Institution.getAll().get(0).getAcronym(), Institution
				.first().getAcronym());
	}

	
	public void testShouldGetLastInstitutionOnDataBase()
			throws ClassNotFoundException, SQLException {
		assertEquals("two", Institution.last().getAcronym());
		assertEquals(Institution.getAll().get(1).getAcronym(), Institution
				.last().getAcronym());
	}
	
	
	public void testShouldGetInstitutionCoursesOnDataBase()
			throws ClassNotFoundException, SQLException {
		Institution institution = new Institution();
		institution.setAcronym("three");
		institution.save();
		Course course = new Course();
		course.setName("Java");
		course.save();
		institution.addCourse(course);
		Course course1 = new Course();
		course1.setName("Phyton");
		course1.save();
		institution.addCourse(course1);
		assertEquals("Java", Institution.last().getCourses().get(0).getName());
		assertEquals("Phyton", Institution.last().getCourses().get(1).getName());
		assertEquals(2, Institution.last().getCourses().size());
		institution.delete();
		course.delete();
		course1.delete();
	}
	
	
	public void testShouldCreateInstitutionCourseOnDataBase()
			throws ClassNotFoundException, SQLException {
		Institution institution = new Institution();
		institution.setAcronym("three");
		institution.save();
		Course course = new Course();
		course.setName("Java");
		course.save();
		institution.addCourse(course);
		assertEquals("Java", Institution.last().getCourses().get(0).getName());
		assertEquals(course.getId(), Institution.last().getCourses().get(0).getId());
		institution.delete();
		course.delete();
	}

	
	public void testShouldGetInstitutionsWithWhereOnDataBase()
			throws ClassNotFoundException, SQLException {
		Institution institution = new Institution();
		institution.setAcronym("Institution AA");
		institution.save();

		Institution institution1 = new Institution();
		institution1.setAcronym("Institution BB");
		institution1.save();

		ArrayList<Institution> institutions1 = Institution.getWhere("acronym", "Institution", true);

		ArrayList<Institution> institutions2 = Institution.getWhere("acronym", "AA", true);
		
		ArrayList<Institution> institutions3 = Institution.getWhere("acronym", "Institution AA", false);
		
		assertEquals(2, institutions1.size());
		assertEquals(1, institutions2.size());
		assertEquals(1, institutions3.size());
		institution.delete();
		institution1.delete();
	}
}
