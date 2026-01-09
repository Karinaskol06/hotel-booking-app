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
        <h3 class="text-center mb-3">User Information</h3>
        <table class="table table-hover shadow-sm rounded overflow-hidden mb-5">
            <thead>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Phone</th>
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
        <h4 class="text-center mb-4 mt-4">Booking Information</h4>

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
                                    <p class="text-center"><strong>Number of nights:</strong> ${nights[item_index]}</p>
                                    <p class="text-center"><strong>Price per night:</strong> ${item.apartment.pricePerNight} UAH</p>

                                    <div class="price-section text-center">
                                        <#assign itemTotalPrice = item.apartment.pricePerNight * nights[item_index]>
                                        <#assign itemPriceResult = cartPriceMap[item.apartment.id?string]!>

                                        <#if itemPriceResult?? && itemPriceResult.hasDiscount()>
                                            <p class="mb-1">
                                                <strong>Total price:</strong><br>
                                                <span class="text-decoration-line-through text-muted">
                                                    ${itemTotalPrice} UAH
                                                </span><br>
                                                <span class="text-success fw-bold fs-5">
                                                    ${itemPriceResult.finalPrice} UAH
                                                </span><br>
                                                <span class="badge bg-success mt-1">
                                                    Discount ${itemPriceResult.discountPercentage}%
                                                </span>
                                            </p>
                                        <#else>
                                            <p class="mb-0">
                                                <strong>Total price:</strong><br>
                                                <span class="fw-bold fs-5">${itemTotalPrice} UAH</span>
                                            </p>
                                        </#if>
                                    </div>
                                </div>
                                <button class="btn btn-outline-success mt-3"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modal-${item.apartment.id}">
                                    View details
                                </button>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

            <#list cart as item>
                <div class="modal fade" id="modal-${item.apartment.id}" tabindex="-1"
                     aria-labelledby="modalLabel-${item.apartment.id}" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5"
                                    id="modalLabel-${item.apartment.id}">
                                    ${item.apartment.name}
                                </h1>
                                <button type="button" class="btn-close"
                                        data-bs-dismiss="modal" aria-label="Close"></button>
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
                                            <div id="carousel-${item.apartment.id}" class="carousel slide"
                                                 data-bs-ride="carousel">
                                                <div class="carousel-inner">
                                                    <#list photos as image>
                                                        <div class="carousel-item <#if image_index == 0>active</#if>">
                                                            <img src="${image.urlImg}" class="d-block w-100"
                                                                 style="height: 500px; object-fit: cover;">
                                                        </div>
                                                    </#list>
                                                </div>
                                                <button class="carousel-control-prev" type="button"
                                                        data-bs-target="#carousel-${item.apartment.id}"
                                                        data-bs-slide="prev">
                                                    <span class="carousel-control-prev-icon"></span>
                                                </button>
                                                <button class="carousel-control-next" type="button"
                                                        data-bs-target="#carousel-${item.apartment.id}"
                                                        data-bs-slide="next">
                                                    <span class="carousel-control-next-icon"></span>
                                                </button>
                                            </div>
                                        </#if>
                                    </div>

                                    <div class="col-md-6 d-flex flex-column justify-content-center">
                                        <div class="mb-3">
                                            <h3 class="mb-4">${item.apartment.name}</h3>
                                            <p><b>Apartment class:</b> ${item.apartment.apartmentClass.apartmentClass}</p>
                                            <p><b>Capacity:</b> ${item.apartment.capacity} persons</p>
                                            <p><b>Price per night:</b> ${item.apartment.pricePerNight} UAH</p>

                                            <#assign itemTotalPrice = item.apartment.pricePerNight * nights[item_index]>
                                            <#assign itemPriceResult = cartPriceMap[item.apartment.id?string]!>
                                            <div class="mt-3 p-3 bg-light rounded">
                                                <#if itemPriceResult?? && itemPriceResult.hasDiscount()>
                                                    <p class="mb-1"><strong>Total price:</strong></p>
                                                    <p class="mb-1">
                                                        <s class="text-muted">${itemTotalPrice} UAH</s>
                                                        <span class="text-success fw-bold ms-2">
                                                            ${itemPriceResult.finalPrice} UAH
                                                        </span>
                                                    </p>
                                                    <p class="mb-0 text-success">
                                                        <small>Discount: ${itemPriceResult.discountPercentage}%</small>
                                                    </p>
                                                <#else>
                                                    <p class="mb-0">
                                                        <strong>Total price:</strong> ${itemTotalPrice} UAH
                                                    </p>
                                                </#if>
                                            </div>
                                        </div>
                                        <div>
                                            <p><b>Class description:</b></p>
                                            <p>${item.apartment.apartmentClass.classDescription}</p>
                                            <p><b>Facilities:</b></p>
                                            <p>${item.apartment.apartmentClass.facilities}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer d-flex justify-content-center">
                                <button type="button" class="btn btn-dark"
                                        data-bs-dismiss="modal">
                                    Close
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>

            <div class="payment-section mx-auto mt-5" style="max-width: 400px;">
                <form action="/booking" method="post">
                    <h4 class="text-center mb-4">Payment</h4>
                    <div class="mb-3">
                        <label for="payment" class="form-label">Choose payment method:</label>
                        <select class="form-select" name="payment" id="payment" aria-label="Payment">
                            <option value="1">Google Pay</option>
                            <option value="2">Bank card</option>
                            <option value="3">Cash on arrival</option>
                        </select>
                    </div>
                    <div class="d-flex justify-content-center mt-3">
                        <button type="submit" class="btn btn-outline-success">
                            Pay
                        </button>
                    </div>
                </form>
            </div>
        <#else>
            <div class="alert alert-warning text-center mt-4" role="alert">
                Your cart is empty. Please add apartments before completing the booking.
            </div>
        </#if>
    </div>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</@p.pages>
