<#import "customer/template_customer.ftl" as p>

<@p.pages>

    <h3 class="text-center mt-5 mb-4">Your bookings</h3>

    <div class="container mb-4">
        <#include "customer/buttonGroup.ftl">
    </div>

    <div class="container mb-5">
        <#if cart?? && cart?size gt 0>
            <div class="table-responsive">
                <table class="table table-bordered align-middle">
                    <thead class="table-light text-center">
                    <tr>
                        <th>Name</th>
                        <th>Image</th>
                        <th>Check-in date</th>
                        <th>Check-out date</th>
                        <th>Nights</th>
                        <th>Price per night</th>
                        <th>Total amount</th>
                        <th>Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list cart as item>
                        <tr class="text-center">
                            <td class="align-middle">${item.apartment.name}</td>
                            <td class="align-middle">
                                <img src="${item.apartment.linkImg}" alt="${item.apartment.name}" class="img-thumbnail"
                                     style="max-width: 90px; max-height: 70px; object-fit: cover;">
                            </td>
                            <td class="align-middle">${item.checkin}</td>
                            <td class="align-middle">${item.checkout}</td>
                            <td class="align-middle">
                                <form method="post" action="/updateNights" class="d-inline">
                                    <input type="hidden" name="id" value="${item.apartment.id}">
                                    <input type="hidden" name="checkin" value="${item.checkin}">
                                    <input type="number" name="nights" value="${nights[item_index]}" min="1"
                                           class="form-control text-center" style="width: 70px; display: inline-block;"
                                           onchange="this.form.submit()">
                                </form>
                            </td>
                            <td class="align-middle">${item.apartment.pricePerNight} UAH</td>

                            <td class="align-middle">
                                <#assign itemTotalPrice = item.apartment.pricePerNight * nights[item_index]>
                                <#assign itemPriceResult = cartPriceMap[item.apartment.id?string]!>

                                <#if itemPriceResult?? && itemPriceResult.hasDiscount()>
                                    <div>
                                        <s class="text-muted d-block">${itemTotalPrice} UAH</s>
                                        <span class="text-success fw-bold d-block">${itemPriceResult.finalPrice} UAH</span>
                                        <small class="text-success d-block">-${itemPriceResult.discountPercentage}%</small>
                                    </div>
                                <#else>
                                    <div class="fw-bold">${itemTotalPrice} UAH</div>
                                </#if>
                            </td>

                            <form method="post" action="/deleteItemFromCart">
                                <td class="align-middle">
                                    <input type="hidden" name="id" value="${item.apartment.id}">
                                    <button class="btn delete-btn">Remove</button>
                                </td>
                            </form>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        <#else>
            <div class="alert text-center border-0" style="background-color: rgba(108,117,125,0.18); color: black;" role="alert">
                No bookings found
            </div>
        </#if>
    </div>

    <hr>

    <div class="container mb-5 mt-5">
        <div class="row justify-content-center text-center">
            <div class="col-md-6">
                <h5 class="mb-2">Total price: <b>${totalPrice} UAH</b></h5>
                <h5>Total number of bookings: <b>${sumEl} booking(s)</b></h5>
            </div>
        </div>
    </div>

    <hr>

    <div class="container mb-5 mt-5">
        <div class="row row-cols-1 row-cols-md-2 g-4">
            <div class="col text-center">
                <h5>Confirm apartment booking</h5>
                <form method="get" action="/booking" class="d-inline">
                    <button type="submit" class="btn btn-outline-success mt-2">Proceed to booking</button>
                </form>
            </div>
            <div class="col text-center">
                <h5>Clear cart and return to homepage</h5>
                <form method="post" action="/deleteAllFromCart" class="d-inline">
                    <button type="submit" class="btn delete-btn mt-2">Clear cart</button>
                </form>
            </div>
        </div>
    </div>

</@p.pages>
