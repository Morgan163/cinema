package db.connections;

import java.sql.Connection;

public interface ConnectionHolder
{
    Connection getConnection();
}
