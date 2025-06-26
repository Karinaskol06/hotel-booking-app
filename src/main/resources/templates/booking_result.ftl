<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <title>Результат бронювання</title>
</head>
<body>
<h1>Інформація про пакет бронювання</h1>

<p><strong>Назва пакету:</strong> ${packageName}</p>
<p><strong>Загальна вартість:</strong> ${totalPrice}</p>
<p><strong>Загальна місткість:</strong> ${totalCapacity}</p>
<p><strong>Доступність:</strong> <#if isAvailable>Так<#else>Ні</#if></p>

<h2>Деталі бронювання:</h2>
<ul>
    <#list details as detail>
        <li>${detail}</li>
    </#list>
</ul>

<p><a href="/composite-booking/form">Повернутися до форми бронювання</a></p>
</body>
</html>
