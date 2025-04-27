# send-mail-app



## 概要
send-mail-appは、Web上から問い合わせフォームを通じて送信された内容を、指定されたメールアドレスに通知するJava Servletベースのアプリケーションです。

MVCモデルに基づいて構成されております。

基本的な構造で、選択した配信先に対し、案件を選択し、メール送信が可能です。


## プロジェクトフォルダ
apache-tomcat-9.0.98/webapps/send-mail-app/


## 開発環境
Java version: 21.0.6

Apache Tomcat: 9.0.98

MariaDB version 11.7.2



## 以下、.jarファイルのCLASSPATHを通す


### apache-tomcat-9.0.98/lib/ に配置
JDBC Driver: mariadb-java-client-3.5.2.jar


### apache-tomcat-9.0.98/webapps/<ProjectName>/WEB-INF/lib/ に配置
servlet-api.jar

jBCrypt-0.4.3.jar

javax.mail.jar

javax.activation.jar



## DBテーブル設計


### データベース名
sendmail_app

### テーブル一覧


#### users
+------------+----------------------+------+-----+---------------------+----------------+
| Field      | Type                 | Null | Key | Default             | Extra          |
+------------+----------------------+------+-----+---------------------+----------------+
| user_id    | int(10) unsigned     | NO   | PRI | NULL                | auto_increment |
| user_name  | varchar(256)         | NO   |     | NULL                |                |
| email      | varchar(256)         | NO   | UNI | NULL                |                |
| password   | varchar(256)         | NO   |     | NULL                |                |
| user_role  | enum('user','admin') | NO   |     | user                |                |
| created_at | timestamp            | YES  |     | current_timestamp() |                |
| deleted_at | timestamp            | YES  |     | NULL                |                |
+------------+----------------------+------+-----+---------------------+----------------+


#### projects
+---------------------+------------------+------+-----+---------------------+----------------+
| Field               | Type             | Null | Key | Default             | Extra          |
+---------------------+------------------+------+-----+---------------------+----------------+
| project_id          | int(10) unsigned | NO   | PRI | NULL                | auto_increment |
| project_name        | varchar(255)     | NO   |     | NULL                |                |
| project_description | text             | NO   |     | NULL                |                |
| created_at          | timestamp        | YES  |     | current_timestamp() |                |
| deleted_at          | timestamp        | YES  |     | NULL                |                |
+---------------------+------------------+------+-----+---------------------+----------------+


#### recipients
+-----------------+------------------+------+-----+---------------------+----------------+
| Field           | Type             | Null | Key | Default             | Extra          |
+-----------------+------------------+------+-----+---------------------+----------------+
| recipient_id    | int(10) unsigned | NO   | PRI | NULL                | auto_increment |
| recipient_name  | varchar(255)     | NO   |     | NULL                |                |
| recipient_email | varchar(255)     | NO   | UNI | NULL                |                |
| created_at      | timestamp        | YES  |     | current_timestamp() |                |
| deleted_at      | timestamp        | YES  |     | NULL                |                |
+-----------------+------------------+------+-----+---------------------+----------------+



## データベース接続設定
src/model/DBConnection.java

String DB_URL = System.getenv("DB_URL"); // 環境変数を設定し、呼び出している

String DB_USER = System.getenv("DB_USER"); // 環境変数を設定し、呼び出している

String DB_PASS = System.getenv("DB_PASSWORD"); // 環境変数を設定し、呼び出している



## メール設定
WEB-INF/classes/setting/mail.propertiesを作成


### mail.propertiesの内容
mailaddress=送信元メールアドレス

password=アプリパスワード

mail.smtp.auth=true

mail.smtp.starttls.enable=true

mail.smtp.host=smtp.gmail.com

mail.smtp.port=587