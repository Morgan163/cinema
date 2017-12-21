package specifications.sql.impl;

import db.DataBaseNames;
import model.SeatSeanceStatusMapper;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class MapperBySeanceIdSqlSpecification implements SqlSpecification<SeatSeanceStatusMapper>
{
    private String tableName;
    private long seanceId;

    public MapperBySeanceIdSqlSpecification(long seanceId){
        this.seanceId = seanceId;
        tableName = DataBaseNames.SEATS_SEANCES;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return String.format("%s.SEANCE_ID = %d", tableName, seanceId);
    }

    public boolean specified(SeatSeanceStatusMapper source){
        return source.getSeance().getSeanceID() == seanceId;
    }
}
