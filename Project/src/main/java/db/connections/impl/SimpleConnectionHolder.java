package db.connections.impl;

import db.DBSettingsConstants;
import db.connections.ConnectionHolder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

/**
 * Created by niict on 24.12.2017.
 */
@ApplicationScoped
public class SimpleConnectionHolder implements ConnectionHolder {
    private DataSource dataSource;
    private Connection connection;
    String dataBaseDriver;
    String dataBaseUrl;
    String dataBaseUser;
    String dataBasePassword;
    public SimpleConnectionHolder() {
    }

    @PostConstruct
    private void init(){
        InputStream input = null;
        try
        {
            Locale.setDefault(Locale.ENGLISH);
//            ClassLoader classLoader = getClass().getClassLoader();
//            input = classLoader.getResourceAsStream(DBSettingsConstants.DB_PROPERTIES_FILE_NAME);
//            Properties properties = new Properties();
//            properties.load(input);
//            dataBaseDriver = properties.getProperty(DBSettingsConstants.DB_DRIVER_CLASS_PROPERTY_NAME);
//            dataBaseUrl = properties.getProperty(DBSettingsConstants.DB_URL_PROPERTY_NAME);
//            dataBaseUser = properties.getProperty(DBSettingsConstants.DB_USER_PROPERTY_NAME);
//            dataBasePassword = properties.getProperty(DBSettingsConstants.DB_PASSWORD_PROPERTY_NAME);;
//
//            Class.forName(dataBaseDriver);
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:/OracleDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
        }

    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
//        connection = DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePassword);
//        return connection;
    }

    @Override
    public void closeAll() {

    }
}
