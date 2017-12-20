package specifications.sql.impl;

import specifications.CompositeSpecification;
import specifications.sql.SqlSpecification;

import java.util.ArrayList;
import java.util.List;

public class SqlCompositeSpecification implements SqlSpecification, CompositeSpecification
{
    private SqlSpecification leftOperand;
    private SqlSpecification rightOperand;
    private Operation operation;

    public SqlCompositeSpecification(SqlSpecification leftOperand, SqlSpecification rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public void setOperation(CompositeSpecification.Operation abstractOperation){
        Operation sqlCompositeOpetarion = Operation.valueOf(abstractOperation.name());
        setOperation(sqlCompositeOpetarion);
    }

    private void setOperation(Operation operation) {
        this.operation = operation;
    }

    public List<String> getSourceTables() {
        ArrayList<String> tables = new ArrayList<String>();
        leftOperand.getSourceTables();
        for (Object tableName : leftOperand.getSourceTables()){
            if (!tables.contains(tableName.toString())){
                tables.add(tableName.toString());
            }
        }
        for (Object tableName : rightOperand.getSourceTables()){
            if (!tables.contains(tableName.toString())){
                tables.add(tableName.toString());
            }
        }
        return tables;
    }

    public String toSqlClause() {
        return operation.createClause(leftOperand, rightOperand);
    }

    public boolean specified(Object source) {
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
