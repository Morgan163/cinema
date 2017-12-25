package specifications.sql.impl;

import db.DataBaseNames;
import model.user.User;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 25.12.2017.
 */
public class UserByRoleIdSqlSpecification implements SqlSpecification<User> {
    private long roleId;
    private String tableName;

    public UserByRoleIdSqlSpecification(long roleId) {
        this.roleId = roleId;
        tableName = DataBaseNames.USERS;
    }

    @Override
    public boolean specified(User source) {
        return roleId == source.getUserRole().getRoleID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.ROLE_ID = %d", tableName, roleId);
    }
}
