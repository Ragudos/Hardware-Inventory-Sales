/** 
 *  Copyright 2025 Aaron Ragudos, Hanz Mapua, Peter Dela Cruz, Jerick Remo, Kurt Raneses, and the contributors of the project.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”),
 *  to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.hanzm_10.murico.swingapp.lib.database.mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.Properties;
import com.github.hanzm_10.murico.swingapp.constants.PropertyKey;
import com.github.hanzm_10.murico.swingapp.lib.database.AbstractSqlFactoryDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.SessionDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.UserCredentialsDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.UserDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.impl.mysql.MySqlSessionDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.impl.mysql.MySqlUserCredentialsDao;
import com.github.hanzm_10.murico.swingapp.lib.database.dao.impl.mysql.MySqlUserDao;
import com.github.hanzm_10.murico.swingapp.lib.io.PropertiesIO;

public final class MySqlFactoryDao extends AbstractSqlFactoryDao {
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static String DB_NAME;

    static {
        var properties = new Properties();

        try {
            PropertiesIO.loadProperties(MySqlFactoryDao.class, properties, "config");

            DB_URL = properties.getProperty(PropertyKey.Database.DB_URL);
            DB_USER = properties.getProperty(PropertyKey.Database.DB_USER);
            DB_PASSWORD = properties.getProperty(PropertyKey.Database.DB_PASSWORD);
            DB_NAME = properties.getProperty(PropertyKey.Database.DB_NAME);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load MySQL properties file", e);
        } catch (IllegalArgumentException e) {
            // DO nothing
        }
    }

    public static final Connection createConnection() throws SQLException, SQLTimeoutException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public SessionDao getSessionDao() {
        return new MySqlSessionDao();
    }

    @Override
    public UserCredentialsDao getUserCredentialsDao() {
        return new MySqlUserCredentialsDao();
    }

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }

}
