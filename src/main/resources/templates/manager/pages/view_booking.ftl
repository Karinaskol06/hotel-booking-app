<#import "../main/templ_manager.ftl" as p>
<@p.pages>

    <div class="booking-wrapper">
        <h2 class="booking-title">Booking Information</h2>

        <#if apartmentsWithNights??>
            <#list apartmentsWithNights as item>
                <#assign ahb = item.ahb>
                <#assign nights = item.nights>

                <div class="booking-card">
                    <div class="booking-info">
                        <span>Apartments:</span> ${ahb.apartment.name}
                    </div>
                    <div class="booking-info">
                        <span>Price per night:</span> ${ahb.apartment.pricePerNight} UAH
                    </div>
                    <div class="booking-info">
                        <span>Check-in:</span> ${ahb.checkIn}
                    </div>
                    <div class="booking-info">
                        <span>Check-out:</span> ${ahb.checkOut}
                    </div>
                    <div class="booking-info">
                        <span>Number of nights:</span> ${nights}
                    </div>
                    <div class="booking-info">
                        <span>Payment method:</span> ${ahb.booking.payment.paymentMethod}
                    </div>

                    <div class="booking-info">
                        <span>Booking status:</span>
                        <#switch ahb.booking.status?string>
                            <#case "NOT_PROCESSED">Not processed<#break>
                            <#case "CONFIRMED">Confirmed<#break>
                            <#case "CANCELLED">Cancelled<#break>
                            <#default>${ahb.booking.status}
                        </#switch>
                    </div>

                    <div class="booking-info total">
                        <span>Total:</span> ${ahb.apartment.pricePerNight * nights} UAH
                    </div>
                </div>
            </#list>

            <div class="booking-card total-summary">
                <div class="booking-info total">
                    <span><strong>Total cost:</strong></span>
                    <span><strong>${totalValue} UAH</strong></span>
                </div>
            </div>

            <h4 class="text-center">User Information</h4>
            <#assign user = apartmentsWithNights[0].ahb.booking.client>
            <div class="booking-card">
                <div class="booking-info">
                    <span>First name:</span> ${user.firstName}
                </div>
                <div class="booking-info">
                    <span>Last name:</span> ${user.lastName}
                </div>
                <div class="booking-info">
                    <span>Email:</span> ${user.email}
                </div>
                <div class="booking-info">
                    <span>Phone number:</span> ${user.phone}
                </div>
            </div>
        </#if>

    </div>
</@p.pages>
