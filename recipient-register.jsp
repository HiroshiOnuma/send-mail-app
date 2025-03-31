<%@page contentType="text/html;charset=utf-8" %>

    <%@page import="model.User" %>
        <% boolean isPost="POST" .equalsIgnoreCase(request.getMethod()); %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "配信先登録" ); %>
                <jsp:include page="WEB-INF/jsp/header.jsp" />

                <div class="inner">
                    <div class="container main-container form-container recipient-register-container">
                        <h2>配信先登録</h2>

                        <form action="recipient-register" class="recipient-register-form" method="post">
                            <div class="">
                                <label for="recipient-name">配信先名: </label>
                                <input class="input" type="text" name="recipient-name" id="recipient-name"
                                    value="${recipientName}">
                                <% if (isPost && request.getAttribute("recipientNameError") !=null) { %>
                                    <span class="err-msg input-error validate-msg">
                                        <%= request.getAttribute("recipientNameError") %>
                                    </span>
                                    <% } %>
                            </div>
                            <div class="">
                                <label for="recipient-email">メールアドレス: </label>
                                <input type="text" name="recipient-email" id="recipient-email" value="${recipientEmail}" class="input">
                                <% if (isPost && request.getAttribute("recipientEmailError") !=null) { %>
                                    <span class="err-msg input-error validate-msg">
                                        <%= request.getAttribute("recipientEmailError") %>
                                    </span>
                                    <% } %>
                            </div>
                            <input type="submit" value="配信先登録" class="register-btn recipient-register-btn">
                        </form>
                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />