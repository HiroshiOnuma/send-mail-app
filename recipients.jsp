<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="java.util.List" %>
    <%@ page import="model.User" %>
        <%@ page import="model.Recipient" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } List<Recipient> recipientList = (List<Recipient>) request.getAttribute("recipientList");
                    request.setAttribute("pageTitle", "配信先一覧" ); %>

                    <jsp:include page="WEB-INF/jsp/header.jsp" />
                    <div class="inner">
                        <div class="container main-container recipients-container">
                            <h2>配信先一覧</h2>
                            <ul class="recipients-list">
                                <% for(Recipient recipient : recipientList) { %>
                                    <li class="recipients-list-menu"><a href="recipient-detail?recipientId=<%= recipient.getRecipientId() %>">
                                            <%= recipient.getRecipientName() %>
                                        </a></li>
                                    <% } %>
                            </ul>
                        </div>
                    </div>
                    <jsp:include page="WEB-INF/jsp/footer.jsp" />