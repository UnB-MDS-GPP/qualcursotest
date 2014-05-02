package unb.mdsgpp.qualcurso.test.models;


import android.database.SQLException;
import java.util.ArrayList;

import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;


public class TestArticle extends TestCase{
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        DataBaseStructures db = new DataBaseStructures();
		db.initDB();
		Article article = new Article();
		article.setPublishedJournals(Integer.parseInt("1"));
		article.setPublishedConferenceProceedings(Integer.parseInt("2"));
		article.save();

		article = new Article();
		article.setPublishedJournals(Integer.parseInt("2"));
		article.setPublishedConferenceProceedings(Integer.parseInt("8"));
		article.save();
	}
	
	@Override
	protected void tearDown() throws Exception{
		super.tearDown();
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		
	}


	public void testShouldCreateNewArticleOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Article.count();

		Article article = new Article();
		article.setPublishedJournals(Integer.parseInt("0"));
		article.setPublishedConferenceProceedings(Integer.parseInt("0"));

		assertEquals(true, article.save());
		assertEquals(initialCount, Article.count()-1);
		article.delete();
	}

	public void testShouldCountArticleOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Article.count();
		Article article = new Article();
		article.setPublishedJournals(Integer.parseInt("0"));
		article.setPublishedConferenceProceedings(Integer.parseInt("0"));
		article.save();
		
		assertEquals(initialCount+1, Article.count());
		assertEquals(Article.getAll().size(), Article.count());
		article.delete();
	}

	public void testShouldGetArticleOnDataBase() throws ClassNotFoundException, SQLException {
		Article article1 = new Article();
		article1.setPublishedJournals(Integer.parseInt("0"));
		article1.setPublishedConferenceProceedings(Integer.parseInt("0"));
		article1.save();

		Article article2 = Article.get(Article.last().getId());

		assertEquals(article1.getPublishedJournals(), article2.getPublishedJournals());
		assertEquals(article1.getPublishedConferenceProceedings(), article2.getPublishedConferenceProceedings());
		article1.delete();
	}

	public void testShouldGetAllArticleOnDataBase() throws ClassNotFoundException, SQLException {
		int total = Article.count();
		assertEquals(total, Article.getAll().size());
	}

	public void testShouldGetTheFirstArticleOnDataBase() throws ClassNotFoundException, SQLException {
		Article first = Article.first();
		assertEquals(first.getPublishedJournals(), Article.getAll().get(0).getPublishedJournals());
		assertEquals(first.getPublishedConferenceProceedings(), Article.getAll().get(0).getPublishedConferenceProceedings());
	}

	public void testShouldGetTheLastArticleOnDataBase() throws ClassNotFoundException, SQLException {
		Article last = Article.last();

		ArrayList<Article> article = Article.getAll();
		assertEquals(last.getPublishedJournals(), article.get(article.size()-1).getPublishedJournals());
		assertEquals(last.getPublishedConferenceProceedings(), article.get(article.size()-1).getPublishedConferenceProceedings());
	}
	
	public void testShouldGetArticleWithWhereOnDataBase() throws ClassNotFoundException, SQLException {
		Article article = new Article();
		article.setPublishedJournals(Integer.parseInt("8000"));
		article.setPublishedConferenceProceedings(Integer.parseInt("8"));
		article.save();

		Article article1 = new Article();
		article1.setPublishedJournals(Integer.parseInt("10000"));
		article1.setPublishedConferenceProceedings(Integer.parseInt("10"));
		article1.save();

		ArrayList<Article> articles1 = Article.getWhere("published_journals", "00", true);
		ArrayList<Article> articles2 = Article.getWhere("published_journals", "10", true);
		
		assertEquals(2, articles1.size());
		assertEquals(1, articles2.size());
		
		article.delete();
		article1.delete();
	}
}
