<#import "manager/templ_manager.ftl" as p>
<@p.pages>
    <div class="container mt-4">
        <h2 class="mb-4 text-center">Адміністрування апартаментами</h2>

        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #027353;">
                Список апартаментів
            </div>
            <div class="card-body">
                <table class="table table-bordered table-hover text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Назва</th>
                        <th>Клас</th>
                        <th>Номер</th>
                        <th>Місткість</th>
                        <th>Ціна за добу</th>
                        <th>Зображення</th>
                        <th>Площа</th>
                        <th>К-сть кімнат</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if apartments??>
                        <#list apartments as a>
                            <tr>
                                <td>${a.id}</td>
                                <td>${a.name}</td>
                                <td>${a.apartmentClass.apartmentClass}</td>
                                <td>${a.apartmentNumber}</td>
                                <td>${a.capacity}</td>
                                <td>${a.pricePerNight} грн.</td>
                                <td><img src="${a.linkImg}" alt="Image" width="100"></td>
                                <td>${a.area} кв.м.</td>
                                <td>${a.numOfRooms}</td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #65A693;">
                Додати новий апартамент
            </div>
            <div class="card-body">
                <form method="post" action="/saveNewApartment">
                    <div class="mb-3">
                        <label for="name" class="form-label">Назва</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="apartClass" class="form-label">Клас апартаменту</label>

                        <select name="apartClass" id="apartClass" class="form-select" required>
                            <option value="" disabled selected>Оберіть клас апартаменту</option>
                            <#list apartment_classes as ac>
                                <option value="${ac.id}">${ac.apartmentClass}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="apartNumber" class="form-label">Номер</label>
                        <input type="number" id="apartNumber" name="apartNumber"
                               class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="capacity" class="form-label">Місткість</label>
                        <input type="number" id="capacity" name="capacity"
                               class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="pricePerNight" class="form-label">Ціна за добу</label>
                        <input type="number" min="0" id="pricePerNight" name="pricePerNight"
                               class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">Зображення</label>
                        <input type="text" id="image" name="image" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="area" class="form-label">Площа</label>
                        <input type="number" id="area" name="area" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="numOfRooms" class="form-label">Кількість кімнат</label>
                        <input type="number" id="numOfRooms" name="numOfRooms"
                               class="form-control" required>
                    </div>
                    <div class="text-center mt-3">
                        <button type="submit" class="btn btn-lg text-white"
                                style="background-color: #027353;">Додати</button>
                    </div>
                </form>
            </div>
        </div>


        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #027353;">
                <h5 class="mb-0">Редагування апартаментів</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-bordered table-hover text-center align-middle">
                        <thead class="table-light">
                        <tr>
                            <th>ID</th>
                            <th>Назва</th>
                            <th>Клас</th>
                            <th>Номер</th>
                            <th>Місткість</th>
                            <th>Ціна за добу</th>
                            <th>Зображення</th>
                            <th>Площа</th>
                            <th>К-сть кімнат</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if apartments??>
                            <#list apartments as a>
                                <tr>
                                    <form method="post" action="/updateApartment" class="d-flex justify-content-center">
                                        <input type="hidden" name="idUpd" value="${a.id}">
                                        <td>${a.id}</td>
                                        <td>
                                            <input type="text" name="name" class="form-control form-control-sm rounded"
                                                   value="${a.name}" placeholder="Назва" required>
                                        </td>
                                        <td>
                                            <select name="apartClass" class="form-select form-select-sm rounded" required>
                                                <#if apartment_classes??>
                                                    <#list apartment_classes as ac>
                                                        <#if ac.id == a.apartmentClass.id>
                                                            <option value="${ac.id}" selected>${ac.apartmentClass}</option>
                                                        <#else>
                                                            <option value="${ac.id}">${ac.apartmentClass}</option>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="number" name="apartNumber" class="form-control form-control-sm rounded"
                                                   value="${a.apartmentNumber}" placeholder="№" required>
                                        </td>
                                        <td>
                                            <input type="number" name="capacity" class="form-control form-control-sm rounded"
                                                   value="${a.capacity}" placeholder="Осіб" required>
                                        </td>
                                        <td>
                                            <input type="number" name="pricePerNight" class="form-control form-control-sm rounded"
                                                   value="${a.pricePerNight?string("0.##")}" step="0.01" placeholder="₴/доба" required>
                                        </td>
                                        <td>
                                            <input type="text" name="image" class="form-control form-control-sm rounded"
                                                   value="${a.linkImg}" placeholder="URL зображення">
                                        </td>
                                        <td>
                                            <input type="number" name="area" class="form-control form-control-sm rounded"
                                                   placeholder="м²" value="${a.area?replace(',', '.')}" step="0.1" required>
                                        </td>
                                        <td>
                                            <input type="number" name="numOfRooms" class="form-control form-control-sm rounded"
                                                   value="${a.numOfRooms}" placeholder="Кімнат" required>
                                        </td>
                                        <td>
                                            <button type="submit" class="btn btn-sm rounded b-upd" title="Редагувати">
                                                <i class="bi bi-pencil-square fs-5"></i>
                                            </button>
                                        </td>
                                    </form>
                                    <form method="post" action="/deleteApartment"
                                          onsubmit="return confirm('Видалити апартамент?');">
                                        <td>
                                            <input type="hidden" name="idDel" value="${a.id}">
                                            <button type="submit" class="btn btn-sm rounded b-del" title="Видалити">
                                                <i class="bi bi-trash3 fs-5"></i>
                                            </button>
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






    </div>

</@p.pages>