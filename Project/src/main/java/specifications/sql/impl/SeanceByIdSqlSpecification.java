package specifications.sql.impl;

import db.DataBaseNames;
import model.Seance;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class SeanceByIdSqlSpecification implements SqlSpecification<Seance>
{
    private String tableName;
    private long seanceId;

    public SeanceByIdSqlSpecification(long seanceId){
        this.seanceId = seanceId;
        tableName = DataBaseNames.SEANCES;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return String.format("%s.SEANCE_ID = %d", tableName, seanceId);
    }

    public boolean specified(Seance source){
        return seanceId == source.getSeanceID();
    }
}
