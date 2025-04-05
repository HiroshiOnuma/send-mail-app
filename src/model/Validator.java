package model;

import java.util.regex.Pattern;

public class Validator {

    /**
     * 空文字かどうかの判断を行うメソッド
     * 
     * @param text
     * @param fieldName
     * @return エラーがある場合にはフィールド名を含んだエラーメッセージ、問題がなければ null
     */
    public static String validateInput(String text, String fieldName) {
        if (text == null || text.trim().isEmpty()) {
            return fieldName + "を入力してください。";
        }
        return null; // エラーなし
    }

    /**
     * メールアドレス形式かどうかの判断を行うメソッド
     * 
     * @param email メールアドレス
     * @param fieldName
     * @return エラーがある場合にはフィールド名を含んだエラーメッセージ、問題がなければ null
     */
    public static String validateEmail(String email, String fieldName) {

        // メールアドレス形式の正規表現
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Patternクラスを使い正規表現をコンパイル
        Pattern pattern = Pattern.compile(emailRegex);

        //正規表現と一致するか判断
        if(!pattern.matcher(email).matches()) {

            return fieldName + "形式で入力してください。";
        }
        return null;
    }

    // public static String validatePassword(String password) {
    // if(password == null || password.trim().isEmpty()) {
    // return "パスワードを入力してください。";
    // }
    // return null;
    // }

}
