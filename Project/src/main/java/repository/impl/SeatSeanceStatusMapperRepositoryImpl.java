package repository.impl;

import db.DataBaseNames;
import model.Seance;
import model.Seat;
import model.SeatSeanceStatus;
import model.SeatSeanceStatusMapper;
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
public class SeatSeanceStatusMapperRepositoryImpl implements Repository<SeatSeanceStatusMapper> {
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    @Inject
    private Repository<Seat> seatRepository;
    @Inject
    private Repository<Seance> seanceRepository;
    private List<String> neededSelectTableColumns;

    public SeatSeanceStatusMapperRepositoryImpl(){
        neededSelectTableColumns = Arrays.asList(DataBaseNames.SEATS_SEANCES + ".MAPPING_ID",
                DataBaseNames.SEATS_SEANCES + ".BOOK_KEY",
                DataBaseNames.SEATS_SEANCES + ".SEAT_ID",
                DataBaseNames.SEATS_SEANCES + ".SEANCE_ID",
                DataBaseNames.SEATS_SEANCES + ".STATUS_ID");
    }

    @Override
    public void add(SeatSeanceStatusMapper item) {
        try {
            item.setMappingId(generateMapperId());
            String sql = getInsertSqlForMapper(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<SeatSeanceStatusMapper> items) {
        for (SeatSeanceStatusMapper mapper : items){
            add(mapper);
        }
    }

    @Override
    public void update(SeatSeanceStatusMapper item) {
        try {
            String sql = getUpdateSqlForMapper(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(SeatSeanceStatusMapper item) {
        SqlSpecification specification = (SqlSpecification)specificationFactory.getMapperByIdSpecification(item.getMappingId());
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
    public List<SeatSeanceStatusMapper> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            List <SeatSeanceStatusMapper> mappers = executeSelect(sql);
            return mappers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateMapperId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.SEATS_SEANCES_STATUSES_ID_SEQUENCE));
    }

    private String getInsertSqlForMapper(SeatSeanceStatusMapper mapper){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForMapper(mapper);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.SEATS_SEANCES, objectColumnValues);
        return sql;
    }

    private String getUpdateSqlForMapper(SeatSeanceStatusMapper mapper){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForMapper(mapper);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.SEATS_SEANCES, objectColumnValues);
        return sql;
    }

    private List<SeatSeanceStatusMapper> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<SeatSeanceStatusMapper> mappers = parseResultSet(resultSet);
        resultSet.close();
        return mappers;
    }

    private List<SeatSeanceStatusMapper> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<SeatSeanceStatusMapper> mappers = new ArrayList<SeatSeanceStatusMapper>();
        while (resultSet.next()) {
            long mappingId = resultSet.getLong("MAPPING_ID");
            long seatId = resultSet.getLong("SEAT_ID");
            long seanceId = resultSet.getLong("SEANCE_ID");
            long statusId = resultSet.getLong("STATUS_ID");
            String bookKey = resultSet.getString("BOOK_KEY");
            Seat seat = seatRepository.query((SqlSpecification)specificationFactory.getSeatByIdSpecification(seatId)).get(0);
            Seance seance = seanceRepository.query((SqlSpecification)specificationFactory.getSeanceByIdSpecification(seanceId)).get(0);
            SeatSeanceStatus seatSeanceStatus = SeatSeanceStatus.getById(statusId);
            SeatSeanceStatusMapper mapper = new SeatSeanceStatusMapper(mappingId, seat, seance, seatSeanceStatus);
            mapper.setBookKey(bookKey);
            mappers.add(mapper);
        }
        return mappers;
    }

    private ObjectColumnValues getObjectColumnValuesForMapper(SeatSeanceStatusMapper mapper){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("MAPPING_ID", String.valueOf(mapper.getMappingId()));
        objectColumnValues.setValueByColumnName("SEAT_ID", String.valueOf(mapper.getSeat().getSeatID()));
        objectColumnValues.setValueByColumnName("SEANCE_ID", String.valueOf(mapper.getSeance().getSeanceID()));
        objectColumnValues.setValueByColumnName("STATUS_ID", String.valueOf(mapper.getSeatSeanceStatus().getStatusID()));
        objectColumnValues.setIdColumnName("MAPPING_ID");
        objectColumnValues.setObjectId(String.valueOf(mapper.getMappingId()));
        return objectColumnValues;
    }
}

