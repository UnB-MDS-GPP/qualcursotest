package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;
import android.os.Bundle;
import android.os.Parcel;
import android.test.AndroidTestCase;
import helpers.Indicator;

import java.util.ArrayList;

import unb.mdsgpp.qualcurso.QualCurso;
import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import models.Search;

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

	public void testShouldGetCourseInstitutionsOnDataBaseByYear() 	throws ClassNotFoundException, SQLException {
		Evaluation [] eva = this.buildEvaluation();

		assertEquals("name institution 3", Course.last().getInstitutions(2007).get(0).getAcronym());
		assertEquals("name institution 2", Course.last().getInstitutions(2010).get(0).getAcronym());

		destroyEvaluation(eva);
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
		Search search = new Search();
		search.setIndicator(Indicator.getIndicatorByValue("triennial_evaluation"));
		search.setYear(2007);
		search.setMinValue(19);
		search.setMaxValue(21);
		courses = Course.getCoursesByEvaluationFilter(search);
		assertEquals(1, courses.size());
		assertEquals("name course 1", courses.get(0).getName());
		search.setMaxValue(-1);
		courses = Course.getCoursesByEvaluationFilter(search);
		assertEquals(2, courses.size());
		assertEquals("name course 1", courses.get(0).getName());
		assertEquals("name course 2", courses.get(1).getName());
		search.setYear(2010);
		search.setMaxValue(31);
		courses = Course.getCoursesByEvaluationFilter(search);
		assertEquals(1, courses.size());
		assertEquals("name course 2", courses.get(0).getName());
		
		this.destroyEvaluation(eva);
	}

	public void testShouldGetInstitutionsByEvaluationFilter() {
		ArrayList<Institution> institutions;
		Evaluation [] eva = this.buildEvaluation();
		Search search = new Search();
		search.setIndicator(Indicator.getIndicatorByValue("triennial_evaluation"));
		search.setYear(2007);
		search.setMinValue(19);
		search.setMaxValue(21);
		institutions = Course.getInstitutionsByEvaluationFilter(eva[0].getIdCourse(), search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 1", institutions.get(0).getAcronym());
		search.setMaxValue(-1);
		institutions = Course.getInstitutionsByEvaluationFilter(eva[2].getIdCourse(), search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 3", institutions.get(0).getAcronym());
		search.setYear(2010);
		search.setMaxValue(31);
		institutions = Course.getInstitutionsByEvaluationFilter(eva[1].getIdCourse(), search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 2", institutions.get(0).getAcronym());

		this.destroyEvaluation(eva);
	}

	public void testShouldWriteCourseToParcel() {
		Course courseA = Course.last();

		Bundle b = new Bundle();
		b.putParcelable("course", courseA);

		Parcel parcel = Parcel.obtain();
		b.writeToParcel(parcel, 0);

		parcel.setDataPosition(0);
	    Bundle b2 = parcel.readBundle();
	    b2.setClassLoader(Institution.class.getClassLoader());
	    Course courseB = b2.getParcelable("course");

	    assertEquals(courseA.getName(), courseB.getName());
	}

	/* Inside test helpers */
	
	private Evaluation [] buildEvaluation() {
		Evaluation [] response = new Evaluation[3];
		
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
		evaluation.setTriennialEvaluation(Integer.parseInt("20"));
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
		evaluation.setTriennialEvaluation(Integer.parseInt("30"));
		evaluation.setPermanentTeachers(Integer.parseInt("2"));
		evaluation.setTheses(Integer.parseInt("3"));
		evaluation.setDissertations(Integer.parseInt("4"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("6"));
		evaluation.save();

		response[1] = evaluation;
		
		/* Third Evaluation */
		
		institution = new Institution();
		institution.setAcronym("name institution 3");
		institution.save();

		article = new Article();
		article.setPublishedJournals(Integer.parseInt("2"));
		article.save();

		book = new Book();
		book.setIntegralText(Integer.parseInt("2"));
		book.save();

		evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course2.getId());
		evaluation.setYear(Integer.parseInt("2007"));
		evaluation.setModality("modality 2");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2001"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2011"));
		evaluation.setTriennialEvaluation(Integer.parseInt("25"));
		evaluation.setPermanentTeachers(Integer.parseInt("2"));
		evaluation.setTheses(Integer.parseInt("3"));
		evaluation.setDissertations(Integer.parseInt("4"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("6"));
		evaluation.save();

		response[2] = evaluation;

		return response;
	}

	private void destroyEvaluation(Evaluation [] eva) {
		Course course1 =  Course.get(eva[0].getIdCourse());
		Course course2 =  Course.get(eva[1].getIdCourse());

		course1.delete();
		course2.delete();

		Institution institution1 =  Institution.get(eva[0].getIdInstitution());
		Institution institution2 =  Institution.get(eva[1].getIdInstitution());
		Institution institution3 =  Institution.get(eva[2].getIdInstitution());
		
		institution1.delete();
		institution2.delete();
		institution3.delete();
		
		Evaluation evaluation1 = Evaluation.get(eva[0].getId());
		Evaluation evaluation2 = Evaluation.get(eva[1].getId());
		Evaluation evaluation3 = Evaluation.get(eva[2].getId());
		
		evaluation1.delete();
		evaluation2.delete();
		evaluation3.delete();
	}
}
