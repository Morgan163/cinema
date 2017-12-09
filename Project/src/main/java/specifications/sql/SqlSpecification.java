package specifications.sql;

import specifications.Specification;

import java.util.List;

public interface SqlSpecification<T> extends Specification<T> {
    List<String> getSourceTables();
    String toSqlClause();
}
