package specifications.sql.impl;

import model.SeatType;
import specifications.sql.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class SeatTypeByIdSqlSpecification implements SqlSpecification<SeatType> {
    private int typeId;
    private String tableName;

    public SeatTypeByIdSqlSpecification(int typeId) {
        this.typeId = typeId;
        tableName = DataBaseNames.SEAT_TYPES;
    }

    public boolean specified(SeatType source) {
        return source.getSeatTypeID() == typeId;
    }

    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    public String toSqlClause() {
        return String.format("%s.SEAT_TYPE_ID = %d", tableName, typeId);
    }
}
