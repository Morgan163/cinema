package repository.impl;

import db.DataBaseNames;
import model.Film;
import model.Seance;
import org.apache.log4j.Logger;
import repository.Repository;
import specifications.factory.SpecificationFactory;
import specifications.sql.SqlSpecification;

import javax.inject.Inject;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class SeanceRepositoryImpl implements Repository<Seance> {
    private static final Logger LOG = Logger.getLogger(SeanceRepositoryImpl.class);
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    @Inject
    private Repository<Film> filmRepository;
    private List<String> neededSelectTableColumns;

    public SeanceRepositoryImpl() {
        neededSelectTableColumns = Arrays.asList(DataBaseNames.SEANCES+".SEANCE_ID",
                                                    DataBaseNames.SEANCES + ".BASE_PRICE_VALUE",
                                                    DataBaseNames.SEANCES + ".FILM_ID",
                                                    DataBaseNames.SEANCES + ".SEANCE_START_DATE");
    }

    @Override
    public void add(Seance item) {
        try {
            item.setSeanceID(generateSeanceId());
            String sql = getInsertSqlForSeance(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<Seance> items) {
        for (Seance seance : items){
            add(seance);
        }
    }

    @Override
    public void update(Seance item) {
        try {
            String sql = getUpdateSqlForSeance(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Seance item) {
        SqlSpecification specification = (SqlSpecification) specificationFactory.getSeanceByIdSpecification(item.getSeanceID());
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
    public List<Seance> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List <Seance> seances = executeSelect(sql);
            return seances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateSeanceId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.SEANCE_ID_SEQUENCE));
    }

    private String getInsertSqlForSeance(Seance seance){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForSeance(seance);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.SEANCES, objectColumnValues);
        LOG.debug(sql);
        return sql;
    }

    private String getUpdateSqlForSeance(Seance seance){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForSeance(seance);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.SEANCES, objectColumnValues);
        return sql;
    }

    private List<Seance> executeSelect(String sql) throws SQLException {
        System.out.println(sql);
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<Seance> seances = parseResultSet(resultSet);
        resultSet.close();
        return seances;
    }

    private List<Seance> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Seance> seances = new ArrayList<Seance>();
        while (resultSet.next()) {
            long seanceId = resultSet.getLong("SEANCE_ID");
            long filmId = resultSet.getLong("FILM_ID");
            int basePriceValue = resultSet.getInt("BASE_PRICE_VALUE");
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(resultSet.getTimestamp("SEANCE_START_DATE"));
            Film film = filmRepository.query((SqlSpecification)specificationFactory.getFilmByIdSpecification(filmId)).get(0);
            Seance seance = new Seance();
            seance.setSeanceID(seanceId);
            seance.setPriceValue(basePriceValue);
            seance.setSeanceStartDate(startDate);
            seance.setFilm(film);
            seances.add(seance);
        }
        return seances;
    }

    private ObjectColumnValues getObjectColumnValuesForSeance(Seance seance){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("FILM_ID", String.valueOf(seance.getFilm().getFilmID()));
        objectColumnValues.setValueByColumnName("SEANCE_ID", String.valueOf(seance.getSeanceID()));
        objectColumnValues.setValueByColumnName("BASE_PRICE_VALUE", String.valueOf(seance.getPriceValue()));
        java.sql.Timestamp startDate = new Timestamp(seance.getSeanceStartDate().getTimeInMillis());
        objectColumnValues.setValueByColumnName("SEANCE_START_DATE", String.format("TO_TIMESTAMP('%s', 'YYYY-MM-DD HH24:MI:SS.FF9')",String.valueOf(startDate)));
        objectColumnValues.setIdColumnName("SEANCE_ID");
        objectColumnValues.setObjectId(String.valueOf(seance.getSeanceID()));
        return objectColumnValues;
    }
}
