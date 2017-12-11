package specifications.sql.impl;

import model.Line;
import specifications.sql.DataBaseTableNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class LineByTheaterIdSqlSpecification implements SqlSpecification<Line> {
    private long theaterId;
    private String tableName;

    public LineByTheaterIdSqlSpecification(int theaterId) {
        this.theaterId = theaterId;
        tableName = DataBaseTableNames.LINES;
    }

    public boolean specified(Line source) {
        return source.getTheater().getTheaterID() == theaterId;
    }

    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    public String toSqlClause() {
        return String.format("%s.THEATER_ID = %d", tableName, theaterId);
    }
}
