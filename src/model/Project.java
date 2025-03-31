package model;
import java.sql.Timestamp;

/*
 * 案件のクラス
 */
public class Project {
    private Integer projectId;
    private String projectName;
    private String projectDescription;
    private Timestamp createdAt;

    // 案件オブジェクトの生成(新規登録用)
    public Project(Integer projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }
    // 案件オブジェクトの生成(データ取得用)
    public Project(Integer projectId, String projectName, String projectDescription, Timestamp createdAt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.createdAt = createdAt;
    }

    //セッター
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    //ゲッター
    public Integer getProjectId() {
        return projectId;
    }
    public String getProjectName() {
        return projectName;
    }
    public String getProjectDescription() {
        return projectDescription;
    }
    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
