package specifications.sql.impl;

import db.DataBaseNames;
import model.user.User;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

public class UserByLoginSqlSpecification implements SqlSpecification<User>
{
    private String tableName;
    private String login;

    public UserByLoginSqlSpecification(String login){
        tableName = DataBaseNames.USERS;
        this.login = login;
    }

    public List<String> getSourceTables(){
        return Collections.singletonList(tableName);
    }

    public String toSqlClause(){
        StringBuilder clauses = new StringBuilder();
        clauses.append(tableName).append(".login").
                append(" = ").append(login);
        return clauses.toString();
    }

    public boolean specified(User source){
        return !(login == null) && login.equals(source.getLogin());
    }
}
