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

	public Collection<T> getAllItems(String property, String value) {
		List<T> items = new ArrayList<T>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Criteria c = session.createCriteria(classType);
		c.createAlias(property, "p");
		c.addOrder(Order.asc("p." + value)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		items = c.list();
		session.close();
		return items;
	}

	public void addItem(T item) throws PSQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.save(item);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	public void updateItem(T item) throws PSQLException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			session.update(item);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
	}

	public T getItemById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		T item = (T) session.get(classType, id);
		session.close();
		return item;
	}

	public void deleteItem(T item) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(item);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public Collection<T> getAllItems(String order) {
		List<T> items = new ArrayList<T>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		items = session.createCriteria(classType).addOrder(Order.asc(order))
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return items;
	}
}
