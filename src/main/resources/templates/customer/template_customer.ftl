<#macro pages showButtonGroup=false>

  <!DOCTYPE html>
  <html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Booking system</title>

  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">



  <!-- Fonts -->
  <link href="https://fonts.googleapis.com/css2?family=Scope+One&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Cormorant+Infant:ital,wght@0,300..700;1,300..700&display=swap"
        rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Brygada+1918:ital,wght@0,400..700;1,400..700&display=swap"
        rel="stylesheet">
  <link rel="stylesheet" href="/css/styles.css">
</head>
  <body class="mt-3 mx-3">

  <div class="container-fluid content-wrapper">
    <!-- navbar -->
    <#include "navbar.ftl">

    <!-- carousel -->
    <#if showCarousel?? && showCarousel>
      <#include "carousel.ftl">
    </#if>

    <#nested/>
  </div>

  <!-- footer -->
  <footer class="mt-3 pt-3 text-center">
    <#include "footer.ftl">
  </footer>

  </body>
  </html>

</#macro>
