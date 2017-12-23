package specifications.sql.impl;

import db.DataBaseNames;
import model.Seance;
import specifications.sql.SqlSpecification;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class SeanceLaterThanSqlSpecification implements SqlSpecification<Seance> {
    private Calendar date;
    private String tableName;

    public SeanceLaterThanSqlSpecification(Calendar date) {
        this.date = date;
        tableName = DataBaseNames.SEANCES;
    }

    @Override
    public boolean specified(Seance source) {
        return source.getSeanceStartDate().compareTo(date) > 0;
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.SEANCE_START_DATE > TO_DATE(\'%s\', \'yyyy-mm-dd\')", tableName, new java.sql.Date(date.getTimeInMillis()));
    }
}
