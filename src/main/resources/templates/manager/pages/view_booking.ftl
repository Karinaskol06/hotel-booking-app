<#import "../main/templ_manager.ftl" as p>
<@p.pages>

    <div class="booking-wrapper">
        <h2 class="booking-title">Інформація про бронювання</h2>

        <#if apartmentsWithNights??>
            <#list apartmentsWithNights as item>
                <#assign ahb = item.ahb>
                <#assign nights = item.nights>

                <div class="booking-card">
                    <div class="booking-info">
                        <span>Апартаменти:</span> ${ahb.apartment.name}
                    </div>
                    <div class="booking-info">
                        <span>Ціна за ніч:</span> ${ahb.apartment.pricePerNight} грн
                    </div>
                    <div class="booking-info">
                        <span>Заселення:</span> ${ahb.checkIn}
                    </div>
                    <div class="booking-info">
                        <span>Виселення:</span> ${ahb.checkOut}
                    </div>
                    <div class="booking-info">
                        <span>Кількість ночей:</span> ${nights}
                    </div>
                    <div class="booking-info">
                        <span>Спосіб оплати: </span>${ahb.booking.payment.paymentMethod}
                    </div>

                    <div class="booking-info">
                        <span>Статус бронювання: </span>
                        <#switch ahb.booking.status?string>
                            <#case "NOT_PROCESSED">Необроблене<#break>
                            <#case "CONFIRMED">Підтверджене<#break>
                            <#case "CANCELLED">Скасоване<#break>
                            <#default>${ahb.booking.status}
                        </#switch>
                    </div>

                    <div class="booking-info total">
                        <span>Сума:</span> ${ahb.apartment.pricePerNight * nights} грн
                    </div>
                </div>
            </#list>

            <div class="booking-card total-summary">
                <div class="booking-info total">
                    <span><strong>Загальна вартість:</strong></span>
                    <span><strong>${totalValue} грн</strong></span>
                </div>
            </div>

            <h4 class="text-center">Інформація про користувача</h4>
            <#assign user = apartmentsWithNights[0].ahb.booking.client>
            <div class="booking-card">
                <div class="booking-info">
                    <span>Ім'я: </span> ${user.firstName}
                </div>
                <div class="booking-info">
                    <span>Прізвище: </span>${user.lastName}
                </div>
                <div class="booking-info">
                    <span>Електронна пошта: </span>${user.email}
                </div>
                <div class="booking-info">
                    <span>Номер телефону: </span>${user.phone}
                </div>
            </div>
        </#if>

    </div>
</@p.pages>
