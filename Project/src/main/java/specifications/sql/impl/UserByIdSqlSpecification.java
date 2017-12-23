package specifications.sql.impl;

import db.DataBaseNames;
import model.user.User;
import specifications.sql.SqlSpecification;

import java.util.Collections;
import java.util.List;

/**
 * Created by niict on 23.12.2017.
 */
public class UserByIdSqlSpecification implements SqlSpecification<User> {
    private long userId;
    private String tableName;

    public UserByIdSqlSpecification(long userId) {
        this.userId = userId;
        tableName = DataBaseNames.USERS;
    }

    @Override
    public boolean specified(User source) {
        return userId == source.getUserID();
    }

    @Override
    public List<String> getSourceTables() {
        return Collections.singletonList(tableName);
    }

    @Override
    public String toSqlClause() {
        return String.format("%s.USER_ID = %d", tableName, userId);
    }
}
