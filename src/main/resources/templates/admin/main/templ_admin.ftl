<#macro pages>
    <html lang="uk">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Booking system admin</title>
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

        <link href="https://fonts.googleapis.com/css2?family=Scope+One&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Cormorant+Infant:ital,wght@0,300..700;1,300..700&display=swap"
              rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Brygada+1918:ital,wght@0,400..700;1,400..700&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="/css/styles2.css">
    </head>
    <body class="mt-3 mx-3">
    <div class="container-fluid content-wrapper">

        <div class="row mb-3">
            <#include "navbar.ftl"/>
        </div>

        <div class="row pt-3">
            <div class="col-3">
                <#include "left-menu.ftl"/>
            </div>

            <div class="col-9">
                <#nested/>
            </div>
        </div>
    </div>

    <footer class="mt-3 pt-3 text-center">
        <#include "footer.ftl">
    </footer>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>