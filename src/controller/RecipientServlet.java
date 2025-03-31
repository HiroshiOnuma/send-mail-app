package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import model.*;

@WebServlet({ "/recipient-register", "/recipients", "/recipient-detail"})

public class RecipientServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        String path = req.getServletPath(); // アクセスされたURLを取得
        if ("/recipient-register".equals(path)) {
            req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
        } else if("/recipients".equals(path)) {
            
            // 以下にrecipientDAOクラスのgetRecipientsメソッドを呼び出し、配信先一覧画面へフォワードする
            RecipientDAO recipientDAO = new RecipientDAO();
            List<Recipient> recipientList = recipientDAO.getRecipients();
            req.setAttribute("recipientList", recipientList);
            req.getRequestDispatcher("recipients.jsp").forward(req, res);
        } else if("/recipient-detail".equals(path)) {
            String recipientIdParam = req.getParameter("recipientId");

            if(recipientIdParam == null || recipientIdParam.isEmpty()) {
                res.sendRedirect("/recipients"); //IDがない場合は配信先一覧へリダイレクト
                return;
            }
            try {
                int recipientId = Integer.parseInt(recipientIdParam);
                RecipientDAO recipientDAO = new RecipientDAO();
                Recipient recipient = recipientDAO.getRecipientById(recipientId);

                if(recipient == null) {
                    res.sendRedirect("/recipients");
                    return;
                }

                req.setAttribute("recipient", recipient);
                req.getRequestDispatcher("recipient-detail.jsp").forward(req, res);
            } catch(NumberFormatException e) {
                res.sendRedirect("recipients");
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String recipientName = req.getParameter("recipient-name");
        req.setAttribute("recipientName", recipientName);
        String recipientEmail = req.getParameter("recipient-email");
        req.setAttribute("recipientEmail", recipientEmail);

        // バリデーション
        String recipientNameError = Validator.validateInput(recipientName, "配信先名");
        String recipientEmailError = Validator.validateInput(recipientEmail, "配信先メールアドレス");

        if (recipientNameError != null || recipientEmailError != null) {
            req.setAttribute("recipientNameError", recipientNameError);
            req.setAttribute("recipientEmailError", recipientEmailError);
            req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
        } else {
            try {
                RecipientDAO recipientDAO = new RecipientDAO();
                Recipient recipient = recipientDAO.recipientInsert(recipientName, recipientEmail);

                if (recipient != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("recipient", recipient);

                    // ダッシュボードページにリダイレクト（成功した場合）
                    res.sendRedirect("dashboard.jsp");
                } else {
                    // 配信先登録失敗の場合、エラーメッセージを表示し、配信先登録画面へ戻る
                    req.setAttribute("recipientInsertError", "配信先登録に失敗しました。");
                    req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
                }
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "配信先登録処理中にエラーが発生しました。");
                req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
            }
        }
    }
}
