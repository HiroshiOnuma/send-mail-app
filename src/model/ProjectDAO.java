package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 * 案件のCRUD処理を担当するクラス
 * 
 */
public class ProjectDAO extends BaseDAO {

    /**
     * DBのprojectsテーブルに案件を登録するメソッド
     *
     * @param projectName        登録する案件の名前（必須）
     * @param projectDescription 登録する案件の概要（任意）
     * @return 登録成功時はProjectオブジェクト、失敗時はnullを返す
     */
    public Project projectInsert(String projectName, String projectDescription) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO projects (project_name, project_description) VALUES (?, ?)";

            // 生成されたキーを取得可能にする
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, projectName);
            pstmt.setString(2, projectDescription);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 自動生成されたキー（project_id）を取得
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int projectId = rs.getInt(1);
                    return new Project(projectId, projectName, projectDescription);
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
     * DBのprojectsテーブルから案件情報を取得するメソッド
     *
     * @return List<Project> 取得した案件情報を格納したProjectオブジェクトのリスト
     * 
     * @throws SQLException SQL処理中にエラーが発生した場合
     */
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT project_id, project_name, project_description FROM projects";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                String projectDesc = rs.getString("project_description");

                Project project = new Project(projectId, projectName, projectDesc);
                projects.add(project);
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
        return projects;
    }

    /*
     * DBのprojectsテーブルからidを指定して案件情報を取得するメソッド
     * 
     * @param int projectId
     * 
     * @return Project
     */
    public Project getProjectById(int projectId) {
        String sql = "SELECT project_id, project_name, project_description, created_at FROM projects WHERE project_id = ?";
        Project project = null;
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String projectName = rs.getString("project_name");
                    String projectDesc = rs.getString("project_description");
                    Timestamp createdAt = rs.getTimestamp("created_at");

                    project = new Project(projectId, projectName, projectDesc, createdAt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }
}
