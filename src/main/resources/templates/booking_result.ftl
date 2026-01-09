<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Result</title>
</head>
<body>
<h1>Booking Package Information</h1>

<p><strong>Package name:</strong> ${packageName}</p>
<p><strong>Total price:</strong> ${totalPrice}</p>
<p><strong>Total capacity:</strong> ${totalCapacity}</p>
<p><strong>Availability:</strong> <#if isAvailable>Yes<#else>No</#if></p>

<h2>Booking details:</h2>
<ul>
    <#list details as detail>
        <li>${detail}</li>
    </#list>
</ul>

<p><a href="/composite-booking/form">Back to booking form</a></p>
</body>
</html>
