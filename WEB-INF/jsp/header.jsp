<%@page contentType="text/html;charset=utf-8" %>
            <!DOCTYPE html>
            <html lang="ja">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="src/css/style.css" class="css">
                <title>
                    <%= request.getAttribute("pageTitle") %>
                </title>
            </head>

            <body>
                <header>
                    <div class="container inner header-inner">
                        <h1>
                            Send-mail-app
                        </h1>
                        <div class="logout">
                            <form action=""><input type="button" value="ログアウト"></form>
                        </div>

                    </div>
                </header>