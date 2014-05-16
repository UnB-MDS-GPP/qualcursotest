package unb.mdsgpp.qualcurso.test.models;


import android.database.SQLException;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import junit.framework.TestCase;
import libraries.DataBaseStructures;
import models.Article;
import models.Book;


public class TestBook extends AndroidTestCase{

	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		db.initDB();
		Book book = new Book();
		book.setIntegralText(Integer.parseInt("1111"));
		book.setChapters(Integer.parseInt("5"));
		book.setCollections(Integer.parseInt("1"));
		book.setEntries(Integer.parseInt("1"));
		book.save();

		book = new Book();
		book.setIntegralText(Integer.parseInt("2222"));
		book.setChapters(Integer.parseInt("10"));
		book.setCollections(Integer.parseInt("2"));
		book.setEntries(Integer.parseInt("2"));
		book.save();
	}


	@Override
	protected void tearDown() throws Exception{
	}


	
	public void testShouldCreateNewBookOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Book.count();

		Book book = new Book();
		book.setIntegralText(Integer.parseInt("0"));
		book.setChapters(Integer.parseInt("0"));
		book.setCollections(Integer.parseInt("0"));
		book.setEntries(Integer.parseInt("0"));

		assertEquals(true, book.save());
		assertEquals(initialCount, Book.count()-1);
		book.delete();
	}

	
	public void testShouldCountbookOnDataBase() throws ClassNotFoundException, SQLException {
		int initialCount = Book.count();
		Book book = new Book();
		book.setIntegralText(Integer.parseInt("0"));
		book.setChapters(Integer.parseInt("0"));
		book.setCollections(Integer.parseInt("0"));
		book.setEntries(Integer.parseInt("0"));
		book.save();
		assertEquals(initialCount+1, (int)Book.count());
		assertEquals(Book.getAll().size(), (int)Book.count());
		book.delete();
	}

	
	public void testShouldGetBookOnDataBase() throws ClassNotFoundException, SQLException {
		Book book1 = new Book();
		book1.setIntegralText(Integer.parseInt("0"));
		book1.setChapters(Integer.parseInt("0"));
		book1.setCollections(Integer.parseInt("0"));
		book1.setEntries(Integer.parseInt("0"));
		book1.save();

		Book book2 = Book.get(Book.last().getId());

		assertEquals(book1.getIntegralText(), book2.getIntegralText());
		assertEquals(book1.getChapters(), book2.getChapters());
		assertEquals(book1.getCollections(), book2.getCollections());
		assertEquals(book1.getEntries(), book2.getEntries());
		book1.delete();
	}

	
	public void testShouldGetAllBooksOnDataBase() throws ClassNotFoundException, SQLException {
		int total = Book.count();
		assertEquals(total, Book.getAll().size());
		assertEquals("", Book.first().get("test"));
	}
	
	
	public void testShouldGetTheFirstBookOnDataBase() throws ClassNotFoundException, SQLException {
		Book first = Book.first();
		assertEquals(first.getIntegralText(), Book.getAll().get(0).getIntegralText());
		assertEquals(first.getChapters(), Book.getAll().get(0).getChapters());
		assertEquals(first.getCollections(), Book.getAll().get(0).getCollections());
		assertEquals(first.getEntries(), Book.getAll().get(0).getEntries());
	}

	
	public void testShouldGetTheLastBookOnDataBase() throws ClassNotFoundException, SQLException {
		Book last = Book.last();

		ArrayList<Book> book = Book.getAll();
		assertEquals(last.getIntegralText(), book.get(book.size()-1).getIntegralText());
		assertEquals(last.getChapters(), book.get(book.size()-1).getChapters());
		assertEquals(last.getCollections(), book.get(book.size()-1).getCollections());
		assertEquals(last.getEntries(), book.get(book.size()-1).getEntries());
	}
	
	
	public void testShouldGetBookWithWhereOnDataBase() throws ClassNotFoundException, SQLException {
		Book book = new Book();
		book.setIntegralText(Integer.parseInt("8000"));
		book.setChapters(Integer.parseInt("8"));
		book.setCollections(Integer.parseInt("1"));
		book.setEntries(Integer.parseInt("5"));
		book.save();

		Book book1 = new Book();
		book1.setIntegralText(Integer.parseInt("10000"));
		book1.setChapters(Integer.parseInt("10"));
		book1.setCollections(Integer.parseInt("6"));
		book1.setEntries(Integer.parseInt("1"));
		book1.save();

		ArrayList<Book> books1 = Book.getWhere("integral_text", "00", true);
		ArrayList<Book> books2 = Book.getWhere("integral_text", "10", true);
		
		assertEquals(2, books1.size());
		assertEquals(1, books2.size());
		
		book.delete();
		book1.delete();
	}
}
