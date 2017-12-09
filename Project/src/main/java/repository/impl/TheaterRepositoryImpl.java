package repository.impl;

import model.Theater;
import repository.Repository;
import specifications.sql.DataBaseTableNames;
import specifications.sql.SqlSpecification;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TheaterRepositoryImpl implements Repository<Theater> {

    public void add(Theater item) {
        add(Collections.singletonList(item));

    }

    public void add(Iterable<Theater> items) {
        for(Theater theater:items) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO THEATER (theater_id, theater_number)")
                    .append(" VALUES (NEXT VALUE FOR THEATER_ID_SEQUENCE,")
                    .append("\'").append(theater.getTheaterNumber()).append("\'").append(")");
            //выполнение sql запроса
        }
    }

    public void update(Theater item) {

    }

    public void remove(Theater item) {

    }

    public void remove(SqlSpecification sqlSpecification) {

    }

    public List<Theater> query(SqlSpecification sqlSpecification) {
        StringBuilder tableNames = new StringBuilder();
        List sourceTables = sqlSpecification.getSourceTables();
        for (int i = 0; i < sourceTables.size() - 1; i++){
            tableNames.append(sourceTables.get(i))
                    .append(", ");
        }
        tableNames.append(sourceTables.get(sourceTables.size()-1))
                .append(" ");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ").append(tableNames)
                .append("WHERE ").append(sqlSpecification.toSqlClause());
    }
}
