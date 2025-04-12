<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="model.User" %>
        <%@ page import="model.Project" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "案件更新" ); %>

                <jsp:include page="WEB-INF/jsp/header.jsp" />
                <div class="inner">
                    <div class="container main-container form-container roject-detail-container">
                        <h2>案件編集</h2>

                        <% Project project=(Project) request.getAttribute("project"); if (project !=null) { %>
                            <form action="project-form" class="data-form project-update-form" method="post">
                                <input type="hidden" name="projectId" value="<%= project.getProjectId() %>">
                                <input type="hidden" name="action" value="update">
                                <div class="">
                                    <label for="project-name">案件名: </label>
                                    <input class="input" type="text" name="project-name" id="project-name"
                                        value="<%= project.getProjectName() %>">

                                </div>
                                <div class="">
                                    <label for="project-desc">案件概要: </label>
                                    <textarea name="project-desc" id="project-desc"
                                        rows="10"><%= project.getProjectDescription() %></textarea>

                                </div>
                                <input type="submit" value="案件更新" class="data-form-btn project-update-btn">
                            </form>
                            <% } %>

                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />