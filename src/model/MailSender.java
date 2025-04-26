package model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

public class MailSender {

    public boolean mailSend(Recipient recipient, Project project) {
        // プロパティファイルから認証に使用するデータを取得
        Properties prop = new Properties();
        try {
            // クラスパス上の"setting/mail.properties"を読み込む
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("setting/mail.properties");

            if (inputStream == null) {
                throw new IOException("mail.propertiesファイルがクラスパスに見つかりませんでした");
            }

            prop.load(inputStream);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // 送信元のGmailアドレス
        final String username = prop.getProperty("mailaddress");
        // Gmailのアカウントのアプリパスワード
        final String password = prop.getProperty("password");

        // SMTPサーバへの認証とメールセッションの作成
        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // メール送信準備
            Message message = new MimeMessage(session);
            // 送信元の設定
            message.setFrom(new InternetAddress(username));
            // 送信先の設定
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient.getRecipientEmail()));
            // 件名の設定
            message.setSubject(
                    "こんにちは、 " + recipient.getRecipientName() + "さん。\n" + "以下の案件情報を添付しました。\n" + project.getProjectName());
            // 本文の設定
            message.setText("こんにちは、 " + recipient.getRecipientName() + "さん。\n" + "以下の案件情報を送信しました。\n" +  project.getProjectDescription());

            // メールの送信
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            // 失敗時のメッセージ
            System.err.println("Email sent unsuccessfully ： " + e);
            e.printStackTrace();
            return false;
        }
    }
}