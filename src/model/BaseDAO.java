package model;

import java.sql.*;

/**
 * データベース接続関連のベースになるクラス
 * 
 * 抽象クラス
 * 
 */
public abstract class BaseDAO {

/**
 * データベース接続を呼び出すメソッド
 * 
 * @return Connectionオブジェクト データベース接続オブジェクト
 */
    protected Connection getConnection() {
        DBConnection dbConnection = new DBConnection();
        return dbConnection.createConnection();
    }

}
