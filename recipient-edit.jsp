<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="model.User" %>
        <%@ page import="model.Recipient" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "配信先更新" ); %>

                <jsp:include page="WEB-INF/jsp/header.jsp" />
                <div class="inner">
                    <div class="container main-container form-container roject-detail-container">
                        <h2>配信先編集</h2>

                        <% Recipient recipient=(Recipient) request.getAttribute("recipient"); if (recipient !=null) { %>
                            <form action="recipient-form" class="data-form recipient-update-form" method="post">
                                <input type="hidden" name="recipientId" value="<%= recipient.getRecipientId() %>">
                                <input type="hidden" name="action" value="update">
                                <div class="">
                                    <label for="recipient-name">配信先名: </label>
                                    <input class="input" type="text" name="recipient-name" id="recipient-name"
                                        value="<%= recipient.getRecipientName() %>">

                                </div>
                                <div class="">
                                    <label for="recipient-desc">メールアドレス: </label>
                                    <input class="input" type="text" name="recipient-email" id="recipient-email"
                                        value="<%= recipient.getRecipientEmail() %>">

                                </div>
                                <button type="submit" value="配信先更新" class="form-button data-form-btn recipient-update-btn">配信先更新</button>
                            </form>
                            <% } %>

                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />