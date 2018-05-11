package DAO;

import java.util.Collection;

import org.postgresql.util.PSQLException;

import model.Option;

public interface IDao<T> {
	public void addItem(T item) throws PSQLException;

	public void updateItem(T item) throws PSQLException;

	public T getItemById(Long id) throws PSQLException;

	public void deleteItem(T item) throws PSQLException;

	public <T extends Option> String[] items();

	public Collection<T> getAllItems(String order);
}
