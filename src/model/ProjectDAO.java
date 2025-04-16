package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

/**
 * 案件のCRUD処理(登録・参照・更新・削除)を担当するクラス
 * 
 */
public class ProjectDAO extends BaseDAO {

    /**
     * DBのprojectsテーブルに案件を登録するメソッド
     *
     * @param projectName        登録する案件の名前
     * @param projectDesc 登録する案件の概要
     * @return 登録成功時はProjectオブジェクト、失敗時はnullを返す
     */
    public Project projectInsert(String projectName, String projectDesc) {
        String sql = "INSERT INTO projects (project_name, project_description) VALUES (?, ?)";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // 生成されたキーを取得可能にする

            pstmt.setString(1, projectName);
            pstmt.setString(2, projectDesc);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                // 自動生成されたキー（project_id）を取得
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int projectId = rs.getInt(1);
                        return new Project(projectId, projectName, projectDesc);
                    }
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DBのprojectsテーブルから案件情報を取得するメソッド
     *
     * @return List<Project> 取得した案件情報を格納したProjectオブジェクトのリスト
     * 
     * @throws SQLException SQL処理中にエラーが発生した場合
     */
    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT project_id, project_name, project_description FROM projects";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                String projectDesc = rs.getString("project_description");

                Project project = new Project(projectId, projectName, projectDesc);
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    /**
     * DBのprojectsテーブルからidを指定して案件情報を取得するメソッド
     * 
     * @param projectId 案件ID
     * 
     * @return Project Projectオブジェクト
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
                    LocalDateTime createdAtDateTime = createdAt.toLocalDateTime();
                    project = new Project(projectId, projectName, projectDesc, createdAtDateTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    /**
     * 案件を更新するメソッド
     * 
     * @param projectId   案件のID
     * @param projectName 案件の名前
     * @param projectDesc 案件概要
     * 
     * @return 更新に成功したら true そうでなければ false
     * 
     */
    public boolean updateProjectById(int projectId, String projectName, String projectDesc) {
        String sql = "UPDATE projects SET project_name = ?, project_description =? WHERE project_id = ?";

        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, projectName);
            pstmt.setString(2, projectDesc);
            pstmt.setInt(3, projectId);

            int result = pstmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 案件を削除するメソッド
     * 
     * @param projectId 案件のID
     * 
     * @return 削除に成功したら true そうでなければ false
     * 
     */
    public boolean deleteProjectById(int projectId) {
        String sql = "DELETE FROM projects WHERE project_id = ?";
        try (Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
