package model;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/*
 * ユーザーのCRUD処理を担当するクラス
 * 
 */
public class UserDAO extends BaseDAO {
    public User userLogin(String userEmail, String userPassword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // ユーザー情報の取得
        try {
            conn = getConnection();
            String sql = "SELECT user_id, user_name, password, user_role FROM users WHERE email = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                // ハッシュ化されたパスワードと入力パスワードを比較
                if (BCrypt.checkpw(userPassword, storedHashedPassword)) {

                    // ユーザー情報をオブジェクトにマッピング
                    User user = new User(rs.getInt("user_id"),
                            rs.getString("user_name"),
                            userEmail,
                            rs.getString("user_role"));

                    return user;
                } else {
                    return null;
                }
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // データベース接続やSQL実行でエラーが発生した場合
            return null;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
