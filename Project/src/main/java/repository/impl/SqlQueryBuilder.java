package repository.impl;

import specifications.sql.SqlSpecification;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by niict on 13.12.2017.
 */
public class SqlQueryBuilder {

    public static String buildSelectQueryBySQLSpecification(List<String> nestedTablesColumns, SqlSpecification sqlSpecification){
        String tableNames = resolveNamesForSqlQuery(sqlSpecification);
        StringBuilder tableColumns = new StringBuilder();
        for (int i = 0; i < nestedTablesColumns.size() - 1; i++){
            tableColumns.append(nestedTablesColumns.get(i))
                    .append(", ");
        }
        tableColumns.append(nestedTablesColumns.get(nestedTablesColumns.size() - 1))
                .append(" ");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ")
                .append(nestedTablesColumns)
                .append(" FROM ").append(tableNames)
                .append(" WHERE ").append(sqlSpecification.toSqlClause());
        return sql.toString();
    }

    public static String buildDeleteQueryBySQLSpecification(SqlSpecification sqlSpecification){
        String tableNames = resolveNamesForSqlQuery(sqlSpecification);
        StringBuilder tableColumns = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE ")
                .append(" FROM ").append(tableNames)
                .append(" WHERE ").append(sqlSpecification.toSqlClause());
        return sql.toString();
    }

    public static String buildInsertQuery(String tableName, Map<String, String> paramsMap){
        Set<String> columnNames = paramsMap.keySet();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (String columnName : columnNames){
            columns.append(columnName).append(", ");
            String value = paramsMap.get(columnName);
            values.append(value).append(", ");
        }
        columns.delete(columns.length()-2, columns.length()-1);
        values.delete(values.length()-2, values.length()-1);
        columns.insert(0, "(");
        values.insert(0, "(");
        columns.append(")");
        values.append(")");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(tableName).append(" ").append(columns)
                .append(" VALUES ").append(values);
        return sql.toString();
    }

    private static String resolveNamesForSqlQuery(SqlSpecification sqlSpecification){
        StringBuilder tableNames = new StringBuilder();
        List sourceTables = sqlSpecification.getSourceTables();
        for (int i = 0; i < sourceTables.size() - 1; i++){
            tableNames.append(sourceTables.get(i))
                    .append(", ");
        }
        tableNames.append(sourceTables.get(sourceTables.size()-1));
        return tableNames.toString();
    }
}
