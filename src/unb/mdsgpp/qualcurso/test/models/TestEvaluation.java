package unb.mdsgpp.qualcurso.test.models;

import android.database.SQLException;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;
import models.Book;
import models.Course;
import models.Evaluation;
import models.Institution;



public class TestEvaluation extends AndroidTestCase{

	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		db.initDB();
		Institution institution = new Institution();
		institution.setAcronym("1");
		institution.save();
		
		Course course = new Course();
		course.setName("name course");
		course.save();
		
		Article article = new Article();
		article.setPublishedJournals(1);
		article.save();
		
		Book book = new Book();
		book.setIntegralText(1);
		book.save();
		
		Evaluation evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course.getId());
		evaluation.setYear(Integer.parseInt("2014"));
		evaluation.setModality("modality");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2000"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2010"));
		evaluation.setTriennialEvaluation(Integer.parseInt("2003"));
		evaluation.setPermanentTeachers(Integer.parseInt("1"));
		evaluation.setTheses(Integer.parseInt("2"));
		evaluation.setDissertations(Integer.parseInt("3"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("5"));
		evaluation.save();
		
		institution = new Institution();
		institution.setAcronym("2");
		institution.save();
		
		course = new Course();
		course.setName("name course 2");
		course.save();
		
		article = new Article();
		article.setPublishedJournals(Integer.parseInt("2"));
		article.save();
		
		book = new Book();
		book.setIntegralText(Integer.parseInt("2"));
		book.save();
		
		evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course.getId());
		evaluation.setYear(Integer.parseInt("2015"));
		evaluation.setModality("modality 2");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2001"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2011"));
		evaluation.setTriennialEvaluation(Integer.parseInt("2004"));
		evaluation.setPermanentTeachers(Integer.parseInt("2"));
		evaluation.setTheses(Integer.parseInt("3"));
		evaluation.setDissertations(Integer.parseInt("4"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("6"));
		evaluation.save();
	}

	@Override
	protected void tearDown() throws Exception{

	}
	
	
	public void testShouldCreateNewEvaluationOnDataBase() throws ClassNotFoundException, SQLException{
		int initialCount = Evaluation.count();
		
		Institution institution = new Institution();
		institution.setAcronym("1");
		institution.save();
		
		Course course = new Course();
		course.setName("name course");
		course.save();
		
		Article article = new Article();
		article.setPublishedJournals(Integer.parseInt("1"));
		article.save();
		
		Book book = new Book();
		book.setIntegralText(Integer.parseInt("1"));
		book.save();
		
		Evaluation evaluation = new Evaluation();
		evaluation.setIdInstitution(institution.getId());
		evaluation.setIdCourse(course.getId());
		evaluation.setYear(Integer.parseInt("2014"));
		evaluation.setModality("modality");
		evaluation.setMasterDegreeStartYear(Integer.parseInt("2000"));
		evaluation.setDoctorateStartYear(Integer.parseInt("2010"));
		evaluation.setTriennialEvaluation(Integer.parseInt("2003"));
		evaluation.setPermanentTeachers(Integer.parseInt("1"));
		evaluation.setTheses(Integer.parseInt("2"));
		evaluation.setDissertations(Integer.parseInt("3"));
		evaluation.setIdArticles(article.getId());
		evaluation.setIdBooks(book.getId());
		evaluation.setArtisticProduction(Integer.parseInt("5"));
		
		assertEquals(true, evaluation.save());
		assertEquals(initialCount, Evaluation.count()-1);
		assertEquals("1", Institution.get(Evaluation.last().getIdInstitution()).getAcronym());
		assertEquals("name course", Course.get(Evaluation.last().getIdCourse()).getName());
		assertEquals(1, Article.get(Evaluation.last().getIdArticles()).getPublishedJournals());
		assertEquals(1, Book.get(Evaluation.last().getIdBooks()).getIntegralText());
		
		institution.delete();
		course.delete();
		book.delete();
		article.delete();
		evaluation.delete();
	}
	
	
	public void testShouldCountEvaluationsOnDataBase() throws ClassNotFoundException, SQLException{
		int initialCount = Evaluation.count();
	
		Evaluation evaluation = new Evaluation();
		evaluation.setYear(2000);
		evaluation.setModality("modality");
		evaluation.setMasterDegreeStartYear(1);
		evaluation.save();
		
		assertEquals(initialCount+1, Evaluation.count());
		assertEquals(Evaluation.getAll().size(), Evaluation.count());
		
		evaluation.delete();
	}
	
	
	public void testShouldGetEvaluationOnDataBase() throws ClassNotFoundException, SQLException{
		
		Evaluation evaluation_1 = new Evaluation();
		evaluation_1.setDoctorateStartYear(1);
		evaluation_1.setTriennialEvaluation(2);
		evaluation_1.setPermanentTeachers(3);
		evaluation_1.setModality("modality");
		evaluation_1.setArtisticProduction(3);
		evaluation_1.save();
		
		Evaluation evaluation_2 = Evaluation.get(Evaluation.last().getId());
		assertEquals(evaluation_1.getDoctorateStartYear(), evaluation_2.getDoctorateStartYear());
		assertEquals(evaluation_1.getTriennialEvaluation(), evaluation_2.getTriennialEvaluation());
		assertEquals(evaluation_1.getPermanentTeachers(), evaluation_2.getPermanentTeachers());
		assertEquals(evaluation_1.getArtisticProduction(), evaluation_2.getArtisticProduction());
		evaluation_1.delete();
	}
	
	
	public void testShouldGetAllEvaluationsOnDataBase() throws ClassNotFoundException, SQLException {
		int total = Evaluation.count();
		assertEquals(total, Evaluation.getAll().size());
		assertEquals(Evaluation.first().getTheses(), Evaluation.getAll().get(0).getTheses());
		assertEquals(Evaluation.first().getDissertations(), Evaluation.getAll().get(0).getDissertations());
		assertEquals("", Evaluation.first().get("test"));
	}
	
	
	public void testShouldGetTheFirstEvaluationOnDataBase() throws ClassNotFoundException, SQLException {
		Evaluation firstEvaluation = Evaluation.first();
		assertEquals(firstEvaluation.getModality(), Evaluation.getAll().get(0).getModality());
	}
	
	
	public void testShouldGetTheLastEvaluationOnDataBase() throws ClassNotFoundException, SQLException {
		Evaluation lastEvaluation = Evaluation.last();
		int last = Evaluation.getAll().size()-1;
		assertEquals(lastEvaluation.getArtisticProduction(), Evaluation.getAll().get(last).getArtisticProduction());
	}
	
	
	public void testShouldGetEvaluationsWithWhereOnDataBase() throws ClassNotFoundException, SQLException{
		Evaluation evaluation1 = new Evaluation();
		evaluation1.setModality("Moda Test");
		evaluation1.save();
		
		Evaluation evaluation2 = new Evaluation();
		evaluation2.setModality("Moda ABC");
		evaluation2.save();
		
		ArrayList<Evaluation> evaluations_1 = Evaluation.getWhere("modality", "Moda ", true);
		ArrayList<Evaluation> evaluations_2 = Evaluation.getWhere("modality", "Test", true);
		ArrayList<Evaluation> evaluations_3 = Evaluation.getWhere("modality", "Moda Test", false);
		
		assertEquals(2, evaluations_1.size());
		assertEquals(1, evaluations_2.size());
		assertEquals(1, evaluations_3.size());
		evaluation1.delete();
		evaluation2.delete();
	}
	public void testShouldGetEvaluationsFromRelation(){
		Evaluation evaluation1 = Evaluation.get(1);
		assertEquals(Evaluation.getFromRelation(1, 1).get(0).getDissertations(), evaluation1.getDissertations());

	}
}
