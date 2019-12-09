package com.javarush.task.task33.shortener.strategy;

import com.javarush.task.task33.shortener.Helper;

import java.sql.*;

public class JDBCMapStorageStrategy implements StorageStrategy {

    private static Connection connection;
    private static Statement statement;

    public JDBCMapStorageStrategy() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:mem:test");
            statement = connection.createStatement();
            statement.execute("create table data(" +
                    "key integer, " +
                    "value message_text);");
            statement.execute("INSERT into data VALUES (30, 'Half'), (5, 'And')");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containsKey(Long key) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM data WHERE key = " + key);
            ResultSet set = statement.getResultSet();
            set.next();
            long l = set.getLong("key");
            Helper.printMessage(String.valueOf(l));
            return key.equals(l);

        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        return false;
    }

    @Override
    public void put(Long key, String value) {

    }

    @Override
    public Long getKey(String value) {
        return null;
    }

    @Override
    public String getValue(Long id) {
        return null;
    }
}
