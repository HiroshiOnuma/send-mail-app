<%@page contentType="text/html;charset=utf-8" %>
    <%@ page import="java.util.List" %>
        <%@ page import="model.User" %>
            <%@ page import="model.Project" %>
                <%@ page import="model.Recipient" %>
                    <% User user=(User) session.getAttribute("user"); if (user==null) {
                        response.sendRedirect("login.jsp"); return; } List<Project> projectList = (List<Project>)
                            request.getAttribute("projectList");
                            request.setAttribute("pageTitle", "配信先メール設定");
                            %>

                            <jsp:include page="WEB-INF/jsp/header.jsp" />
                            <div class="inner">
                                <div class="container main-container form-container mail-setting-container">
                                    <h2>配信先メール設定</h2>
                                   
                                    <% if(request.getAttribute("sendErrorMessage") !=null) { %>
                                        <p class="err-msg">
                                            <%= request.getAttribute("sendErrorMessage") %>
                                        </p>
                                        <% } %>
                                            <% Recipient recipient=(Recipient) request.getAttribute("recipient"); if
                                                (recipient !=null) { %>
                                                <form action="recipient-mail-setting" class="data-form send-mail-form"
                                                    method="post">
                                                    <input type="hidden" name="action" value="recipient-mail-send">
                                                    <input type="hidden" name="recipient-email"
                                                        value="<%= recipient.getRecipientEmail() %>">
                                                    <input type="hidden" name="recipient-name"
                                                        value="<%= recipient.getRecipientName() %>">

                                                    <div class="">
                                                        <label for="project-name">案件選択: </label>
                                                        <select name="projectId" id="">
                                                            <% if (projectList !=null) { for (Project project :
                                                                projectList) { %>
                                                                <option value="<%= project.getProjectId() %>">
                                                                    <%= project.getProjectName() %>
                                                                </option>
                                                                <% } } %>
                                                        </select>
                                                    </div>

                                                    <button type="submit" value="メール送信"
                                                        class="data-form-btn form-button send-mail-btn">メール送信</button>
                                                </form>
                                                <% } %>

                                </div>
                            </div>
                            <jsp:include page="WEB-INF/jsp/footer.jsp" />