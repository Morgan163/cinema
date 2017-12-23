package repository.impl;

import db.DataBaseNames;
import model.SeatType;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class SeatTypeRepositoryImpl implements Repository<SeatType> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public SeatTypeRepositoryImpl() {
        neededSelectTableColumns = Collections.singletonList(DataBaseNames.SEAT_TYPES);
    }

    @Override
    public void add(SeatType item) {
        throw new UnsupportedOperationException("It's impossible to add SeatTypes");
    }

    @Override
    public void add(Iterable<SeatType> items) {
        throw new UnsupportedOperationException("It's impossible to add SeatTypes");
    }

    @Override
    public void update(SeatType item) {
        throw new UnsupportedOperationException("It's impossible to update SeatTypes");
    }

    @Override
    public void remove(SeatType item) {
        throw new UnsupportedOperationException("It's impossible to remove SeatTypes");
    }

    @Override
    public void remove(SqlSpecification sqlSpecification) {
        throw new UnsupportedOperationException("It's impossible to remove SeatTypes");
    }

    @Override
    public List<SeatType> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List<SeatType> seatTypes = executeSelect(sql);
            return seatTypes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SeatType> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<SeatType> seatTypes = parseResultSet(resultSet);
        resultSet.close();
        return seatTypes;
    }

    private List<SeatType> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<SeatType> seatTypes = new ArrayList<SeatType>();
        while (resultSet.next()) {
            long seatTypeId = resultSet.getLong("SEAT_TYPE_ID");
            SeatType seatType = SeatType.getById(seatTypeId);
            seatTypes.add(seatType);
        }
        return seatTypes;
    }
}
