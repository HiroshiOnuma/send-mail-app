<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="java.util.List" %>
    <%@ page import="model.User" %>
        <%@ page import="model.Project" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } List<Project> projectList = (List<Project>) request.getAttribute("projectList");
                    request.setAttribute("pageTitle", "案件一覧" ); %>

                    <jsp:include page="WEB-INF/jsp/header.jsp" />
                    <div class="inner">
                        <div class="container main-container projects-container">
                            <h2>案件一覧</h2>
                            <ul class="projects-list">
                                <% for(Project project : projectList) { %>
                                    <li class="projects-list-menu"><a href="project-detail?projectId=<%= project.getProjectId() %>">
                                            <%= project.getProjectName() %>
                                        </a></li>
                                    <% } %>
                            </ul>
                        </div>
                    </div>
                    <jsp:include page="WEB-INF/jsp/footer.jsp" />