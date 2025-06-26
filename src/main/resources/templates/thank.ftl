<#import "customer/template_customer.ftl" as p>
<@p.pages>
    <div class="container my-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h3 class="mb-0">Дякуємо за ваше бронювання!</h3>
                    </div>
                    <div class="card-body">
                        <#if invoice??>
                            <h4>Рахунок-фактура</h4>
                            <div class="invoice-details p-3 border rounded mb-4">
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <strong>Номер рахунку:</strong> ${invoice.id}
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Дата:</strong> ${invoice.date?string("dd.MM.yyyy")}
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-6">
                                        <strong>Клієнт:</strong>
                                        ${booking.client.firstName}
                                        ${booking.client.lastName}
                                    </div>
                                    <div class="col-md-6">
                                        <strong>Метод оплати:</strong>
                                        ${booking.payment.paymentMethod}
                                    </div>
                                </div>
                                <div class="row mb-2">

                                </div>

                                <div class="booked-items mt-4">
                                    <h5 class="text-center mb-3">Заброньовані апартаменти:</h5>
                                    <table class="table table-striped">
                                        <thead>
                                        <tr>
                                            <th>Назва</th>
                                            <th>Номер</th>
                                            <th>Клас</th>
                                            <th>Дата заїзду</th>
                                            <th>Дата виїзду</th>
                                            <th>Ціна за добу</th>
                                            <th>Кількість ночей</th>
                                            <th>Вартість</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#list apartmentsWithNights as item>
                                            <#assign ahb = item.ahb>
                                            <#assign nights = item.nights>

                                            <tr>
                                                <td>${ahb.apartment.name}</td>
                                                <td>${ahb.apartment.apartmentNumber}</td>
                                                <td>${ahb.apartment.apartmentClass.apartmentClass}</td>
                                                <td>${ahb.checkIn}</td>
                                                <td>${ahb.checkOut}</td>
                                                <td>${ahb.apartment.pricePerNight} грн</td>
                                                <td>${nights}</td>
                                                <td>${ahb.apartment.pricePerNight * nights} грн</td>
                                            </tr>
                                        </#list>
                                        </tbody>
                                        <tfoot>
                                        <tr>
                                            <td colspan="8" class="text-end text-center">
                                                <strong>Загальна сума: ${invoice.totalAmount} грн</strong>
                                            </td>
                                        </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                        </#if>

                        <div class="text-center my-4">
                            <p class="lead">Ваше бронювання успішно оформлено та прийнято до обробки.</p>
                            <p>Номер вашого бронювання: <strong>${booking.id}</strong></p>
                            <p>Наш менеджер зв'яжеться з вами найближчим часом для підтвердження деталей.</p>
                        </div>

                        <div class="text-center mt-4">
                            <a href="/" class="btn btn-outline-success">Повернутися на головну</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@p.pages>