package controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import model.*;

@WebServlet({ "/project-form", "/projects", "/project-detail", "/project-edit" })

public class ProjectServlet extends HttpServlet {

    // GET送信リクエスト
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

            // 個別案件ページへのGETリクエスト
        } else if ("/project-detail".equals(path)) {
            Project project = ServletHelper.fetchEntityOrRedirect(req, res, "projectId",
                    id -> new ProjectDAO().getProjectById(id), "/projects");

            if (project != null) {
                req.setAttribute("project", project);
                req.getRequestDispatcher("project-detail.jsp").forward(req, res);
            }

            // 案件編集ページへのGETリクエスト
        } else if ("/project-edit".equals(path)) {
            Project project = ServletHelper.fetchEntityOrRedirect(req, res, "projectId",
                    id -> new ProjectDAO().getProjectById(id), "/projects");

            if (project != null) {
                req.setAttribute("project", project);
                req.getRequestDispatcher("project-edit.jsp").forward(req, res);
            }
        }
    }

    // POST送信リクエスト
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
            ServletHelper.processEntityOperation(req, res, "projectId", "projects",
                    (int id) -> projectDao.deleteProjectById(id), "projects", "project-detail.jsp",
                    "案件削除処理中にエラーが発生しました。");
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
            ServletHelper.processEntityOperation(req, res, "projectId", "projects",
                    (int id) -> projectDao.updateProjectById(id, projectName, projectDesc),
                    "projects", "project-detail.jsp", "案件更新処理中にエラーが発生しました。");
            return;
        }

        // 案件登録処理
        ServletHelper.InsertEntity(req, res, () -> projectDao.projectInsert(projectName, projectDesc), "dashboard.jsp",
                "project-register.jsp", "案件登録処理中にエラーが発生しました。");
    }
}
