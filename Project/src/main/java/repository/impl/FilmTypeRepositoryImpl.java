package repository.impl;

import db.DataBaseNames;
import model.Film;
import model.FilmType;
import repository.Repository;
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
public class FilmTypeRepositoryImpl implements Repository<FilmType> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public FilmTypeRepositoryImpl(){
        neededSelectTableColumns = Arrays.asList(DataBaseNames.FILM_TYPES + ".FILM_TYPE_ID",
                DataBaseNames.FILM_TYPES + ".FILM_TYPE_NAME");
    }

    @Override
    public void add(FilmType item) {
        try {
            item.setFilmTypeID(generateTypeId());
            String sql = getInsertSqlForType(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<FilmType> items) {
        for (FilmType filmType : items){
            add(filmType);
        }
    }

    @Override
    public void update(FilmType item) {
        try {
            String sql = getUpdateSqlForType(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(FilmType item) {
        SqlSpecification specification = (SqlSpecification) specificationFactory.getFilmTypeByIdSpecification(item.getFilmTypeID());
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
    public List<FilmType> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List <FilmType> types = executeSelect(sql);
            return types;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateTypeId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.FILM_TYPE_ID_SEQUENCE));
    }

    private String getInsertSqlForType(FilmType filmType){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForType(filmType);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.FILM_TYPES, objectColumnValues);
        return sql;
    }

    private String getUpdateSqlForType(FilmType filmType){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForType(filmType);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.FILM_TYPES, objectColumnValues);
        return sql;
    }

    private List<FilmType> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<FilmType> filmTypes = parseResultSet(resultSet);
        resultSet.close();
        return filmTypes;
    }

    private List<FilmType> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<FilmType> filmTypes = new ArrayList<FilmType>();
        while (resultSet.next()) {
            long filmTypeId = resultSet.getLong("FILM_TYPE_ID");
            String filmTypeName = resultSet.getString("FILM_TYPE_NAME");
            FilmType filmType = new FilmType(filmTypeId, filmTypeName);
            filmTypes.add(filmType);
        }
        return filmTypes;
    }

    private ObjectColumnValues getObjectColumnValuesForType(FilmType filmType){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("FILM_TYPE_ID", String.valueOf(filmType.getFilmTypeID()));
        objectColumnValues.setValueByColumnName("FILM_TYPE_NAME", String.valueOf(filmType.getFilmTypeName()));
        objectColumnValues.setIdColumnName("FILM_TYPE_ID");
        objectColumnValues.setObjectId(String.valueOf(filmType.getFilmTypeID()));
        return objectColumnValues;
    }
}
