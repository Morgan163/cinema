package db.connections.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import db.connections.ConnectionHolder;

import java.sql.Connection;

public class PooledConnectionHolder implements ConnectionHolder
{
    private static final String DEFAULT_PROPERTY_FILE_NAME = "db.properties";
    private ComboPooledDataSource cpds;
    private String propertyFileName;

    public PooledConnectionHolder(){
        this.propertyFileName = DEFAULT_PROPERTY_FILE_NAME;
    }

    public PooledConnectionHolder(String propertyFileName){
        this.propertyFileName = propertyFileName;
    }

    public PooledConnectionHolder(ComboPooledDataSource cpds, String propertyFileName){
        this.cpds = cpds;
        this.propertyFileName = propertyFileName;
        init();
    }

    public Connection getConnection(){
        if (cpds == null){
            cpds = new ComboPooledDataSource();
            init();
        }
        return null;
    }

    private void init(){
        cpds.setDriverClass( "org.postgresql.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:postgresql://localhost/testdb" );
        cpds.setUser("swaldman");
        cpds.setPassword("test-password");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
    }
}
