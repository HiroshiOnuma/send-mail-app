package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 配信先のCRUD処理を担当するクラス
 * 
 */
public class RecipientDAO extends BaseDAO {

    /**
     * DBのrecipientsテーブルに配信先を登録するメソッド
     *
     * @param recipientName        登録する配信先の名前（必須）
     * @param recipientEmail 登録する配信先のメールアドレス（必須）
     * @return 登録成功時はRecipientオブジェクト、失敗時はnullを返す
     */
    public Recipient recipientInsert(String recipientName, String recipientEmail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO recipients (recipient_name, recipient_email) VALUES (?, ?)";

            // 生成されたキーを取得可能にする
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, recipientName);
            pstmt.setString(2, recipientEmail);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 自動生成されたキー（recipient_id）を取得
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int recipientId = rs.getInt(1);
                    return new Recipient(recipientId, recipientName, recipientEmail);
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {

            // リソースをクローズ
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

    /*
     * DBのrecipientsテーブルから配信先情報を取得するメソッド
     *
     * @return List<Recipient> 取得した配信先情報を格納したRecipientオブジェクトのリスト
     * 
     * @throws SQLException SQL処理中にエラーが発生した場合
     */
    public List<Recipient> getRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT recipient_id, recipient_name, recipient_email FROM recipients";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int recipientId = rs.getInt("recipient_id");
                String recipientName = rs.getString("recipient_name");
                String recipientDesc = rs.getString("recipient_email");

                Recipient recipient = new Recipient(recipientId, recipientName, recipientDesc);
                recipients.add(recipient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースをクローズ
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
        return recipients;
    }

    /*
     * DBのrecipientsテーブルからidを指定して配信先情報を取得するメソッド
     * 
     * @param int recipientId
     * 
     * @return Recipient
     */
    public Recipient getRecipientById(int recipientId) {
        String sql = "SELECT recipient_id, recipient_name, recipient_email, created_at FROM recipients WHERE recipient_id = ?";
        Recipient recipient = null;
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recipientId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String recipientName = rs.getString("recipient_name");
                    String recipientEmail = rs.getString("recipient_email");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    recipient = new Recipient(recipientId, recipientName, recipientEmail, createdAt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipient;
    }
}
