package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import model.*;

@WebServlet({ "/login", "/logout" })

public class AuthServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // ログイン・ログアウトのリクエストに対してパスを切り替えるために使用
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            req.getRequestDispatcher("login.jsp").forward(req, res);
            return;
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // ログイン・ログアウトのリクエストに対してパスを切り替えるために使用
        String path = req.getServletPath();
        if ("/login".equals(path)) {

            String userEmail = req.getParameter("user-email");
            req.setAttribute("userEmail", userEmail);

            String userPass = req.getParameter("user-password");
            req.setAttribute("userPass", userPass);

            // バリデーション
            String emailError = Validator.validateInput(userEmail, "メールアドレス");
            String passwordError = Validator.validateInput(userPass, "パスワード");

            if (emailError != null || passwordError != null) {
                req.setAttribute("emailError", emailError);
                req.setAttribute("passwordError", passwordError);
                req.getRequestDispatcher("login.jsp").forward(req, res);
            } else {
                try {
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.userLogin(userEmail, userPass);

                    if (user != null) {
                        // ログイン成功した場合、セッションにユーザー情報を保存
                        HttpSession session = req.getSession();
                        session.setAttribute("user", user);

                        // ダッシュボードページにリダイレクト（成功した場合）
                        res.sendRedirect("dashboard.jsp");
                        return;
                    } else {
                        // ログイン失敗の場合、エラーメッセージを表示
                        req.setAttribute("loginError", "メールアドレスまたはパスワードが間違っています");
                        req.getRequestDispatcher("login.jsp").forward(req, res);
                    }

                } catch (Exception e) {
                    // 例外が発生した場合、エラーメッセージを表示
                    e.printStackTrace();
                    req.setAttribute("error", "ログイン処理中にエラーが発生しました。");
                    req.getRequestDispatcher("login.jsp").forward(req, res);
                }
            }
        } else if ("/logout".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            res.sendRedirect("login");
            return;
        }

    }

}
