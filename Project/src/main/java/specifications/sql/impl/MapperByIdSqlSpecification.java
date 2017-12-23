package specifications.sql.impl;

import db.DataBaseNames;
import model.SeatSeanceStatusMapper;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class MapperByIdSqlSpecification implements SqlSpecification<SeatSeanceStatusMapper> {
    private long mapperId;
    private String tableName;

    public MapperByIdSqlSpecification(long mapperId) {
        this.mapperId = mapperId;
        tableName = DataBaseNames.SEATS_SEANCES;
    }

    @Override
    public boolean specified(SeatSeanceStatusMapper source) {
        return source.getMappingId() == mapperId;
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.MAPPING_ID = %d", tableName, mapperId);
    }
}
