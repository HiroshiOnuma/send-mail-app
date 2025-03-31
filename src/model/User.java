package model;

public class User {
    private Integer userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userRole;

    // 新規登録用コンストラクタ
    public User(String userName, String userEmail, String userPassword, String userRole) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    // データ取得用コンストラクタ
    public User(Integer userId, String userName, String userEmail, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    // セッター
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    // ゲッター
    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserRole() {
        return userRole;
    }
}
