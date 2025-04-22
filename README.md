# send-mail-app

## 概要
send-mail-appは、Web上から問い合わせフォームを通じて送信された内容を、指定されたメールアドレスに通知するJava Servletベースのアプリケーションです。

MVCモデルに基づいて構成されております。

現在は基本的な構造を実装中であり、今後メール送信処理やバリデーション機能などを追加予定です。

## プロジェクトフォルダ

apache-tomcat-9.0.98/webapps/send-mail-app/

## 開発環境

Java version: 21.0.6

Apache Tomcat: 9.0.98

MariaDB version 11.7.2


### apache-tomcat-9.0.98/lib/　に配置

JDBC Driver: mariadb-java-client-3.5.2.jar


### apache-tomcat-9.0.98/webapps/<ProjectName>/WEB-INF/lib/ に配置

servlet-api.jar

jBCrypt-0.4.3.jar

javax.mail.jar

javax.activation.jar