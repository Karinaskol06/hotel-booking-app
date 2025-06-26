<#import "admin/templ_admin.ftl" as p>
<@p.pages>

    <style>
        .user-card {
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            border-left: 6px solid #08A696;
            padding: 16px;
            margin-bottom: 16px;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.05);
        }

        .user-header {
            font-size: 16px;
            font-weight: bold;
            color: #1c3d3d;
            margin-bottom: 8px;
        }

        .form-inline {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        select {
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #ccc;
            background-color: #f7fafa;
            color: #1c3d3d;
        }

        button {
            background-color: #0BBFAD;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out;
        }

        button:hover {
            background-color: #1f736a;
        }

        .messages {
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
        }

        .success {
            background-color: #dff5f3;
            color: #088C7F;
        }

        .error {
            background-color: #ffe0e0;
            color: rgba(191, 61, 61, 0.78);
        }

        i {
            margin-right: 6px;
            vertical-align: middle;
        }
    </style>

    <#if successMess??>
        <div class="messages success">${successMess}</div>
    </#if>
    <#if errorMess??>
        <div class="messages error">${errorMess}</div>
    </#if>

    <#list users as user>
        <div class="user-card">
            <div class="user-header">
                <i class="bi bi-person-circle"></i> ${user.username}
            </div>

            <#if user.rolesSet?has_content>
                <div class="mb-2">
                    <strong>Поточні ролі:</strong>
                    <#list user.rolesSet as role>
                        <span>${role.roleName}</span><#if role_has_next>, </#if>
                    </#list>
                </div>
            <#else>
                <div><em>Користувач не має жодної ролі</em></div>
            </#if>

            <form class="form-inline" action="/saveNewRoleForUser" method="post">
                <input type="hidden" name="id" value="${user.id}">
                <select name="roles">
                    <option value="1">ROLE_user</option>
                    <option value="2">ROLE_manager</option>
                    <option value="3">ROLE_admin</option>
                </select>
                <button type="submit"><i class="bi bi-plus-circle"></i> Додати роль</button>
            </form>
        </div>
    </#list>


</@p.pages>
