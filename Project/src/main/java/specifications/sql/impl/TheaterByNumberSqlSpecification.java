package specifications.sql.impl;

import model.Theater;
import specifications.sql.DataBaseTableNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class TheaterByNumberSqlSpecification implements SqlSpecification<Theater> {
    private int theaterNumber;
    private String tableName;

    /*
    todo сделать в скрипте theaterNumber уникальным
     */
    public TheaterByNumberSqlSpecification(int theaterNumber) {
        this.theaterNumber = theaterNumber;
        tableName = DataBaseTableNames.THEATERS;
    }

    public boolean specified(Theater source) {
        return source.getTheaterNumber() == theaterNumber;
    }

    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    public String toSqlClause() {
        return String.format("%s.THEATER_NUMBER = %d", tableName, theaterNumber);
    }
}
