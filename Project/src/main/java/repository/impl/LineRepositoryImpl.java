package repository.impl;

import db.DataBaseNames;
import model.Line;
import model.Seat;
import org.apache.log4j.Logger;
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
public class LineRepositoryImpl implements Repository<Line> {
    private static final Logger LOG = Logger.getLogger(LineRepositoryImpl.class);
    @Inject
    private SpecificationFactory specificationFactory;
    @Inject
    private DataBaseHelper dataBaseHelper;
    @Inject
    private Repository<Seat> seatRepository;
    private List<String> neededSelectTableColumns;

    public LineRepositoryImpl() {
        neededSelectTableColumns = Arrays.asList(DataBaseNames.LINES+".LINE_ID",
                                                        DataBaseNames.LINES+".LINE_NUMBER");
    }

    @Override
    public void add(Line item) {
        try {
            item.setLineID(generateLineId());
            String sql = getInsertSqlForLine(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Iterable<Line> items) {
        for (Line line: items){
            add(line);
        }
    }

    @Override
    public void update(Line item) {
        try {
            String sql = getUpdateSqlForLine(item);
            dataBaseHelper.executeUpdateQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(Line item) {
        SqlSpecification specification = (SqlSpecification)specificationFactory.getLineByIdSpecification(item.getLineID());
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
    public List<Line> query(SqlSpecification sqlSpecification) {
        try {
            String sql = dataBaseHelper.buildSelectQueryBySQLSpecification(neededSelectTableColumns, sqlSpecification);
            LOG.debug("LineRepository Runs sql : " + sql);
            List <Line> lines = executeSelect(sql);
            return lines;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long generateLineId() throws SQLException {
        return Long.valueOf(dataBaseHelper.getNextValueForSequence(DataBaseNames.LINE_ID_SEQUENCE));
    }

    private String getInsertSqlForLine(Line line){
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForLine(line);
        String sql = dataBaseHelper.buildInsertQuery(DataBaseNames.LINES, objectColumnValues);
        return sql;
    }


    private String getUpdateSqlForLine(Line item) {
        ObjectColumnValues objectColumnValues = getObjectColumnValuesForLine(item);
        String sql = dataBaseHelper.buildUpdateQuery(DataBaseNames.LINES, objectColumnValues);
        return sql;
    }

    private List<Line> executeSelect(String sql) throws SQLException {
        ResultSet resultSet = dataBaseHelper.executeSelectQuery(sql);
        List<Line> lines = parseResultSet(resultSet);
        resultSet.close();
        return lines;
    }

    private List<Line> parseResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Line> lines = new ArrayList<Line>();
        while (resultSet.next()) {
            long lineId = resultSet.getLong("LINE_ID");
            int lineNumber = resultSet.getInt("LINE_NUMBER");
            List<Seat> seats = seatRepository.query((SqlSpecification)specificationFactory.getSeatByLineIdSpecification(lineId));
            Line line = new Line();
            line.setLineID(lineId);
            line.setLineNumber(lineNumber);
            for (Seat seat : seats){
                seat.setLine(line);
            }
            line.setSeats(seats);
            lines.add(line);
        }
        return lines;
    }

    private ObjectColumnValues getObjectColumnValuesForLine(Line line){
        ObjectColumnValues objectColumnValues = new ObjectColumnValues();
        objectColumnValues.setValueByColumnName("line_number", String.valueOf(line.getLineNumber()));
        objectColumnValues.setValueByColumnName("Line_id", String.valueOf(line.getLineID()));
        objectColumnValues.setValueByColumnName("theater_id", String.valueOf(line.getTheater().getTheaterID()));
        objectColumnValues.setIdColumnName("Line_id");
        objectColumnValues.setObjectId(String.valueOf(line.getLineID()));
        return objectColumnValues;
    }
}
