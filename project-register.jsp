<%@page contentType="text/html;charset=utf-8" %>

    <%@page import="model.User" %>
        <% boolean isPost="POST" .equalsIgnoreCase(request.getMethod()); %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "案件登録" ); %>
                <jsp:include page="WEB-INF/jsp/header.jsp" />

                <div class="inner">
                    <div class="container main-container form-container project-register-container">
                        <h2>案件登録</h2>

                        <form action="project-register" class="project-register-form" method="post">
                            <div class="">
                                <label for="project-name">案件名: </label>
                                <input class="input" type="text" name="project-name" id="project-name"
                                    value="${projectName}">
                                <% if (isPost && request.getAttribute("projectNameError") !=null) { %>
                                    <span class="err-msg input-error validate-msg">
                                        <%= request.getAttribute("projectNameError") %>
                                    </span>
                                    <% } %>
                            </div>
                            <div class="">
                                <label for="project-desc">案件概要: </label>
                                <textarea name="project-desc" id="project-desc" rows="10"></textarea>
                                <% if (isPost && request.getAttribute("projectDescError") !=null) { %>
                                    <span class="err-msg input-error validate-msg">
                                        <%= request.getAttribute("projectDescError") %>
                                    </span>
                                    <% } %>
                            </div>
                            <input type="submit" value="案件登録" class="register-btn project-register-btn">
                        </form>
                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />