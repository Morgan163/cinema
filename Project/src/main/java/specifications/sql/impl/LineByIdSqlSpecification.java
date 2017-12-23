package specifications.sql.impl;

import db.DataBaseNames;
import model.Line;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class LineByIdSqlSpecification implements SqlSpecification<Line> {
    private long lineId;
    private String tableName;

    public LineByIdSqlSpecification(long lineId) {
        this.lineId = lineId;
        tableName = DataBaseNames.LINES;
    }

    @Override
    public boolean specified(Line source) {
        return lineId == source.getLineID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.LINE_ID = %d", tableName, lineId);
    }
}
