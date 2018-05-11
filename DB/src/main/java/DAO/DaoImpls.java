package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.postgresql.util.PSQLException;

import model.Option;
import util.HibernateUtil;

public class DaoImpls<T> implements IDao<T> {
	Class<?> classType;

	public DaoImpls(Class<?> type) {
		classType = type;
	}

	public <T extends Option> String[] items() {
		List<T> items = (List<T>) getAllItems("id");
		String[] res = new String[items.size()];
		for (int i = 0; i < res.length; i++)
			res[i] = items.get(i).getId() + "-" + items.get(i).getName();
		return res;
	}

	public Collection<T> getAllItems(String s) {
		Session session = null;
		List<T> items = new ArrayList<T>();
		session = HibernateUtil.getSessionFactory().openSession();
		items = session.createCriteria(classType).addOrder(Order.asc(s))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		if (session != null && session.isOpen()) {
			session.close();
		}
		return items;
	}

	public void addItem(T item) throws PSQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void updateItem(T item) throws PSQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.update(item);
			session.getTransaction().commit();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public T getItemById(Long id) throws PSQLException {
		Session session = null;
		T item = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			item = (T) session.get(classType, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return item;
	}

	public void deleteItem(T item) throws PSQLException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.delete(item);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
