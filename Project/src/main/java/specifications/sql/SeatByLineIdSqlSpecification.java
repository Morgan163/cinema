package specifications.sql;

import model.Seat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 09.12.2017.
 */
public class SeatByLineIdSqlSpecification implements SqlSpecification<Seat> {
    private long lineId;
    private String tableName;

    public SeatByLineIdSqlSpecification(int lineId) {
        this.lineId = lineId;
        tableName = DataBaseTableNames.SEATS;
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
