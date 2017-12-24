package specifications.sql.impl;

import db.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Arrays;
import java.util.List;

/**
 * Created by niict on 24.12.2017.
 */
public class LineIdEqualsSeatLineIdSqlSpecification implements SqlSpecification {
    public List<String> getSourceTables(){
        return Arrays.asList(DataBaseNames.LINES, DataBaseNames.SEATS);
    }

    public String toSqlClause(){
        return String.format("%s.line_id = %s.line_id", DataBaseNames.LINES, DataBaseNames.SEATS);
    }

    public boolean specified(Object source){
        throw new UnsupportedOperationException("specified is not supported for this spec");
    }
}
