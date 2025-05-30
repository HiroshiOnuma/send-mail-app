package model;

import java.time.LocalDateTime;

/**
 * 案件のクラス
 */
public class Project {
    private Integer projectId;
    private String projectName;
    private String projectDescription;
    private LocalDateTime createdAt;

    public Project() {
    }

    // 案件オブジェクトの生成(新規登録・更新用)
    public Project(Integer projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    // 案件オブジェクトの生成(データ取得用)
    public Project(Integer projectId, String projectName, String projectDescription, LocalDateTime createdAt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.createdAt = createdAt;
    }

    // セッター
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    // ゲッター
    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
