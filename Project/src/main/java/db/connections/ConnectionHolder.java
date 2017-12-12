package db.connections;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionHolder
{
    Connection getConnection() throws SQLException;
}
