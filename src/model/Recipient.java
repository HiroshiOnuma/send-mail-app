package model;

import java.time.LocalDateTime;

/*
 * 配信先のクラス
 */
public class Recipient {
    private Integer recipientId;
    private String recipientName;
    private String recipientEmail;
    private LocalDateTime createdAt;

    // コンストラクタ
    public Recipient() {}
    
    public Recipient(Integer recipientId, String recipientName, String recipientEmail) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
    }

    // 配信先オブジェクトの生成(データ取得用)
    public Recipient(Integer recipientId, String recipientName, String recipientEmail, LocalDateTime createdAt) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
        this.createdAt = createdAt;
    }

    // セッター
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    // ゲッター
    public Integer getRecipientId() {
        return recipientId;
    }
    public String getRecipientName() {
        return recipientName;
    }
    public String getRecipientEmail() {
        return recipientEmail;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}