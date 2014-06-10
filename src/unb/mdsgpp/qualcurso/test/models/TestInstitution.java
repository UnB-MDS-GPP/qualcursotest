package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;
import android.test.AndroidTestCase;
import helpers.Indicator;

import java.util.ArrayList;

import unb.mdsgpp.qualcurso.QualCurso;
import libraries.DataBaseStructures;
import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;
import models.Search;


public class TestInstitution extends AndroidTestCase{

	
	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		db.initDB();
		Institution institution = new Institution();
		institution.setAcronym("one");
		institution.save();
		institution = new Institution();
		institution.setAcronym("two");
		institution.save();
	}
	

	@Override
    protected void setUp() throws Exception {
		
	}

	@Override
	protected void tearDown() throws Exception {
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
		assertEquals("", Institution.first().get("test"));
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
		assertEquals("one", Institution.first().toString());
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
	
	public void testShouldGetInstitutionsByEvaluationFilter() {
		ArrayList<Institution> institutions = new ArrayList<Institution>();
		Evaluation [] eva = this.buildEvaluation();
		Search search = new Search();
		search.setIndicator(Indicator.getIndicatorByValue("triennial_evaluation"));
		search.setYear(2007);
		search.setMinValue(19);
		search.setMaxValue(21);
		institutions = Institution.getInstitutionsByEvaluationFilter(search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 1", institutions.get(0).getAcronym());
		search.setMinValue(24);
		search.setMaxValue(26);
		search.setYear(2010);
		institutions = Institution.getInstitutionsByEvaluationFilter(search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 2", institutions.get(0).getAcronym());
		search.setMaxValue(-1);
		institutions = Institution.getInstitutionsByEvaluationFilter(search);
		assertEquals(1, institutions.size());
		assertEquals("name institution 2", institutions.get(0).getAcronym());
		
		this.destroyEvaluation(eva);
	}
	
	public void testShouldGetCoursesByEvaluationFilter() {
		ArrayList<Course> courses;
		Evaluation [] eva = this.buildEvaluation();
		Search search = new Search();
		search.setIndicator(Indicator.getIndicatorByValue("triennial_evaluation"));
		search.setYear(2007);
		search.setMinValue(19);
		search.setMaxValue(21);
		courses = Institution.getCoursesByEvaluationFilter(eva[0].getIdInstitution(), search);
		assertEquals(1, courses.size());
		assertEquals("name course 1", courses.get(0).getName());
		search.setMinValue(24);
		search.setMaxValue(26);
		search.setYear(2010);
		courses = Institution.getCoursesByEvaluationFilter(eva[1].getIdInstitution(), search);
		assertEquals(1, courses.size());
		assertEquals("name course 2", courses.get(0).getName());
		search.setMaxValue(-1);
		courses = Institution.getCoursesByEvaluationFilter(eva[2].getIdInstitution(), search);
		assertEquals(2, courses.size());
		assertEquals("name course 2", courses.get(0).getName());
		assertEquals("name course 3", courses.get(1).getName());
		
		this.destroyEvaluation(eva);
	}

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
		evaluation.setTriennialEvaluation(Integer.parseInt("25"));
		evaluation.setPermanentTeachers(Integer.parseInt("2"));
		evaluation.setTheses(Integer.parseInt("3"));
		evaluation.setDissertations(Integer.parseInt("4"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("6"));
		evaluation.save();

		response[1] = evaluation;

		/* Third Evaluation */

		Course course3 = new Course();
		course3.setName("name course 3");
		course3.save();

		article = new Article();
		article.setPublishedJournals(Integer.parseInt("2"));
		article.save();

		book = new Book();
		book.setIntegralText(Integer.parseInt("2"));
		book.save();

		evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course3.getId());
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

		response[2] = evaluation;
		
		return response;
	}

	private void destroyEvaluation(Evaluation [] eva) {
		Course course1 =  Course.get(eva[0].getIdCourse());
		Course course2 =  Course.get(eva[1].getIdCourse());
		Course course3 =  Course.get(eva[2].getIdCourse());

		course1.delete();
		course2.delete();
		course3.delete();

		Institution institution1 =  Institution.get(eva[0].getIdInstitution());
		Institution institution2 =  Institution.get(eva[1].getIdInstitution());
		
		institution1.delete();
		institution2.delete();
		
		Evaluation evaluation1 = Evaluation.get(eva[0].getId());
		Evaluation evaluation2 = Evaluation.get(eva[1].getId());
		Evaluation evaluation3 = Evaluation.get(eva[2].getId());
		
		evaluation1.delete();
		evaluation2.delete();
		evaluation3.delete();
	}
}
