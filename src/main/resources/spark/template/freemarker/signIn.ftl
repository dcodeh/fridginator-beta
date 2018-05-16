<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <title>${title} | Fridginator</title>
        <link rel="stylesheet" type="text/css" href="/styles/style.css">
        <link rel="icon" href="/images/favicon.png">
    </head>
    
    <body>
        <div class="page">
            <h1>Fridginator</h1>

            <form action="./signIn" method="POST">
                Enter your credentials to log in.
                <br/>
                <input name="username" value="Username" type="text"/>
                <br/>
                <input name="password" value="Password" type="password"/>
                <br/>
                <button type="submit">Log In</button>
            </form>

            <p>${version}</p>
        </div>
    </body>
</html>
