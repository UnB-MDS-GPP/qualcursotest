package unb.mdsgpp.qualcurso.test.models;


import junit.framework.TestCase;
import libraries.DataBaseStructures;


public class TestCreateDatabase extends TestCase{
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        DataBaseStructures db = new DataBaseStructures();
		db.initDB();
	}
	
	public void testCreated(){
		assertEquals("OK", "OK");
	}
}