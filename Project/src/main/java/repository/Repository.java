package repository;

import specifications.sql.SqlSpecification;

import java.util.List;

public interface Repository<T> {

    void add(T item);
    void addItems(Iterable<T> items);
    void update(T item);
    void remove(T item);

    void remove(SqlSpecification sqlSpecification);
    List<T> query(SqlSpecification sqlSpecification);
}
