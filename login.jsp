<%@page contentType="text/html;charset=utf-8" %>
    <% boolean isPost="POST" .equalsIgnoreCase(request.getMethod()); %>
        <!DOCTYPE html>
        <html lang="ja">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="src/css/style.css" class="css">
            <title>ログイン</title>
        </head>

        <body class="login-page">
            <div class="inner">
                <div class="container main-container form-container login-container">
                    <h2>ログイン画面</h2>
                    <% String loginError=(String) request.getAttribute("loginError"); if(loginError !=null) { %>
                        <div>
                            <p class="err-msg">
                                <%= loginError %>
                            </p>
                        </div>
                        <% } %>
                            <form action="login" class="login-form" method="post">
                                <div class="">
                                    <label for="user-email">メールアドレス: </label>
                                    <input class="input" type="text" name="user-email" id="user-email" value="${userEmail}">
                                    <% if (isPost && request.getAttribute("emailError") !=null) { %>
                                        <span class="err-msg input-error validate-msg">
                                            <%= request.getAttribute("emailError") %>
                                        </span>
                                        <% } %>
                                </div>
                                <div class="">
                                    <label for="user-password">パスワード: </label>
                                    <input class="input" type="password" name="user-password" id="user-password" value="${userPass}">
                                    <% if (isPost && request.getAttribute("passwordError") !=null) { %>
                                        <span class="err-msg input-error validate-msg">
                                            <%= request.getAttribute("passwordError") %>
                                        </span>
                                        <% } %>
                                </div>
                                <input type="submit" value="ログイン" class="login-btn">
                            </form>
                </div>
            </div>
        </body>

        </html>