
public class Regresion {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestScript06 test = new TestScript06();
		test.testData();
		try {
			test.testCase05(Util.USER_NAME, Util.PASSWD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

}
