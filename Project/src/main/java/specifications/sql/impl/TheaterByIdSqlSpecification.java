package specifications.sql.impl;

import model.Theater;
import db.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class TheaterByIdSqlSpecification implements SqlSpecification<Theater> {
    private long theaterId;
    private String tableName;

    public TheaterByIdSqlSpecification(long theaterId) {
        this.theaterId = theaterId;
        tableName = DataBaseNames.THEATERS_TABLE_NAME;
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
