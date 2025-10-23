<#import "customer/template_customer.ftl" as p>

<@p.pages>
    <style>
        .table thead {
            background-color: #0F3A2B;
            color: white;
        }

        .table td, .table th {
            vertical-align: middle;
            text-align: center;
        }

        .card-title {
            color: #262118;
        }

        .card-body p {
            margin-bottom: 0.4rem;
        }

        .card {
            border: none;
            box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease;
        }

        .card:hover {
            transform: scale(1.02);
        }

        .payment-section {
            background-color: white;
            padding: 2rem;
            border-radius: 0.5rem;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.12);
            border-left: 5px solid #E78200;
        }

        .price-section {
            background-color: #f8f9fa;
            padding: 10px;
            border-radius: 5px;
            margin: 10px 0;
        }

        .text-decoration-line-through {
            text-decoration: line-through !important;
        }
    </style>

    <div class="container py-4">
        <h3 class="text-center mb-3">Інформація про користувача</h3>
        <table class="table table-hover shadow-sm rounded overflow-hidden mb-5">
            <thead>
            <tr>
                <th>Імʼя</th>
                <th>Прізвище</th>
                <th>Телефон</th>
                <th>Email</th>
            </tr>
            </thead>
            <tr>
                <td>${client.firstName}</td>
                <td>${client.lastName}</td>
                <td>${client.phone}</td>
                <td>${client.email}</td>
            </tr>
        </table>

        <hr>
        <h4 class="text-center mb-4 mt-4">Інформація про бронювання</h4>

        <#if cart?? && cart?size gt 0>
            <div class="row justify-content-center g-4 mb-5">
                <#list cart as item>
                    <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">
                        <div class="card h-100">
                            <img src="${item.apartment.linkImg}" class="card-img-top img-fluid"
                                 style="height: 200px; object-fit: cover;">
                            <div class="card-body d-flex flex-column justify-content-between">
                                <div>
                                    <h5 class="card-title text-center">${item.apartment.name}</h5>
                                    <p class="text-center"><strong>Кількість ночей:</strong> ${nights[item_index]}</p>
                                    <p class="text-center"><strong>Ціна за ніч:</strong> ${item.apartment.pricePerNight} грн</p>

                                    <div class="price-section text-center">
                                        <#assign itemTotalPrice = item.apartment.pricePerNight * nights[item_index]>
                                        <#assign itemPriceResult = cartPriceMap[item.apartment.id?string]!>

                                        <#if itemPriceResult?? && itemPriceResult.hasDiscount()>
                                            <p class="mb-1">
                                                <strong>Загальна вартість:</strong><br>
                                                <span class="text-decoration-line-through text-muted">
                                                    ${itemTotalPrice} грн
                                                </span><br>
                                                <span class="text-success fw-bold fs-5">
                                                    ${itemPriceResult.finalPrice} грн
                                                </span><br>
                                                <span class="badge bg-success mt-1">
                                                    Знижка ${itemPriceResult.discountPercentage}%
                                                </span>
                                            </p>
                                        <#else>
                                            <p class="mb-0">
                                                <strong>Загальна вартість:</strong><br>
                                                <span class="fw-bold fs-5">${itemTotalPrice} грн</span>
                                            </p>
                                        </#if>
                                    </div>
                                </div>
                                <button class="btn btn-outline-success mt-3" data-bs-toggle="modal" data-bs-target="#modal-${item.apartment.id}">
                                    Детальніше
                                </button>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

            <#list cart as item>
                <div class="modal fade" id="modal-${item.apartment.id}" tabindex="-1" aria-labelledby="modalLabel-${item.apartment.id}" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="modalLabel-${item.apartment.id}">${item.apartment.name}</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <#assign photos = item.apartment.apartmentImages?filter(img -> img.urlImg?has_content)>
                                        <#if photos?size == 0>
                                            <img src="${item.apartment.linkImg}" class="d-block w-100"
                                                 style="height: 500px; object-fit: cover;">
                                        <#elseif photos?size == 1>
                                            <img src="${photos[0].urlImg}" class="d-block w-100"
                                                 style="height: 500px; object-fit: cover;">
                                        <#else>
                                            <div id="carousel-${item.apartment.id}" class="carousel slide" data-bs-ride="carousel">
                                                <div class="carousel-inner">
                                                    <#list photos as image>
                                                        <div class="carousel-item <#if image_index == 0>active</#if>">
                                                            <img src="${image.urlImg}" class="d-block w-100"
                                                                 style="height: 500px; object-fit: cover;">
                                                        </div>
                                                    </#list>
                                                </div>
                                                <button class="carousel-control-prev" type="button" data-bs-target="#carousel-${item.apartment.id}" data-bs-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                </button>
                                                <button class="carousel-control-next" type="button" data-bs-target="#carousel-${item.apartment.id}" data-bs-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                </button>
                                            </div>
                                        </#if>
                                    </div>

                                    <div class="col-md-6 d-flex flex-column justify-content-center">
                                        <div class="mb-3">
                                            <h3 class="mb-4">${item.apartment.name}</h3>
                                            <p><b>Клас апартаментів:</b> ${item.apartment.apartmentClass.apartmentClass}</p>
                                            <p><b>Місткість:</b> ${item.apartment.capacity} ос.</p>
                                            <p><b>Ціна за ніч:</b> ${item.apartment.pricePerNight} грн.</p>
                                            <#assign itemTotalPrice = item.apartment.pricePerNight * nights[item_index]>
                                            <#assign itemPriceResult = cartPriceMap[item.apartment.id?string]!>
                                            <div class="mt-3 p-3 bg-light rounded">
                                                <#if itemPriceResult?? && itemPriceResult.hasDiscount()>
                                                    <p class="mb-1"><strong>Загальна вартість:</strong></p>
                                                    <p class="mb-1">
                                                        <s class="text-muted">${itemTotalPrice} грн</s>
                                                        <span class="text-success fw-bold ms-2">${itemPriceResult.finalPrice} грн</span>
                                                    </p>
                                                    <p class="mb-0 text-success">
                                                        <small>Знижка: ${itemPriceResult.discountPercentage}%</small>
                                                    </p>
                                                <#else>
                                                    <p class="mb-0"><strong>Загальна вартість:</strong> ${itemTotalPrice} грн</p>
                                                </#if>
                                            </div>
                                        </div>
                                        <div>
                                            <p><b>Опис класу:</b></p>
                                            <p>${item.apartment.apartmentClass.classDescription}</p>
                                            <p><b>Зручності:</b></p>
                                            <p>${item.apartment.apartmentClass.facilities}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer d-flex justify-content-center">
                                <button type="button" class="btn btn-dark" data-bs-dismiss="modal">Закрити</button>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>

            <div class="payment-section mx-auto mt-5" style="max-width: 400px;">
                <form action="/booking" method="post">
                    <h4 class="text-center mb-4">Оплата</h4>
                    <div class="mb-3">
                        <label for="payment" class="form-label">Оберіть спосіб оплати:</label>
                        <select class="form-select" name="payment" id="payment" aria-label="Оплата">
                            <option value="1">Google Pay</option>
                            <option value="2">Банківська карта</option>
                            <option value="3">Готівкою при заселенні</option>
                        </select>
                    </div>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="submit" class="btn btn-outline-success">Заплатити</button>
                    </div>
                </form>
            </div>
        <#else>
            <div class="alert alert-warning text-center mt-4" role="alert">
                Ваш кошик порожній. Будь ласка, додайте апартаменти, перш ніж оформити бронювання.
            </div>
        </#if>
    </div>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</@p.pages>