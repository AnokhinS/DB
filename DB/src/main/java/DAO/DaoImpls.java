package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.postgresql.util.PSQLException;

import model.Option;
import util.HibernateUtil;

public class DaoImpls<T> implements IDao<T> {
	Class<?> classType;

	public DaoImpls(Class<?> type) {
		classType = type;
	}

	@SuppressWarnings("hiding")
	public <T extends Option> String[] items() {
		@SuppressWarnings("unchecked")
		List<T> items = (List<T>) getAllItems("id", null, null, null);
		String[] res = new String[items.size()];
		for (int i = 0; i < res.length; i++)
			res[i] = items.get(i).getId() + "-" + items.get(i).getName();
		return res;
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
		@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> getAllItems(String order, String property, String value, List<Criterion> crits) {
		List<T> items = new ArrayList<T>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		@SuppressWarnings("deprecation")
		Criteria result = session.createCriteria(classType);
		if (order != null) {
			result.addOrder(Order.asc(order));
		} else if (property != null && value != null) {
			result.createAlias(property, "p").addOrder(Order.asc("p." + value));
		}
		if (crits != null)
			for (Criterion c : crits) {
				result.add(c);
			}
		items = result.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		session.close();
		return items;
	}

}
