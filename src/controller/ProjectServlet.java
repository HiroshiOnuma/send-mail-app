package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import model.*;

@WebServlet({ "/project-form", "/projects", "/project-detail", "/project-edit" })

public class ProjectServlet extends HttpServlet {

    // GETリクエスト
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String path = req.getServletPath(); // アクセスされたURLを取得
        if ("/project-form".equals(path)) {
            req.getRequestDispatcher("project-register.jsp").forward(req, res);
        } else if ("/projects".equals(path)) {

            // 以下にProjectDAOクラスのクラスのgetProjectsメソッドを呼び出し、案件一覧画面へフォワードする
            ProjectDAO projectDAO = new ProjectDAO();
            List<Project> projectList = projectDAO.getProjects();
            req.setAttribute("projectList", projectList);
            req.getRequestDispatcher("projects.jsp").forward(req, res);

        } else if ("/project-detail".equals(path)) {
            String projectIdParam = req.getParameter("projectId");

            if (projectIdParam == null || projectIdParam.isEmpty()) {
                res.sendRedirect("/projects"); // IDがない場合は案件一覧へリダイレクト
                return;
            }
            try {
                int projectId = Integer.parseInt(projectIdParam);
                ProjectDAO projectDAO = new ProjectDAO();
                Project project = projectDAO.getProjectById(projectId);

                if (project == null) {
                    res.sendRedirect("/projects");
                    return;
                }

                req.setAttribute("project", project);
                req.getRequestDispatcher("project-detail.jsp").forward(req, res);
            } catch (NumberFormatException e) {
                res.sendRedirect("projects");
            }
        } else if ("/project-edit".equals(path)) {
            String projectIdParam = req.getParameter("projectId");

            if (projectIdParam == null || projectIdParam.isEmpty()) {
                res.sendRedirect("/projects"); // IDがない場合は案件一覧へリダイレクト
                return;
            }
            try {
                int projectId = Integer.parseInt(projectIdParam);
                ProjectDAO projectDAO = new ProjectDAO();
                Project project = projectDAO.getProjectById(projectId);

                if (project == null) {
                    res.sendRedirect("/projects");
                    return;
                }

                req.setAttribute("project", project);
                req.getRequestDispatcher("project-edit.jsp").forward(req, res);
            } catch (NumberFormatException e) {
                res.sendRedirect("/projects");
            }
        }
    }

    // POSTリクエスト
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        String projectName = req.getParameter("project-name");
        req.setAttribute("projectName", projectName);
        String projectDesc = req.getParameter("project-desc");
        req.setAttribute("projectDesc", projectDesc);
        String projectIdParam = req.getParameter("projectId");

        // 案件の削除・更新のリクエストに対してaction属性を切り替えるために使用
        String action = req.getParameter("action");
        ProjectDAO projectDao = new ProjectDAO();

        // 案件削除処理
        if ("delete".equals(action)) {
            if (projectIdParam == null || projectIdParam.isEmpty()) {
                res.sendRedirect("projects");
                return;
            }
            int projectId;
            try {
                projectId = Integer.parseInt(projectIdParam);

            } catch (NumberFormatException e) {
                res.sendRedirect("projects");
                e.printStackTrace();
                return;
            }
            boolean deleted = projectDao.deleteProjectById(projectId);
            if (deleted) {
                res.sendRedirect("projects");
            } else {
                req.setAttribute("error", "案件削除処理中にエラーが発生しました。");
                req.getRequestDispatcher("project-detail.jsp").forward(req, res);
            }
            return;
        }

        // バリデーション
        String projectNameError = Validator.validateInput(projectName, "案件名");
        String projectDescError = Validator.validateInput(projectDesc, "案件概要");

        if (projectNameError != null || projectDescError != null) {
            req.setAttribute("projectName", projectName);
            req.setAttribute("projectDesc", projectDesc);
            req.setAttribute("projectNameError", projectNameError);
            req.setAttribute("projectDescError", projectDescError);

            String forwardPage = "update".equals(action) ? "project-edit.jsp" : "project-register.jsp";
            req.getRequestDispatcher(forwardPage).forward(req, res);
            return;
        }

        // 案件更新処理
        if ("update".equals(action)) {
            if (projectIdParam == null || projectIdParam.isEmpty()) {
                res.sendRedirect("projects");
                return;
            }
            int projectId;
            try {
                projectId = Integer.parseInt(projectIdParam);

            } catch (NumberFormatException e) {
                res.sendRedirect("projects");
                e.printStackTrace();
                return;
            }
            boolean updated = projectDao.updateProjectById(projectId, projectName, projectDesc);
            if (updated) {
                res.sendRedirect("projects");
            } else {
                req.setAttribute("error", "案件更新処理中にエラーが発生しました。");
                req.getRequestDispatcher("project-detail.jsp").forward(req, res);
            }
            return;
        }

        // 案件登録処理
        try {
            Project project = projectDao.projectInsert(projectName, projectDesc);

            if (project != null) {
                HttpSession session = req.getSession();
                session.setAttribute("project", project);

                // ダッシュボードページにリダイレクト（成功した場合）
                res.sendRedirect("dashboard.jsp");
            } else {
                // 案件登録失敗の場合、エラーメッセージを表示し、案件登録画面へ戻る
                req.setAttribute("projectInsertError", "案件登録に失敗しました。");
                req.getRequestDispatcher("project-register.jsp").forward(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "案件登録処理中にエラーが発生しました。");
            req.getRequestDispatcher("project-register.jsp").forward(req, res);
        }
    }
}
