package specifications.sql.impl;

import db.DataBaseNames;
import model.user.UserRole;
import specifications.sql.SqlSpecification;

import java.util.Arrays;
import java.util.List;

public class RoleIdEqualsUserRoleIdSqlSpecification implements SqlSpecification
{

    public List<String> getSourceTables(){
        return Arrays.asList(DataBaseNames.USER_ROLES, DataBaseNames.USERS);
    }

    public String toSqlClause(){
        StringBuilder sql = new StringBuilder();
        sql.append(DataBaseNames.USER_ROLES).append(".role_id")
                .append(" = ")
                .append(DataBaseNames.USERS).append(".role_id");
        return sql.toString();
    }

    public boolean specified(Object source){
        throw new UnsupportedOperationException("specified is not supported for this spec");
    }
}
