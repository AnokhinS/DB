package hibernate;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import model.Personal;

/**
 * hiberante
 *
 */
public class App {
	public static void main(String[] args) throws SQLException {

		// try {
		// Class.forName("org.postgresql.Driver");
		// System.out.println("ok");
		// } catch (ClassNotFoundException e) {
		// System.out.println("BAd");
		// }

		// try {
		// Faculty f = (Faculty) Factory.getInstance().getFacultyDAO().getItemById(5L);
		// System.out.println(f.toString());
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		// Collection<Faculty> items = Factory.getInstance().getFacultyDAO().test();
		// Iterator<Faculty> itemIterator = items.iterator();
		// while (itemIterator.hasNext()) {
		// Faculty item = (Faculty) itemIterator.next();
		// System.out.println(item.toString());
		// }
		//
		// System.out.println();

		Collection<Personal> items = Factory.getInstance().getPersonalDAO().getAllItems();
		Iterator<Personal> itemIterator = items.iterator();
		while (itemIterator.hasNext()) {
			Personal item = (Personal) itemIterator.next();
			System.out.println(item.toString());
		}

		System.out.println();
	}
}