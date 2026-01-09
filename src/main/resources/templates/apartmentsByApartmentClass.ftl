<#import "customer/template_customer.ftl" as p>

<@p.pages>

    <h2 class="mt-3 mb-4 text-center">Apartments</h2>

    <form method="get" action="/apartment_class/${id}"
          class="d-flex flex-wrap justify-content-center gap-3 p-4 border rounded shadow-sm"
          style="max-width: 1000px; margin: 0 auto;">

        <#if errorMessage??>
            <div class="alert alert-danger w-100 text-center">${errorMessage}</div>
        </#if>
        <#if warningMessage??>
            <div class="alert alert-warning w-100 text-center">${warningMessage}</div>
        </#if>
        <#if infoMessage??>
            <div class="alert alert-info w-100 text-center">${infoMessage}</div>
        </#if>

        <div class="form-group d-flex align-items-center gap-2">
            <label for="checkin" class="form-label mb-0">Check-in date:</label>
            <input type="date" id="checkin" name="checkin" value="${(checkin)!''}"
                   required class="form-control" style="min-width: 200px;">
        </div>

        <div class="form-group d-flex align-items-center gap-2">
            <label for="checkout" class="form-label mb-0">Check-out date:</label>
            <input type="date" id="checkout" name="checkout" value="${(checkout)!''}"
                   required class="form-control" style="min-width: 200px;">
        </div>

        <div class="form-group align-self-center">
            <button type="submit" class="btn btn-primary"
                    style="background-color: #5A735A; color: #fff; border-color: rgba(24,38,37,0);">
                Check availability
            </button>
        </div>
    </form>

    <div class="container mt-3 mb-3">
        <#include "customer/buttonGroup.ftl">
    </div>

    <!-- cards -->
    <div class="container pt-4">
        <#if apartments??>
            <div class="row row-cols-2 g-7">
                <#list apartments as apartment>
                    <div class="col">
                        <div class="card h-100" style="max-width: 680px; height: 300px;">
                            <div class="row g-0 h-100">

                                <div class="col-md-5">
                                    <a href="#" data-bs-toggle="modal" data-bs-target="#modal-${apartment.id}">
                                        <img src="${apartment.linkImg}" class="img-fluid rounded-start"
                                             alt="Apartment image"
                                             style="width: 100%; height: 100%; object-fit: cover;">
                                    </a>
                                </div>

                                <div class="col-md-6 d-flex flex-column justify-content-between">
                                    <div class="card-body d-flex flex-column justify-content-between p-6"
                                         style="height: 100%;">
                                        <div class="d-flex flex-column gap-2"
                                             style="flex-grow: 1; overflow: hidden;">
                                            <h4 class="card-title mb-4">${apartment.name}</h4>

                                            <p class="card-text mb-1">
                                                <b>Class:</b> ${apartment.apartmentClass.apartmentClass}
                                            </p>

                                            <#assign priceResult = priceMap[apartment.id?string]!>

                                            <#if priceResult?? && priceResult.hasDiscount()>
                                                <p class="card-text mb-1">
                                                    <b>Price:</b>
                                                    <span class="text-decoration-line-through text-muted me-2">
                                                        ${priceResult.originalPrice} UAH
                                                    </span>
                                                    <span class="text fw-bold me-2">
                                                        ${priceResult.finalPrice} UAH
                                                    </span>
                                                    <span class="badge bg-success">${priceResult.discountPercentage}% off</span>
                                                </p>
                                            <#elseif priceResult??>
                                                <p class="card-text mb-1" style="text-decoration: underline;">
                                                    <b>Booking price:</b> ${priceResult.finalPrice} UAH
                                                </p>
                                            <#else>
                                                <#assign nights = ((checkout?date("yyyy-MM-dd")?long - checkin?date("yyyy-MM-dd")?long) / (24 * 60 * 60 * 1000))>
                                                <#assign totalPrice = apartment.pricePerNight * nights>
                                                <p class="card-text mb-1">
                                                    <b>Booking price:</b> ${totalPrice?round} UAH
                                                </p>
                                            </#if>

                                            <p class="card-text mb-1">
                                                <b>Price per night:</b> ${apartment.pricePerNight} UAH
                                            </p>

                                            <p class="card-text mb-1">
                                                <b>Capacity:</b> ${apartment.capacity} persons
                                            </p>

                                            <p class="card-text mb-1">
                                                <b>Area:</b> ${apartment.area} m²
                                            </p>

                                            <p class="card-text mb-1">
                                                <b>Number of rooms:</b> ${apartment.numOfRooms}
                                            </p>
                                        </div>

                                        <form method="post" action="/addToCart" class="mt-auto">
                                            <input type="hidden" name="id" value="${apartment.id}">
                                            <input type="hidden" name="checkin" value="${checkin}">
                                            <input type="hidden" name="checkout" value="${checkout}">
                                            <input type="submit" value="Book now"
                                                   class="btn btn-outline-secondary w-100 mt-2">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="modal-${apartment.id}" tabindex="-1"
                         aria-labelledby="modalLabel-${apartment.id}" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <h1 class="modal-title fs-5"
                                        id="modalLabel-${apartment.id}">
                                        ${apartment.name}
                                    </h1>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>

                                <div class="modal-body">
                                    <div class="row">

                                        <!-- Photo section -->
                                        <div class="col-md-6">
                                            <#assign photos = apartment.apartmentImages?filter(img -> img.urlImg?has_content)>
                                            <#if photos?size == 0>
                                                <img src="${apartment.linkImg}" class="d-block w-100"
                                                     style="height: 500px; object-fit: cover;"
                                                     alt="Apartment photo">
                                            <#elseif photos?size == 1>
                                                <img src="${photos[0].urlImg}" class="d-block w-100"
                                                     style="height: 500px; object-fit: cover;"
                                                     alt="Apartment photo">
                                            <#else>
                                                <div id="carousel-${apartment.id}" class="carousel slide"
                                                     data-bs-ride="carousel">
                                                    <div class="carousel-inner">
                                                        <#list photos as image>
                                                            <div class="carousel-item <#if image_index == 0>active</#if>">
                                                                <img src="${image.urlImg}" class="d-block w-100"
                                                                     style="height: 500px; object-fit: cover;"
                                                                     alt="Apartment photo">
                                                            </div>
                                                        </#list>
                                                    </div>
                                                    <button class="carousel-control-prev" type="button"
                                                            data-bs-target="#carousel-${apartment.id}"
                                                            data-bs-slide="prev">
                                                        <span class="carousel-control-prev-icon"></span>
                                                    </button>
                                                    <button class="carousel-control-next" type="button"
                                                            data-bs-target="#carousel-${apartment.id}"
                                                            data-bs-slide="next">
                                                        <span class="carousel-control-next-icon"></span>
                                                    </button>
                                                </div>
                                            </#if>
                                        </div>

                                        <div class="col-md-6 d-flex flex-column justify-content-center">
                                            <div class="mb-3">
                                                <h3 class="mb-4">${apartment.name}</h3>
                                                <p><b>Apartment class:</b> ${apartment.apartmentClass.apartmentClass}</p>
                                                <p><b>Capacity:</b> ${apartment.capacity} persons</p>
                                                <p><b>Price per night:</b> ${apartment.pricePerNight} UAH</p>

                                                <#assign priceResult = priceMap[apartment.id?string]!>
                                                <#if priceResult?? && priceResult.hasDiscount()>
                                                    <p class="fw-bold mt-3">
                                                        <b>Price:</b>
                                                        <s class="text-muted me-2">${priceResult.originalPrice} UAH</s>
                                                        <span class="text me-2">${priceResult.finalPrice} UAH</span>
                                                        <span class="badge bg-success">${priceResult.discountPercentage}% off</span>
                                                    </p>
                                                <#elseif priceResult??>
                                                    <p class="fw-bold mt-3">
                                                        Price: ${priceResult.finalPrice} UAH
                                                    </p>
                                                </#if>
                                            </div>

                                            <div>
                                                <p><b>Class description:</b></p>
                                                <p>${apartment.apartmentClass.classDescription}</p>
                                                <p><b>Facilities:</b></p>
                                                <p>${apartment.apartmentClass.facilities}</p>
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
            </div>
        </#if>
    </div>

</@p.pages>
