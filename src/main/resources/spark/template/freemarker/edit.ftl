<!DOCTYPE html>
<!-- Copyright (c) 2018 David Cody Burrows...See LICENSE file for details -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${title} | Fridginator</title>
        <link rel="stylesheet" type="text/css" href="/styles/style.css">
        <link rel="icon" href="/images/favicon.png">
    </head>
    
    <body>
    
        <#if message??>
            <div class="${messageType}">${message}</div>
        </#if>
        
        <div class="page">
            <h1>Edit Personal List</h1>
            <div class="edit">
                <form action="./editList" method="POST">
                    <textarea name="listText"><#if listContents??>${listContents}</#if></textarea>
                    <!-- Bottom Buttons -->
                    <button type="submit">Save</button>
                    <button type="submit">Exit</button>
                </form>
            </div>
            
        </div> <!-- end of page -->
    </body>
</html>
