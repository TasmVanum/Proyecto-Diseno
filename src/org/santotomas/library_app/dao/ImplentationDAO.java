package org.santotomas.library_app.dao;

import java.sql.SQLException;
import java.util.List;

public interface ImplentationDAO<T> {
    public List<T> getAll() throws SQLException;
    public T getByUUID(String uuid) throws SQLException;
    public T getById(int id);
    public int add(T t) throws SQLException;
    public int update(T t) throws SQLException;
    public int delete(String uuid) throws SQLException;

}
