package repository.impl;

import db.connections.ConnectionHolder;
import specifications.sql.SqlSpecification;

import org.apache.log4j.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by niict on 13.12.2017.
 */
public class DataBaseHelper {
    @Inject
    private ConnectionHolder connectionHolder;
    private Connection connection;
    private static final Logger LOG = Logger.getLogger(DataBaseHelper.class);

    public DataBaseHelper() {
    }

    public String buildSelectQueryBySQLSpecification(List<String> nestedTablesColumns, SqlSpecification sqlSpecification){
        String tableNames = resolveTableNamesForSqlQuery(sqlSpecification);
        String columnNames = resolveColumnsNamesForSqlQuery(nestedTablesColumns);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(columnNames)
                .append(" FROM ").append(tableNames)
                .append(" WHERE ").append(sqlSpecification.toSqlClause());
        return sql.toString();
    }

    private String resolveColumnsNamesForSqlQuery(List<String> nestedTablesColumns){
        StringBuilder tableColumns = new StringBuilder();
        for (int i = 0; i < nestedTablesColumns.size() - 1; i++){
            tableColumns.append(nestedTablesColumns.get(i))
                    .append(", ");
        }
        tableColumns.append(nestedTablesColumns.get(nestedTablesColumns.size() - 1))
                .append(" ");
        return tableColumns.toString();
    }

    public String buildDeleteQueryBySQLSpecification(SqlSpecification sqlSpecification){
        String tableNames = resolveTableNamesForSqlQuery(sqlSpecification);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ")
                .append(" FROM ").append(tableNames)
                .append(" WHERE ").append(sqlSpecification.toSqlClause());
        return sql.toString();
    }

    private String resolveTableNamesForSqlQuery(SqlSpecification sqlSpecification){
        StringBuilder tableNames = new StringBuilder();
        List sourceTables = sqlSpecification.getSourceTables();
        for (int i = 0; i < sourceTables.size() - 1; i++){
            tableNames.append(sourceTables.get(i))
                    .append(", ");
        }
        tableNames.append(sourceTables.get(sourceTables.size()-1));
        return tableNames.toString();
    }

    public String buildInsertQuery(String tableName, ObjectColumnValues objectColumnValues){
        String columns = getColumnsForInsertQuery(objectColumnValues);
        String values = getValuesForInsertQuery(objectColumnValues);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(tableName).append(" ").append(columns)
                .append(" VALUES ").append(values);
        return sql.toString();
    }

    private String getColumnsForInsertQuery(ObjectColumnValues objectColumnValues){
        Set<String> columnNames = objectColumnValues.getColumnNamesSet();
        StringBuilder columns = new StringBuilder();
        for (String columnName : columnNames){
            columns.append(columnName).append(", ");
        }
        columns.delete(columns.length()-2, columns.length()-1);
        return wrapWithBrackets(columns);
    }

    private String getValuesForInsertQuery(ObjectColumnValues objectColumnValues){
        Set<String> columnNames = objectColumnValues.getColumnNamesSet();
        StringBuilder values = new StringBuilder();
        for (String columnName : columnNames){
            String value = objectColumnValues.getValueByColumnName(columnName);
            values.append(value).append(", ");
        }
        values.delete(values.length()-2, values.length()-1);
        return wrapWithBrackets(values);
    }

    private String wrapWithBrackets(StringBuilder target) {
        target.insert(0, "(");
        target.append(")");
        return target.toString();
    }

    public String buildUpdateQuery(String tableName, ObjectColumnValues objectColumnValues){
        String columnValues = getColumnValuesForUpdateQuery(objectColumnValues);
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName)
                .append(" SET ").append(columnValues)
                .append(" WHERE ").append(objectColumnValues.getIdColumnName()).append(" = ")
                .append(objectColumnValues.getObjectId());
        return sql.toString();
    }

    private String getColumnValuesForUpdateQuery(ObjectColumnValues objectColumnValues){
        Set<String> columnNames = objectColumnValues.getColumnNamesSet();
        StringBuilder columnValues = new StringBuilder();
        for (String columnName : columnNames){
            columnValues.append(columnName).append(" = ");
            String value = objectColumnValues.getValueByColumnName(columnName);
            columnValues.append(value).append(", ");
        }
        columnValues.delete(columnValues.length()-2, columnValues.length()-1);
        return columnValues.toString();
    }

    public void executeUpdateQuery(String sql) throws SQLException {
        System.out.println(sql);
        connection.createStatement().executeUpdate(sql);
    }

    public ResultSet executeSelectQuery(String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public String getNextValueForSequence(String tableIdSequence) throws SQLException {
        String sql = getQueryForSequence(tableIdSequence);
        ResultSet resultSet = executeSelectQuery(sql);
        resultSet.next();
        String value = resultSet.getString("NEXTVAL");
        resultSet.close();
        return value;
    }

    private String getQueryForSequence(String tableIdSequence){
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(tableIdSequence).append(".nextval ")
                .append("FROM DUAL");
        return sql.toString();
    }

    @PreDestroy
    private void destroy() {
        try {
            connection.close();
            connectionHolder.closeAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void init(){
        try {
            LOG.debug("init started...");
            connection = connectionHolder.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
