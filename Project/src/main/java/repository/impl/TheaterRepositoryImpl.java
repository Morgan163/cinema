package repository.impl;

import model.Theater;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import db.DataBaseNames;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.*;
import java.util.*;

public class TheaterRepositoryImpl implements Repository<Theater> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public TheaterRepositoryImpl(){
        neededSelectTableColumns = Arrays.asList(DataBaseNames.THEATERS_TABLE_NAME + ".THEATER_ID",
                                              DataBaseNames.THEATERS_TABLE_NAME + ".THEATER_NUMBER");
    }

    public void add(Iterable<Theater> items) {
        for (Theater theater : items) {
            add(theater);
        }
    }

    public void add(Theater item) {
        try {
            item.setTheaterID(generateTheaterId());
            String sql = getInsertSqlForTheater(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Theater item) {
        try {
            String sql = getUpdateSqlForTheater(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(Theater item) {
        SqlSpecification specification = (SqlSpecification) specificationFactory.getTheaterByIdSpecification(item.getTheaterID());
        remove(specification);
    }

    public void remove(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildDeleteQueryBySQLSpecification(sqlSpecification);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Theater> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List <Theater> theaters = executeSelect(sql);
            return theaters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateTheaterId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.TABLE_ID_SEQUENCE));
    }

    private String getInsertSqlForTheater(Theater theater){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForTheater(theater);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.THEATERS_TABLE_NAME, objectColumnValues);
        return sql;
    }

    private String getUpdateSqlForTheater(Theater theater){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForTheater(theater);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.THEATERS_TABLE_NAME, objectColumnValues);
        return sql;
    }

    private List<Theater> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<Theater> theaters = parseResultSet(resultSet);
        resultSet.close();
        return theaters;
    }

    private List<Theater> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Theater> theaters = new ArrayList<Theater>();
        while (resultSet.next()) {
            long theaterId = resultSet.getLong("THEATER_ID");
            int theaterNumber = resultSet.getInt("THEATER_NUMBER");
            Theater theater = new Theater(theaterId, theaterNumber);
            theaters.add(theater);
        }
        return theaters;
    }

    private ObjectColumnValues getObjectColumnValuesForTheater(Theater theater){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("theater_number", String.valueOf(theater.getTheaterNumber()));
        objectColumnValues.setValueByColumnName("theater_id", String.valueOf(theater.getTheaterID()));
        objectColumnValues.setIdColumnName("theater_id");
        objectColumnValues.setObjectId(String.valueOf(theater.getTheaterID()));
        return objectColumnValues;
    }
}

