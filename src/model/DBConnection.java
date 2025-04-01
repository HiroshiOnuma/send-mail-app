package model;

import java.sql.*;

/*
 * データベース接続クラス
 */
public class DBConnection {
    String DB_URL = System.getenv("DB_URL");
    String DB_USER = System.getenv("DB_USER");
    String DB_PASS = System.getenv("DB_PASSWORD");

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (SQLException e) {
            System.out.println("データベースの接続に失敗しました。" + e.getMessage());

            return null;
        } 

    }
}