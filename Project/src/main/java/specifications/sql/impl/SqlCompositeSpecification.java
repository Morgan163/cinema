package specifications.sql.impl;

import specifications.sql.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class SqlCompositeSpecification<T> implements SqlSpecification<T> {
    private SqlSpecification<T> leftOperand;
    private SqlSpecification<T> rightOperand;
    private Operation operation;

    public List<String> getSourceTables() {
        ArrayList<String> tables = new ArrayList<String>();
        for (String tableName : leftOperand.getSourceTables()){
            if (!tables.contains(tableName)){
                tables.add(tableName);
            }
        }
        for (String tableName : rightOperand.getSourceTables()){
            if (!tables.contains(tableName)){
                tables.add(tableName);
            }
        }
        return tables;
    }

    public String toSqlClause() {
        return operation.createClause(leftOperand, rightOperand);
    }

    public boolean specified(T source) {
        return operation.perform(leftOperand, rightOperand, source);
    }

    public enum Operation{
        OR{
            String createClause(SqlSpecification leftOperand, SqlSpecification rightOperand) {
                StringBuilder clause = new StringBuilder();
                clause.append("(")
                        .append(rightOperand.toSqlClause())
                        .append(" OR ")
                        .append(leftOperand.toSqlClause())
                        .append(")");
                return clause.toString();
            }

            boolean perform(SqlSpecification leftOperand, SqlSpecification rightOperand, Object source) {
                return leftOperand.specified(source) ||
                        rightOperand.specified(source);
            }

        },
        AND{
            String createClause(SqlSpecification leftOperand, SqlSpecification rightOperand) {
                StringBuffer clause = new StringBuffer();
                clause.append("(")
                        .append(rightOperand.toSqlClause())
                        .append(" AND ")
                        .append(leftOperand.toSqlClause())
                        .append(")");
                return clause.toString();
            }

            boolean perform(SqlSpecification leftOperand, SqlSpecification rightOperand, Object source) {
                return leftOperand.specified(source) &&
                        rightOperand.specified(source);
            }
        };
        abstract String createClause(SqlSpecification leftOperand, SqlSpecification rightOperand);
        abstract boolean perform(SqlSpecification leftOperand, SqlSpecification rightOperand, Object source);
    }
}
