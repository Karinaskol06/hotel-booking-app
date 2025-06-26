<#import "manager/templ_manager.ftl" as p>
<@p.pages>
    <div class="container mt-4">
        <h2 class="mb-4 text-center">Адміністрування класів апартаментів</h2>

        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #027353;">
                Список класів апартаментів
            </div>
            <div class="card-body">
                <table class="table table-bordered table-hover text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Назва</th>
                        <th>Опис</th>
                        <th>Зручності</th>
                        <th>Зображення</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if apartment_classes??>
                        <#list apartment_classes as ac>
                            <tr>
                                <td>${ac.id}</td>
                                <td>${ac.apartmentClass}</td>
                                <td>${ac.classDescription}</td>
                                <td>${ac.facilities}</td>
                                <td><img src="${ac.linkImg}" alt="Image" width="100"></td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #65A693;">
                Додати новий клас апартаментів
            </div>
            <div class="card-body">
                <form method="post" action="/saveNewApartmentClass">
                    <div class="mb-3">
                        <label for="name" class="form-label">Назва</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Опис</label>
                        <input type="text" id="description" name="description" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="facilities" class="form-label">Зручності</label>
                        <input type="text" id="facilities" name="facilities" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">Посилання на зображення</label>
                        <input type="text" id="image" name="image" class="form-control" required>
                    </div>
                    <div class="text-center mt-3">
                        <button type="submit" class="btn btn-lg text-white" style="background-color: #027353;">Додати</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header" style="background-color: #D9D9D9;">
                Оновити або видалити класи апартаментів
            </div>
            <div class="card-body">
                <table class="table table-bordered table-striped align-middle text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Назва</th>
                        <th>Опис</th>
                        <th>Зручності</th>
                        <th>Зображення</th>
                        <th>Оновити</th>
                        <th>Видалити</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if apartment_classes??>
                        <#list apartment_classes as ac>
                            <tr>
                                <form method="post" action="/updateApartClass">
                                    <td>${ac.id}</td>
                                    <td><input type="text" name="name" value="${ac.apartmentClass}" class="form-control" required></td>
                                    <td><input type="text" name="description" value="${ac.classDescription}" class="form-control" required></td>
                                    <td><input type="text" name="facilities" value="${ac.facilities}" class="form-control" required></td>
                                    <td><input type="text" name="image" value="${ac.linkImg}" class="form-control" required></td>
                                    <td>
                                        <input type="hidden" name="idUpd" value="${ac.id}">
                                        <button type="submit" class="btn b-upd">Оновити</button>
                                    </td>
                                </form>
                                <form method="post" action="/deleteApartClassFromList">
                                    <td>
                                        <input type="hidden" name="idDel" value="${ac.id}">
                                        <button type="submit" class="btn b-del">Видалити</button>
                                    </td>
                                </form>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@p.pages>
