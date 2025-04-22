<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="model.User" %>
        <%@ page import="model.Project" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "案件情報" ); %>

                <jsp:include page="WEB-INF/jsp/header.jsp" />
                <div class="inner">
                    <div class="container main-container project-detail-container">
                        <% Project project=(Project) request.getAttribute("project"); if (project !=null) { %>
                            <h2>
                                <%= project.getProjectName() %>
                            </h2>
                            <dl>
                                <div>
                                    <dt>作成日時: </dt>
                                    <dd>
                                        <%= project.getCreatedAt() %>
                                    </dd>
                                </div>
                                <div>
                                    <dt>案件名: </dt>
                                    <dd>
                                        <%= project.getProjectName() %>
                                    </dd>
                                </div>
                                <div>
                                    <dt>案件概要: </dt>
                                    <dd style="white-space: pre-line;"><%= project.getProjectDescription() %></dd>
                                </div>
                            </dl>
                            <% } %>
                                <div class="form-controller-area">
                                    <form class="edit-form" action="project-edit" method="get">
                                        <input type="hidden" name="projectId" value="<%= project.getProjectId() %>">
                                        <button type="submit" class="form-button">編集する</button>
                                    </form>
                                    <form class="delete-form" action="project-detail" method="post">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="projectId" value="<%= project.getProjectId() %>">
                                        <button type="submit" class="form-button delete-btn">削除する</button>
                                    </form>
                                </div>
                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />