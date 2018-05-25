package DAO;

import java.util.Collection;
import java.util.Map;

import org.postgresql.util.PSQLException;

import model.Option;

public interface IDao<T> {
	public void addItem(T item) throws PSQLException;

	public void updateItem(T item) throws PSQLException;

	public T getItemById(Long id);

	public void deleteItem(T item);

	public <T extends Option> String[] items();

	public Collection<T> getAllItems(String order, String property, String value, Map<String, Object> map);
}
