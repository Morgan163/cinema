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
        return String.format("%s.role_id = %s.rode_id", DataBaseNames.USER_ROLES, DataBaseNames.USERS);
    }

    public boolean specified(Object source){
        throw new UnsupportedOperationException("specified is not supported for this spec");
    }
}
