package repository.impl;

import db.DataBaseNames;
import model.AgeLimitType;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class AgeLimitTypeRepositoryImpl implements Repository<AgeLimitType> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public AgeLimitTypeRepositoryImpl() {
        neededSelectTableColumns = Arrays.asList(DataBaseNames.AGE_LIMIT_TYPES + ".AGE_LIMIT_ID");
    }

    @Override
    public void add(AgeLimitType item) {
        throw new UnsupportedOperationException("It's impossible to add AgeLimitType");
    }

    @Override
    public void add(Iterable<AgeLimitType> items) {
        throw new UnsupportedOperationException("It's impossible to add AgeLimitType");
    }

    @Override
    public void update(AgeLimitType item) {
        throw new UnsupportedOperationException("It's impossible to update AgeLimitType");
    }

    @Override
    public void remove(AgeLimitType item) {
        throw new UnsupportedOperationException("It's impossible to remove AgeLimitType");
    }

    @Override
    public void remove(SqlSpecification sqlSpecification) {
        throw new UnsupportedOperationException("It's impossible to remove AgeLimitType");
    }

    @Override
    public List<AgeLimitType> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List<AgeLimitType> seatTypes = executeSelect(sql);
            return seatTypes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<AgeLimitType> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<AgeLimitType> ageLimitTypes = parseResultSet(resultSet);
        resultSet.close();
        return ageLimitTypes;
    }

    private List<AgeLimitType> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<AgeLimitType> ageLimitTypes = new ArrayList<AgeLimitType>();
        while (resultSet.next()) {
            long ageLimitId = resultSet.getLong("AGE_LIMIT_ID");
            AgeLimitType ageLimitType = AgeLimitType.getById(ageLimitId);
            ageLimitTypes.add(ageLimitType);
        }
        return ageLimitTypes;
    }
}
