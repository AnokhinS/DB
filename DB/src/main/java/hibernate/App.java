package hibernate;

import java.lang.reflect.Field;
import java.sql.SQLException;

import model.Faculty;

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

		// Collection<Payment> items =
		// Factory.getInstance().getPaymentDAO().getAllItems();
		// Iterator<Payment> itemIterator = items.iterator();
		// while (itemIterator.hasNext()) {
		// Payment item = (Payment) itemIterator.next();
		// System.out.println(item.toString());
		// }

		for (Field f : Faculty.class.getDeclaredFields()) {
			f.setAccessible(true);
			Object o;
			try {
				o = f.get(new Faculty());
			} catch (Exception e) {
				o = e;
			}
			System.out.println(f.getGenericType() + " " + f.getName() + " = " + o);
		}
	}
}