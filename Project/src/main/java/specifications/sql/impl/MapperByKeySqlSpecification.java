package specifications.sql.impl;

import db.DataBaseNames;
import model.SeatSeanceStatusMapper;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 25.12.2017.
 */
public class MapperByKeySqlSpecification implements SqlSpecification<SeatSeanceStatusMapper> {
    private String key;
    private String tableName;

    public MapperByKeySqlSpecification(String key) {
        this.key = key;
        tableName = DataBaseNames.SEATS_SEANCES;
    }

    @Override
    public boolean specified(SeatSeanceStatusMapper source) {
        return key.equals(source.getBookKey());
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.BOOK_KEY = '%s'", tableName, key);
    }
}
