package specifications.sql.impl;

import model.Seat;
import specifications.sql.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class SeatByLineIdSqlSpecification implements SqlSpecification<Seat> {
    private long lineId;
    private String tableName;

    public SeatByLineIdSqlSpecification(int lineId) {
        this.lineId = lineId;
        tableName = DataBaseNames.SEATS;
    }

    public boolean specified(Seat source) {
        return source.getLine().getLineID() == lineId;
    }

    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    public String toSqlClause() {
        return String.format("%s.LINE_ID = %d", tableName, lineId);
    }
}
