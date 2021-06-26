package excelUtility;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;

public class TestUtil {

	static ExcelReader reader;

	public static ArrayList<Object[]> getDataFromExcel() {

		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			reader = new ExcelReader(System.getProperty("user.dir") + "/src/main/resources/TestData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int rowNum = 2; rowNum <= reader.getRowCount("TestDataSheet"); rowNum++) {
			String emailAddress = reader.getCellData("TestDataSheet", "emailAddress", rowNum) + RandomUtils.nextInt() + "@gmail.com";
			String firstName = reader.getCellData("TestDataSheet", "firstname", rowNum);
			String lastName = reader.getCellData("TestDataSheet", "lastname", rowNum);
			String password = reader.getCellData("TestDataSheet", "password", rowNum);
			String address = reader.getCellData("TestDataSheet", "address", rowNum);
			String city = reader.getCellData("TestDataSheet", "city", rowNum);
			String state = reader.getCellData("TestDataSheet", "state", rowNum);
			String postalCode = reader.getCellData("TestDataSheet", "postalCode", rowNum);
			String mobilePhone = reader.getCellData("TestDataSheet", "mobilePhone", rowNum);
			Object ob[] = {emailAddress, firstName, lastName, password, address, city, state, postalCode,
					mobilePhone};
			myData.add(ob);
		}
		return myData;
	}
}
