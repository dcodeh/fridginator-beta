<!DOCTYPE html>

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
            <h1>My List</h1>

            <form action="./signOut" method="POST">
                <h2>Personal Items</h2>
                <div class="list">

                    <!-- repeated section for personal items -->
                    <#list personalItems as item>
                        <label class="container">${item.getLine()}
                            <#if ${item.getIsChecked()}>
                                <input type="checkbox checked="checked">
                            <#else>
                                <input type="checkbox"> 
                            </#if>
                            <span class="checkmark"></span>
                        </label>
                    </#list>
                    
                </div>

                <h2>Shared Items</h2>
                <div class="list">

                    <!-- repeated section for shared items-->
                    <#list sharedItems as item>
                        <label class="container">${item.getLine()}
                            <#if ${item.getIsChecked()}>
                                <input type="checkbox checked="checked">
                            <#else>
                                <input type="checkbox"> 
                            </#if>
                            <span class="checkmark"></span>
                        </label>
                    </#list>

                </div>

                <!-- Bottom Buttons -->
                <button type="submit" name="save">Save</button>
                <button type="submit" name="edit">Edit</button>
                <button type="submit" name="exit">Exit</button>
            </form>
        </div> <!-- end of page -->
    </body>
</html>
