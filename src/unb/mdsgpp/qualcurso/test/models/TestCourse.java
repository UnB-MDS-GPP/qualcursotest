package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import unb.mdsgpp.qualcurso.QualCurso;
import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
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

	public void testShouldGetCoursesByEvaluationFilter() {
		ArrayList<Course> courses;
		Evaluation [] eva = this.buildEvaluation();

		courses = Course.getCoursesByEvaluationFilter("triennial_evaluation", "2007", "7", "9");
		assertEquals(1, courses.size());
		assertEquals("name course 1", courses.get(0).getName());

		courses = Course.getCoursesByEvaluationFilter("triennial_evaluation", "2010", "2", "5");
		assertEquals(1, courses.size());
		assertEquals("name course 2", courses.get(0).getName());

		this.destroyEvaluation(eva);
	}

	public void testShouldGetInstitutionsByEvaluationFilter() {
		ArrayList<Institution> institutions;
		Evaluation [] eva = this.buildEvaluation();

		institutions = Course.getInstitutionsByEvaluationFilter(Integer.toString(eva[0].getIdCourse()), "triennial_evaluation", "2007", "7", "9");
		assertEquals(1, institutions.size());
		assertEquals("name institution 1", institutions.get(0).getAcronym());

		institutions = Course.getInstitutionsByEvaluationFilter(Integer.toString(eva[1].getIdCourse()), "triennial_evaluation", "2010", "2", "5");
		assertEquals(1, institutions.size());
		assertEquals("name institution 2", institutions.get(0).getAcronym());

		this.destroyEvaluation(eva);
	}

	private Evaluation [] buildEvaluation() {
		Evaluation [] response = new Evaluation[2];
		/*  First Evaluation */
		Institution institution = new Institution();
		institution.setAcronym("name institution 1");
		institution.save();

		Course course1 = new Course();
		course1.setName("name course 1");
		course1.save();

		Article article = new Article();
		article.setPublishedJournals(1);
		article.save();

		Book book = new Book();
		book.setIntegralText(1);
		book.save();

		Evaluation evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course1.getId());
		evaluation.setYear(Integer.parseInt("2007"));
		evaluation.setModality("modality");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2000"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2010"));
		evaluation.setTriennialEvaluation(Integer.parseInt("8"));
		evaluation.setPermanentTeachers(Integer.parseInt("1"));
		evaluation.setTheses(Integer.parseInt("2"));
		evaluation.setDissertations(Integer.parseInt("3"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("5"));
		evaluation.save();

		response[0] = evaluation;

		/* Second Evaluation */
		institution = new Institution();
		institution.setAcronym("name institution 2");
		institution.save();

		Course course2 = new Course();
		course2.setName("name course 2");
		course2.save();

		article = new Article();
		article.setPublishedJournals(Integer.parseInt("2"));
		article.save();

		book = new Book();
		book.setIntegralText(Integer.parseInt("2"));
		book.save();

		evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course2.getId());
		evaluation.setYear(Integer.parseInt("2010"));
		evaluation.setModality("modality 2");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2001"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2011"));
		evaluation.setTriennialEvaluation(Integer.parseInt("4"));
		evaluation.setPermanentTeachers(Integer.parseInt("2"));
		evaluation.setTheses(Integer.parseInt("3"));
		evaluation.setDissertations(Integer.parseInt("4"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("6"));
		evaluation.save();

		response[1] = evaluation;

		return response;
	}

	private void destroyEvaluation(Evaluation [] eva) {
		Course course1 =  Course.get(eva[0].getIdCourse());
		Course course2 =  Course.get(eva[1].getIdCourse());

		course1.delete();
		course2.delete();

		Institution institution1 =  Institution.get(eva[0].getIdInstitution());
		Institution institution2 =  Institution.get(eva[1].getIdInstitution());

		institution1.delete();
		institution2.delete();
		
		Evaluation evaluation1 = Evaluation.get(eva[0].getId());
		Evaluation evaluation2 = Evaluation.get(eva[1].getId());
		evaluation1.delete();
		evaluation2.delete();
	}
}
