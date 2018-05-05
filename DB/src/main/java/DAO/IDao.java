package DAO;

import java.sql.SQLException;
import java.util.Collection;

import model.Option;

public interface IDao<T> {
	public void addItem(T item) throws SQLException;

	public void updateItem(T item) throws SQLException;

	public T getItemById(Long id) throws SQLException;

	public Collection<T> getAllItems() throws SQLException;

	public void deleteItem(T item) throws SQLException;

	public <T extends Option> String[] items() throws SQLException;

	public Collection<T> getAllItems(String order);
}
