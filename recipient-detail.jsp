<%@page contentType="text/html;charset=utf-8" %>

    <%@ page import="model.User" %>
        <%@ page import="model.Recipient" %>
            <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp");
                return; } request.setAttribute("pageTitle", "配信先情報" ); %>

                <jsp:include page="WEB-INF/jsp/header.jsp" />
                <div class="inner">
                    <div class="container main-container recipient-detail-container">
                        <% Recipient recipient=(Recipient) request.getAttribute("recipient"); if (recipient !=null) { %>
                            <h2>
                                <%= recipient.getRecipientName() %>
                            </h2>
                            <dl>
                                <div>
                                    <dt>配信先名: </dt>
                                    <dd>
                                        <%= recipient.getRecipientName() %>
                                    </dd>
                                </div>
                                <div>
                                    <dt>メールアドレス: </dt>
                                    <dd>
                                        <%= recipient.getRecipientEmail() %>
                                    </dd>
                                </div>
                            </dl>
                            <% } %>
                    </div>
                </div>
                <jsp:include page="WEB-INF/jsp/footer.jsp" />