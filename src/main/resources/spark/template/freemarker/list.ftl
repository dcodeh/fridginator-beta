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

                    <!-- repeated section -->
                    <label class="container">1 gal Milk
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>
                    <label class="container">12 Eggs
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>
                    <label class="container">1423 Bananas
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>

                </div>

                <h2>Shared Items</h2>
                <div class="list">

                    <!-- Repeated section -->
                    <label class="container">Toilet Paper [24 rolls]
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>
                    <label class="container">Paper Towels [8 rolls]
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>
                    <label class="container">FRUITY DRANK [2 gal]
                        <input type="checkbox"> <!-- checked="checked" -->
                        <span class="checkmark"></span>
                    </label>

                </div>

                <!-- Bottom Buttons -->
                <button type="submit" name="save">Save</button>
                <button type="submit" name="edit">Edit</button>
                <button type="submit" name="exit">Exit</button>
            </form>
        </div> <!-- end of page -->
    </body>
</html>
