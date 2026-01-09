<#import "../main/templ_manager.ftl" as p>
<@p.pages>
    <div class="container mt-4">
        <h2 class="mb-4 text-center">Apartment Class Administration</h2>

        <div class="card mb-5 shadow-sm">
            <div class="card-header text-white" style="background-color: #027353;">
                List of Apartment Classes
            </div>
            <div class="card-body">
                <table class="table table-bordered table-hover text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Facilities</th>
                        <th>Image</th>
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
                Add New Apartment Class
            </div>
            <div class="card-body">
                <form method="post" action="/saveNewApartmentClass">
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <input type="text" id="name" name="name" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <input type="text" id="description" name="description" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="facilities" class="form-label">Facilities</label>
                        <input type="text" id="facilities" name="facilities" class="form-control" required>
                    </div>
                    <div class="mb-3">
                        <label for="image" class="form-label">Image URL</label>
                        <input type="text" id="image" name="image" class="form-control" required>
                    </div>
                    <div class="text-center mt-3">
                        <button type="submit" class="btn btn-lg text-white" style="background-color: #027353;">Add</button>
                    </div>
                </form>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header" style="background-color: #D9D9D9;">
                Update or Delete Apartment Classes
            </div>
            <div class="card-body">
                <table class="table table-bordered table-striped align-middle text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Facilities</th>
                        <th>Image</th>
                        <th>Update</th>
                        <th>Delete</th>
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
                                        <button type="submit" class="btn b-upd">Update</button>
                                    </td>
                                </form>
                                <form method="post" action="/deleteApartClassFromList">
                                    <td>
                                        <input type="hidden" name="idDel" value="${ac.id}">
                                        <button type="submit" class="btn b-del">Delete</button>
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
