package specifications.sql.impl;

import db.DataBaseNames;
import model.SeatSeanceStatusMapper;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class MapperBySeatIdSqlSpecification implements SqlSpecification<SeatSeanceStatusMapper>
{
    private String tableName;
    private long seatId;

    public MapperBySeatIdSqlSpecification(long seatId){
        this.seatId = seatId;
        tableName = DataBaseNames.SEATS_SEANCES;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        return String.format("%s.SEAT_ID = %d", tableName, seatId);
    }

    public boolean specified(SeatSeanceStatusMapper source){
        return source.getSeat().getSeatID() == seatId;
    }
}
