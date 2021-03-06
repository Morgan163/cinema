package specifications.sql.impl;

import model.Line;
import model.Theater;
import db.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class LineByTheaterIdSqlSpecification implements SqlSpecification<Line> {
    private long theaterId;
    private String tableName;

    private LineByTheaterIdSqlSpecification(){
        tableName = DataBaseNames.LINES;
    }

    public LineByTheaterIdSqlSpecification(long theaterId) {
        this();
        this.theaterId = theaterId;
    }

    public LineByTheaterIdSqlSpecification(Theater theater) {
        this(theater.getTheaterID());
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
