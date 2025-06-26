<#import "admin/templ_admin.ftl" as p>
<@p.pages>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
            margin-top: 2rem;
        }

        thead {
            background-color: #62B5A9;
            color: white;
        }

        th, td {
            padding: 1rem;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        tr:hover {
            background-color: rgba(98, 181, 169, 0.25);
            color: #153B3C;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 0.5rem;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #EDE3DD;
        }

        button {
            background-color: #088c7f;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #153B3C;
        }

        h2 {
            text-align: center;
            color: #153B3C;
        }
    </style>

    <h2>Сторінка адміністрування користувачів</h2>
    <#if successMess??>
        <div style="padding: 1rem; margin: 1rem 0; background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; border-radius: 5px;">
            ${successMess}
        </div>
    </#if>

    <#if errorMess??>
        <div style="padding: 1rem; margin: 1rem 0; background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; border-radius: 5px;">
            ${errorMess}
        </div>
    </#if>

    <table>
        <thead>
        <tr>
            <th>Ім'я</th>
            <th>Прізвище</th>
            <th>Електронна пошта</th>
            <th>Номер телефону</th>
            <th>Username</th>
            <th>Password</th>
        </tr>
        </thead>
        <tbody>
        <#if users?? && clients??>
            <#list users as user>
                <#list clients as client>
                    <#if client.id == user.id>
                        <tr>
                            <td>${client.firstName}</td>
                            <td>${client.lastName}</td>
                            <td>${client.email}</td>
                            <td>${client.phone}</td>
                            <td>${user.username}</td>
                            <td>${user.password}</td>
                        </tr>
                    </#if>
                </#list>
            </#list>
        </#if>
        </tbody>
    </table>
</@p.pages>
