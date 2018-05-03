package hibernate;

import java.util.Collection;

import model.StudentHouse;

/**
 * hiberante
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		Collection<StudentHouse> list = Factory.getInstance().getStudentHouseDAO().getAllItems();
		for (StudentHouse f : list)
			System.out.println(f.toString());
	}

}