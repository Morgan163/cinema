package specifications.sql.impl;

import db.DataBaseNames;
import model.Theater;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class AnyTheaterSqlSpecification implements SqlSpecification<Theater>
{
    public List<String> getSourceTables(){
        return Collections.singletonList(DataBaseNames.THEATERS_TABLE_NAME);
    }

    public String toSqlClause(){
        return "True";
    }

    public boolean specified(Theater source){
        return true;
    }
}
