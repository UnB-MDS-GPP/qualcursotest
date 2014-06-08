package unb.mdsgpp.qualcurso.test.models;

import android.test.AndroidTestCase;

import java.sql.Date;
import java.util.Calendar;

import libraries.DataBaseStructures;
import models.Search;
import helpers.Indicator;
import unb.mdsgpp.qualcurso.QualCurso;

public class TestSearch extends AndroidTestCase {

	@Override
	public void testAndroidTestCaseSetupProperly() {
		super.testAndroidTestCaseSetupProperly();
		QualCurso.getInstance().setDatabaseName("database_test.sqlite3.db");
		DataBaseStructures db = new DataBaseStructures();
		db.dropDB();
		db.initDB();

		Calendar c = Calendar.getInstance();
		Search s = new Search();
		s.setIndicator(Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR));
		s.setMaxValue(10);
		s.setMinValue(5);
		s.setOption(1);
		s.setYear(2014);
		s.setDate(new Date(c.getTime().getTime()));
		s.save();

		s = new Search();
		s.setIndicator(Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR));
		s.setMaxValue(15);
		s.setMinValue(10);
		s.setOption(2);
		s.setYear(2014);
		s.setDate(new Date(c.getTime().getTime()));
		s.save();
	}

	public void testShouldBeOnlyTenSearchesSavedAtTheDatabase() {
		Calendar c =Calendar.getInstance();
		Search s = new Search();
		Indicator ind = Indicator.getIndicatorByValue(Indicator.DEFAULT_INDICATOR);

		for(int i = 0; i < 12; i++) {
			s.setIndicator(ind);
			s.setMaxValue(i*10);
			s.setMinValue(i*5);
			s.setOption(i);
			s.setYear(2000+i);
			s.setDate(new Date(c.getTime().getTime()));
			s.save();
		}

		assertEquals(10, Search.count());
		testAndroidTestCaseSetupProperly();
	}

	public void testShouldGetSearch() {
		Search sLast = Search.last();

		assertEquals(sLast.getId(), Search.get(sLast.getId()).getId());
	}

	public void testShouldGetAllSearches() {
		int count = Search.count();

		assertEquals(count, Search.getAll().size());
	}

	public void testShouldGetFirstAndLastSearches() {
		Search sf = Search.first();
		Search sl = Search.last();

		assertEquals(10, sf.getMaxValue());
		assertEquals(15, sl.getMaxValue());
	}

	public void testShouldGetSearchBySomeValue() {
		Search s = Search.getWhere("max_value", "10", false).get(0);

		assertEquals(10, s.getMaxValue());
	}

	public void testShouldDeleteSearches() {
		while(Search.count() > 0)
			Search.last().delete();

		assertEquals(0, Search.count());
		testAndroidTestCaseSetupProperly();
	}
}
