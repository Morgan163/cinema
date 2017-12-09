package specifications.sql.impl;

import model.Theater;
import specifications.sql.DataBaseTableNames;
import specifications.sql.SqlSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 09.12.2017.
 */
public class TheaterByNumberSqlSpecification implements SqlSpecification<Theater> {
    private long theaterNumber;
    private String tableName;

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
