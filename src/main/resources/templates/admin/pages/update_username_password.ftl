<#import "../main/templ_admin.ftl" as p>
<@p.pages>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #ffffff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            overflow: hidden;
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
    </style>

    <h2 class="mb-5 text-center">User Administration Page</h2>

    <table>
        <thead>
        <tr>
            <th>ІD</th>
            <th>Name</th>
            <th>Lastname</th>
            <th>Email</th>
            <th>Phone number</th>
            <th>Username</th>
            <th>Password</th>
            <th>Update</th>
        </tr>
        </thead>
        <tbody>
        <#if users??>
            <#list users as user>
                <#if clients??>
                    <#list clients as client>
                        <#if client.id == user.id>
                            <form action="/updateUsernameAndPassword" method="post">
                            <tr>

                                    <td>${client.id}</td>
                                    <td>${client.firstName}</td>
                                    <td>${client.lastName}</td>
                                    <td>${client.email}</td>
                                    <td>${client.phone}</td>

                                    <input type="hidden" name="id" value="${user.id}">
                                    <td>
                                        <input type="text" name="username" value="${user.username}">
                                    </td>
                                    <td>
                                        <input type="text" name="password" value="${user.password}">
                                    </td>
                                    <td>
                                        <button type="submit">Update</button>
                                    </td>

                            </tr>
                            </form>
                        </#if>
                    </#list>
                </#if>
            </#list>
        </#if>
        </tbody>
    </table>
</@p.pages>
