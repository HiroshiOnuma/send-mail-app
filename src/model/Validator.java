package model;

public class Validator {

    public static String validateInput(String text, String fieldName) {
        if(text == null || text.trim().isEmpty()) {
            return fieldName + "を入力してください。";
        }
        return null; //エラーなし
    }

    // public static String validatePassword(String password) {
    //     if(password == null || password.trim().isEmpty()) {
    //         return "パスワードを入力してください。";
    //     }
    //     return null;
    // }

}
