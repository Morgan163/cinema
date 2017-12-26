package specifications.sql.impl;

import db.DataBaseNames;
import specifications.sql.SqlSpecification;

import java.util.Arrays;
import java.util.List;

public class TheaterIdEqualsLineTheaterIdSqlSpecification implements SqlSpecification
{
    public List<String> getSourceTables(){
        return Arrays.asList(DataBaseNames.THEATERS_TABLE_NAME, DataBaseNames.LINES);
    }

    public String toSqlClause(){
        return String.format("%s.theater_id = %s.theater_id", DataBaseNames.THEATERS_TABLE_NAME, DataBaseNames.LINES);
    }

    public boolean specified(Object source){
        throw new UnsupportedOperationException("specified is not supported for this spec");
    }
}