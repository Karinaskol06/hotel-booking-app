<#import "customer/template_customer.ftl" as p>
<@p.pages>
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h3 class="mb-0">Thank you for your booking!</h3>
                    </div>
                    <div class="card-body">
                        <#if invoice??>
                            <h4>Invoice</h4>
                            <div class="invoice-details p-3 border rounded mb-4">
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <strong>Invoice number:</strong> ${invoice.id}
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Date:</strong> ${invoice.date?string("dd.MM.yyyy")}
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <strong>Client:</strong>
                                        ${booking.client.firstName}
                                        ${booking.client.lastName}
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Payment method:</strong>
                                        ${booking.payment.paymentMethod}
                                    </div>
                                </div>

                                <div class="booked-items mt-4">
                                    <h5 class="text-center mb-3">Booked apartments:</h5>
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Number</th>
                                            <th>Class</th>
                                            <th>Check-in date</th>
                                            <th>Check-out date</th>
                                            <th>Price per night</th>
                                            <th>Number of nights</th>
                                            <th>Total price</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list apartmentsWithNights as item>
                                            <#assign ahb = item.ahb>
                                            <#assign nights = item.nights>
                                            <#assign totalPrice = ahb.apartment.pricePerNight * nights>
                                            <#assign discountedPrice = ahb.totalPrice!totalPrice>
                                            <#assign hasDiscount = (discountedPrice != totalPrice)>

                                            <tr>
                                                <td>${ahb.apartment.name}</td>
                                                <td>${ahb.apartment.apartmentNumber}</td>
                                                <td>${ahb.apartment.apartmentClass.apartmentClass}</td>
                                                <td>${ahb.checkIn}</td>
                                                <td>${ahb.checkOut}</td>
                                                <td>${ahb.apartment.pricePerNight} грн</td>
                                                <td>${nights}</td>
                                                <td>
                                                    <#if hasDiscount>
                                                        <div>
                                                            <s class="text-muted small">${totalPrice} грн</s>
                                                            <div class="text-success fw-bold">${discountedPrice} грн</div>
                                                            <small class="text-success">
                                                                Discount: ${((totalPrice - discountedPrice) / totalPrice * 100)?round}%
                                                            </small>
                                                        </div>
                                                    <#else>
                                                        ${totalPrice} грн
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                        <tfoot>
                                        <tr class="table-active">
                                            <td colspan="6"></td>
                                            <td class="text-end"><strong>Total:</strong></td>
                                            <td class="text-center">
                                                <strong>${invoice.totalAmount} грн</strong>
                                                <#if originalTotalAmount?? && originalTotalAmount != invoice.totalAmount>
                                                    <div>
                                                        <s class="text-muted small">${originalTotalAmount} грн</s>
                                                        <div class="text-success">
                                                            Savings: ${(originalTotalAmount - invoice.totalAmount)} грн
                                                        </div>
                                                    </div>
                                                </#if>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </#if>

                        <div class="text-center my-4">
                            <p class="lead">Your booking has been successfully completed and accepted for processing.</p>
                            <p>Your booking number: <strong>${booking.id}</strong></p>
                            <p>Our manager will contact you shortly to confirm the details.</p>
                        </div>

                        <div class="text-center mt-4">
                            <a href="/" class="btn btn-outline-success">Return to homepage</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <style>
        .text-decoration-line-through {
            text-decoration: line-through !important;
        }
    </style>
</@p.pages>
