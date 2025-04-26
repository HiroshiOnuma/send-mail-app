package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import model.*;

@WebServlet({ "/recipient-form", "/recipients", "/recipient-detail", "/recipient-edit", "/recipient-mail-setting" })

public class RecipientServlet extends HttpServlet {

    // Get送信リクエスト
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String path = req.getServletPath(); // アクセスされたURLを取得
        if ("/recipient-form".equals(path)) {
            req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
        } else if ("/recipients".equals(path)) {

            // 以下にrecipientDAOクラスのgetRecipientsメソッドを呼び出し、配信先一覧画面へフォワードする
            RecipientDAO recipientDAO = new RecipientDAO();
            List<Recipient> recipientList = recipientDAO.getRecipients();
            req.setAttribute("recipientList", recipientList);
            req.getRequestDispatcher("recipients.jsp").forward(req, res);

            // 個別案件ページへのGETリクエスト
        } else if ("/recipient-detail".equals(path)) {
            Recipient recipient = ServletHelper.fetchEntityOrRedirect(req, res, "recipientId",
                    id -> new RecipientDAO().getRecipientById(id), "/recipients");

            if (recipient != null) {
                req.setAttribute("recipient", recipient);
                req.getRequestDispatcher("recipient-detail.jsp").forward(req, res);
            }

            // 配信先編集ページへのGETリクエスト
        } else if ("/recipient-edit".equals(path)) {
            Recipient recipient = ServletHelper.fetchEntityOrRedirect(req, res, "recipientId",
                    id -> new RecipientDAO().getRecipientById(id), "/recipient-edit");

            if (recipient != null) {
                req.setAttribute("recipient", recipient);
                req.getRequestDispatcher("recipient-edit.jsp").forward(req, res);
            }

            // 配信先メール設定ページへのGETリクエスト
        } else if ("/recipient-mail-setting".equals(path)) {
            String recipientIdParam = req.getParameter("recipientId");
            int recipientId = Integer.parseInt(recipientIdParam);

            // getProjectsメソッド、getRecipientByIdメソッドを呼び出し、メール送信設定画面へフォワードする
            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projectList = projectDAO.getProjects();

            RecipientDAO recipientDao = new RecipientDAO();
            Recipient recipient = recipientDao.getRecipientById(recipientId);

            if (recipient != null && !projectList.isEmpty()) {
                req.setAttribute("projectList", projectList);
                req.setAttribute("recipient", recipient);
                req.getRequestDispatcher("recipient-mail-setting.jsp").forward(req, res);
            }
        }
    }

    // POST送信リクエスト
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String recipientName = req.getParameter("recipient-name");
        String recipientEmail = req.getParameter("recipient-email");
        RecipientDAO recipientDao = new RecipientDAO();
        
        // 案件の削除・更新のリクエストに対してaction属性を切り替えるために使用
        String action = req.getParameter("action");

        // 配信先削除処理
        if ("delete".equals(action)) {
            ServletHelper.processEntityOperation(req, res, "recipientId", "recipients",
                    (int id) -> recipientDao.deleteRecipientById(id), "recipients", "recipient-detail.jsp",
                    "配信先削除処理中にエラーが発生しました。");
            return;
        }

        // 配信先へメール送信
        if ("recipient-mail-send".equals(action)) {
            ProjectDAO projectDao = new ProjectDAO();
            Recipient recipient = new Recipient();
            recipient.setRecipientEmail(recipientEmail);
            recipient.setRecipientName(recipientName);

            String projectIdStr = req.getParameter("projectId");
            int projectId = Integer.parseInt(projectIdStr);

            // projectIdからDBなどでProjectを取得
            Project project = projectDao.getProjectById(projectId);
            
            MailSender mail = new MailSender();
            boolean mailSend = mail.mailSend(recipient, project);
            if (mailSend) {
                req.setAttribute("sendMessage", "メッセージを送信しました。");
            } else {
                req.setAttribute("sendErrorMessage", "メッセージの送信に失敗しました。");
            }
            req.getRequestDispatcher("recipient-mail-setting.jsp").forward(req, res);
        }

        // バリデーション
        String recipientNameError = Validator.validateInput(recipientName, "配信先名");
        String recipientEmailError = Validator.validateInput(recipientEmail, "メールアドレス");
        String recipientEmailFormatError = Validator.validateEmail(recipientEmail, "メールアドレス");

        if (recipientNameError != null || recipientEmailError != null || recipientEmailFormatError != null) {
            req.setAttribute("recipientNameError", recipientNameError);
            req.setAttribute("recipientEmailError", recipientEmailError);
            req.setAttribute("recipientEmailFormatError", recipientEmailFormatError);
            req.getRequestDispatcher("recipient-register.jsp").forward(req, res);
            return;
        }

        // 配信先更新処理
        if ("update".equals(action)) {
            ServletHelper.processEntityOperation(req, res, "recipientId", "recipients",
                    (int id) -> recipientDao.updateRecipientById(id, recipientName, recipientEmail), "recipients",
                    "recipient-detail.jsp", "案件更新処理中にエラーが発生しました。");
            return;
        }

        // 配信先登録処理
        if ("recipient-form".equals(action)) {

            ServletHelper.InsertEntity(req, res, () -> recipientDao.recipientInsert(recipientName, recipientEmail),
                    "dashboard.jsp", "recipient-register.jsp", "配信先登録処理中にエラーが発生しました。");
            return;
        }

    }
}
