package specifications.sql.impl;

import db.DataBaseNames;
import model.Seat;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class SeatByIdSqlSpecification implements SqlSpecification<Seat> {
    private long seatId;
    private String tableName;

    public SeatByIdSqlSpecification(long seatId) {
        this.seatId = seatId;
        tableName = DataBaseNames.SEATS;
    }

    @Override
    public boolean specified(Seat source) {
        return seatId == source.getSeatID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.SEAT_ID = %d", tableName, seatId);
    }
}
