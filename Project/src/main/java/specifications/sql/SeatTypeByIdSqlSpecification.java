package specifications.sql;

import model.SeatType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 09.12.2017.
 */
public class SeatTypeByIdSqlSpecification implements SqlSpecification<SeatType> {
    private int typeId;
    private String tableName;

    public SeatTypeByIdSqlSpecification(int typeId) {
        this.typeId = typeId;
        tableName = DataBaseTableNames.SEAT_TYPES;
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
