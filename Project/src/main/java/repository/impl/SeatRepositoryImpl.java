package repository.impl;

import db.DataBaseNames;
import model.Seat;
import repository.Repository;
import repository.impl.DataBaseHelper;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class SeatRepositoryImpl implements Repository<Seat> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public SeatRepositoryImpl(){
        neededSelectTableColumns = Arrays.asList(DataBaseNames.SEATS + ".SEAT_ID",
                DataBaseNames.SEATS + ".SEAT_NUMBER");
    }

    @Override
    public void add(Seat item) {
        try {
            item.setSeatID(generateSeatId());
            String sql = getInsertSqlForSeat(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<Seat> items) {
        for (Seat seat : items){
            add(seat);
        }
    }

    @Override
    public void update(Seat item) {
        try {
            String sql = getUpdateSqlForSeat(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Seat item) {
        SqlSpecification specification = (SqlSpecification)specificationFactory.getSeatByIdSpecification(item.getSeatID());
        remove(specification);
    }

    @Override
    public void remove(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildDeleteQueryBySQLSpecification(sqlSpecification);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seat> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List <Seat> seats = executeSelect(sql);
            return seats;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateSeatId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.SEANCE_ID_SEQUENCE));
    }

    private String getInsertSqlForSeat(Seat seat){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForSeat(seat);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.SEATS, objectColumnValues);
        return sql;
    }

    private String getUpdateSqlForSeat(Seat seat){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForSeat(seat);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.SEATS, objectColumnValues);
        return sql;
    }

    private List<Seat> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<Seat> seats = parseResultSet(resultSet);
        resultSet.close();
        return seats;
    }

    private List<Seat> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Seat> seats = new ArrayList<Seat>();
        while (resultSet.next()) {
            long seatId = resultSet.getLong("SEAT_ID");
            int seatNumber = resultSet.getInt("SEAT_NUMBER");
            Seat seat = new Seat();
            seat.setSeatID(seatId);
            seat.setNumber(seatNumber);
            seats.add(seat);
        }
        return seats;
    }

    private ObjectColumnValues getObjectColumnValuesForSeat(Seat seat){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("SEAT_ID", String.valueOf(seat.getSeatID()));
        objectColumnValues.setValueByColumnName("SEAT_NUMBER", String.valueOf(seat.getNumber()));
        objectColumnValues.setValueByColumnName("SEAT_TYPE_ID", String.valueOf(seat.getSeatType().getSeatTypeID()));
        objectColumnValues.setValueByColumnName("LINE_ID", String.valueOf(seat.getLine().getLineID()));
        objectColumnValues.setIdColumnName("SEAT_ID");
        objectColumnValues.setObjectId(String.valueOf(seat.getSeatID()));
        return objectColumnValues;
    }
}
