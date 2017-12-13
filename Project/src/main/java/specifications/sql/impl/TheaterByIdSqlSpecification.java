package specifications.sql.impl;

import model.Theater;
import specifications.sql.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class TheaterByIdSqlSpecification implements SqlSpecification<Theater> {
    private long theaterId;
    private String tableName;

    /*
    todo сделать в скрипте theaterId уникальным
     */
    public TheaterByIdSqlSpecification(long theaterId) {
        this.theaterId = theaterId;
        tableName = DataBaseNames.THEATERS;
    }

    public boolean specified(Theater source) {
        return source.getTheaterNumber() == theaterId;
    }

    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    public String toSqlClause() {
        return String.format("%s.THEATER_ID = %d", tableName, theaterId);
    }
}
