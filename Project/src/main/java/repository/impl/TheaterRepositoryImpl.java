package repository.impl;

import db.connections.ConnectionHolder;
import model.Theater;
import repository.Repository;
import specifications.Specification;
import specifications.factory.SpecificationFactory;
import specifications.sql.TableNamesResolver;
import specifications.sql.DataBaseTableNames;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheaterRepositoryImpl implements Repository<Theater> {

    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private ConnectionHolder connectionHolder;
    private Connection connection;

    public void add(Theater item) {
        add(Collections.singletonList(item));
    }

    public void add(Iterable<Theater> items) {
        for(Theater theater:items) {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO THEATER (theater_id, theater_number)")
                    .append(" VALUES (NEXT VALUE FOR THEATER_ID_SEQUENCE,")
                    .append("\'").append(theater.getTheaterNumber()).append("\'").append(")");
            //выполнение sql запроса
        }
    }

    public void update(Theater item) {

    }

    public void remove(Theater item) {
        Specification specification = specificationFactory.createTheaterByNumberSqlSpecification(item.getTheaterNumber());
    }

    public void remove(SqlSpecification sqlSpecification) {

    }

    public List<Theater> query(SqlSpecification sqlSpecification) {
        String tableNames = TableNamesResolver.getInstance().resolveNamesForSqlQuery(sqlSpecification);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(DataBaseTableNames.THEATERS).append(".THEATER_ID, ")
                .append(DataBaseTableNames.THEATERS).append(".THEATER_NUMBER ")
                .append(" FROM ").append(tableNames)
                .append(" WHERE ").append(sqlSpecification.toSqlClause());
        try
        {
            connection = connectionHolder.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql.toString());
            ArrayList<Theater> theaters = new ArrayList <Theater>();
            while (resultSet.next()) {
                long theaterId = resultSet.getLong("THEATER_ID");
                int theaterNumber = resultSet.getInt("THEATER_NUMBER");
                Theater theater = new Theater(theaterId, theaterNumber);
                theaters.add(theater);
            }
            return theaters;
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

