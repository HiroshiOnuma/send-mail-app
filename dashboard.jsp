<%@page contentType="text/html;charset=utf-8" %>
    <%@page import="model.User" %>
        <% User user=(User) session.getAttribute("user"); if (user==null) { response.sendRedirect("login.jsp"); return;
            } request.setAttribute("pageTitle", "ダッシュボード" );%>
            <jsp:include page="WEB-INF/jsp/header.jsp" />

            <div class="inner">
                <div class="container main-container dashboard-container">
                    <h2>ようこそ、<%= user.getUserName() %>さん</h2>
                    <ul class="menu">
                        <li><a href="project-form">案件登録</a></li>
                        <li><a href="recipient-form">配信先登録</a></li>
                        <li><a href="projects">案件一覧</a></li>
                        <li><a href="recipients">配信先一覧</a></li>
                    </ul>
                </div>
            </div>
            <jsp:include page="WEB-INF/jsp/footer.jsp" />