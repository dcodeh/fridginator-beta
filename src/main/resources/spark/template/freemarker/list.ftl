<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>${title} | Fridginator</title>
        <link rel="stylesheet" type="text/css" href="/styles/style.css">
        <link rel="icon" href="/images/favicon.png">
    </head>
    
    <body>
    
        <#if message??>
            <div class="message">${message}</div>
        </#if>
    
        <div class="page">
            <h1>My List</h1>

            <form action="./signOut" method="POST">
                <button type="submit">Log Out</button>
            </form>
        </div>
    </body>
</html>
