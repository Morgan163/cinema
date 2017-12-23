package repository.impl;

import db.DataBaseNames;
import model.Film;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class FilmRepositoryImpl implements Repository<Film> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    private List<String> neededSelectTableColumns;

    public FilmRepositoryImpl() {
        neededSelectTableColumns = Collections.singletonList(DataBaseNames.FILMS);
    }
    @Override
    public void add(Film item) {
        try {
            item.setFilmID(generateFilmId());
            String sql = getInsertSqlForFilm(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<Film> items) {
        for (Film film : items){
            add(film);
        }
    }

    @Override
    public void update(Film item) {
        try {
            String sql = getUpdateSqlForFilm(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Film item) {
        SqlSpecification specification = (SqlSpecification)specificationFactory.getFilmByIdSpecification(item.getFilmID());
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
    public List<Film> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List<Film> films = executeSelect(sql);
            return films;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateFilmId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.LINE_ID_SEQUENCE));
    }

    private String getInsertSqlForFilm(Film film){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForFilm(film);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.LINES, objectColumnValues);
        return sql;
    }

    private String getUpdateSqlForFilm(Film film){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForFilm(film);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.LINES, objectColumnValues);
        return sql;
    }

    private List<Film> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<Film> films = parseResultSet(resultSet);
        resultSet.close();
        return films;
    }

    private List<Film> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Film> films = new ArrayList<Film>();
        while (resultSet.next()) {
            long filmId = resultSet.getLong("FILM_ID");
            String filmName = resultSet.getString("FILM_NAME");
            Film film = new Film();
            film.setFilmID(filmId);
            film.setFilmName(filmName);
            films.add(film);
        }
        return films;
    }

    private ObjectColumnValues getObjectColumnValuesForFilm(Film film){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("Film_name", String.valueOf(film.getFilmName()));
        objectColumnValues.setValueByColumnName("FILM_ID", String.valueOf(film.getFilmID()));
        objectColumnValues.setValueByColumnName("FILM_TYPE_ID", String.valueOf(film.getFilmType().getFilmTypeID()));
        objectColumnValues.setValueByColumnName("AGE_LIMIT_ID", String.valueOf(film.getAgeLimitType().getAgeLimitID()));
        objectColumnValues.setIdColumnName("FILM_ID");
        objectColumnValues.setObjectId(String.valueOf(film.getFilmID()));
        return objectColumnValues;
    }
}
