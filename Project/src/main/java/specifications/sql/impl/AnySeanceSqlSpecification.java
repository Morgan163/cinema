package specifications.sql.impl;

import db.DataBaseNames;
import model.Seance;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class AnySeanceSqlSpecification implements SqlSpecification<Seance>
{
    String tableName;

    public AnySeanceSqlSpecification(){
        this.tableName = DataBaseNames.SEANCES;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return "1 = 1";
    }

    public boolean specified(Seance source){
        return true;
    }
}
