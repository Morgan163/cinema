package repository.impl;

import db.connections.ConnectionHolder;
import model.Theater;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.DataBaseNames;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class TheaterRepositoryImpl implements Repository<Theater> {

    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private ConnectionHolder connectionHolder;
    private Connection connection;
    private List<String> nestedSelectTableColumns;
    private List<String> nestedUpdateTableColumns;

    public TheaterRepositoryImpl(){
        nestedSelectTableColumns = Arrays.asList(DataBaseNames.THEATERS + ".THEATER_ID",
                                              DataBaseNames.THEATERS + ".THEATER_NUMBER");
        nestedUpdateTableColumns = Arrays.asList("theater_id", "theater_number");
    }

    public void add(Theater item) {
        add(Collections.singletonList(item));
    }



    public void add(Iterable<Theater> items) {
        for (Theater theater : items) {
            Map<String, String> params = new HashMap<String,String>();
            params.put("theater_id", "NEXT VALUE FOR " + DataBaseNames.TABLE_ID_SEQUENCE);
            params.put("theater_number", String.valueOf(theater.getTheaterNumber()));
            String sql = SqlQueryBuilder.buildInsertQuery(DataBaseNames.THEATERS, params);
            try {
                connection = connectionHolder.getConnection();
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(Theater item) {
        //todo update
    }

    public void remove(Theater item) {
        SqlSpecification specification = (SqlSpecification) specificationFactory.getTheaterByIdSpecification(item.getTheaterID());
        remove(specification);
    }

    public void remove(SqlSpecification sqlSpecification) {
        String sql = SqlQueryBuilder.buildDeleteQueryBySQLSpecification(sqlSpecification);
        try {
            connection = connectionHolder.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Theater> query(SqlSpecification sqlSpecification) {
        String sql = SqlQueryBuilder.buildSelectQueryBySQLSpecification(nestedSelectTableColumns, sqlSpecification);
        try {
            connection = connectionHolder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Theater> theaters = new ArrayList<Theater>();
            while (resultSet.next()) {
                long theaterId = resultSet.getLong("THEATER_ID");
                int theaterNumber = resultSet.getInt("THEATER_NUMBER");
                Theater theater = new Theater(theaterId, theaterNumber);
                theaters.add(theater);
            }
            return theaters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

