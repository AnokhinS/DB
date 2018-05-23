package DAO;

import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.postgresql.util.PSQLException;

import model.Option;

public interface IDao<T> {
	public void addItem(T item) throws PSQLException;

	public void updateItem(T item) throws PSQLException;

	public T getItemById(Long id);

	public void deleteItem(T item);

	public <T extends Option> String[] items();

	public Collection<T> getAllItems(String order, String property, String value, List<Criterion> c);
}
