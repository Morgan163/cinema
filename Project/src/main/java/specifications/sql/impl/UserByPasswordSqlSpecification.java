package specifications.sql.impl;

import db.DataBaseNames;
import model.user.User;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class UserByPasswordSqlSpecification implements SqlSpecification<User>
{
    private String tableName;
    private String password;

    public UserByPasswordSqlSpecification(String password){
        tableName = DataBaseNames.USERS;
        this.password = password;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        StringBuilder clauses = new StringBuilder();
        clauses.append(tableName).append(".password").
                append(" = ").append(password);
        return clauses.toString();
    }

    public boolean specified(User source){
        return !(password == null) && password.equals(source.getPassword());
    }
}
