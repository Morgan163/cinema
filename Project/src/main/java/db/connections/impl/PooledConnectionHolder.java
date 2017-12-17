package db.connections.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import db.DBSettingsConstants;
import db.connections.ConnectionHolder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

@ApplicationScoped
public class PooledConnectionHolder implements ConnectionHolder
{
    private static final String MIN_POOL_SIZE = "5";
    private static final String ACQUIRE_INCREMENT = "5";
    private static final String MAX_POOL_SIZE = "20";
    private ComboPooledDataSource cpds;
    private String propertyFileName;

    public PooledConnectionHolder(){
        this.propertyFileName = DBSettingsConstants.DB_PROPERTIES_FILE_NAME;
    }

    public PooledConnectionHolder(String propertyFileName){
        this.propertyFileName = propertyFileName;
    }

    public synchronized Connection getConnection() throws SQLException{
        return cpds.getConnection();
    }

    @PostConstruct
    private void init(){
        InputStream input = null;
        try
        {
            Locale.setDefault(Locale.ENGLISH);
            ClassLoader classLoader = getClass().getClassLoader();
            input = classLoader.getResourceAsStream(propertyFileName);
            Properties properties = new Properties();
            properties.load(input);

            String dataBaseDriver = properties.getProperty(DBSettingsConstants.DB_DRIVER_CLASS_PROPERTY_NAME);
            String dataBaseUrl = properties.getProperty(DBSettingsConstants.DB_URL_PROPERTY_NAME);
            String dataBaseUser = properties.getProperty(DBSettingsConstants.DB_USER_PROPERTY_NAME);
            String dataBasePassword = properties.getProperty(DBSettingsConstants.DB_PASSWORD_PROPERTY_NAME);
            int minPoolSize = Integer.valueOf(properties.getProperty(DBSettingsConstants.DB_MIN_POOL_SIZE_PROPERTY_NAME,MIN_POOL_SIZE));
            int acquireIncrement = Integer.valueOf(properties.getProperty(DBSettingsConstants.DB_ACQUIRE_INCREMENT__PROPERTY_NAME, ACQUIRE_INCREMENT));
            int maxPoolSize = Integer.valueOf(properties.getProperty(DBSettingsConstants.DB_MAX_POOL_SIZE__PROPERTY_NAME, MAX_POOL_SIZE));

            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(dataBaseDriver); //loads the jdbc driver
            cpds.setJdbcUrl(dataBaseUrl);
            cpds.setUser(dataBaseUser);
            cpds.setPassword(dataBasePassword);
            cpds.setMinPoolSize(minPoolSize);
            cpds.setAcquireIncrement(acquireIncrement);
            cpds.setMaxPoolSize(maxPoolSize);
        }
        catch (PropertyVetoException e)
        {
            throw new IllegalStateException(e);
        } catch (FileNotFoundException e)
        {
            throw new IllegalStateException(e);
        } catch (IOException e)
        {
            throw new IllegalStateException(e);
        }
        finally
        {
            if (input != null){
                try
                {
                    input.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
