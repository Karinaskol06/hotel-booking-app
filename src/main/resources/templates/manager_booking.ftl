<#import "manager/templ_manager.ftl" as p>
<@p.pages>
    <style>
        body {
            background-color: #f4f9fc;
        }

        .booking-title {
            color: #0C3449;
        }

        .booking-card {
            border: none;
            border-radius: 1rem;
            background: linear-gradient(145deg, #e9f5fb, #ffffff);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
            transition: transform 0.2s ease;
        }

        .booking-card:hover {
            transform: translateY(-5px);
        }

        .booking-btn {
            background-color: #6AB9E2;
            color: #fff;
            font-size: 0.85rem;
            padding: 0.4rem 1rem;
            border: none;
            border-radius: 20px;
            transition: background-color 0.3s ease, transform 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .booking-btn:hover {
            background-color: #196B96;
            transform: scale(1.05);
            color: #fff;
        }

        .booking-save-btn {
            background-color: #89D99D;
            color: #fff;
            font-size: 0.85rem;
            padding: 0.4rem 1rem;
            border: none;
            border-radius: 20px;
            transition: background-color 0.3s ease, transform 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            margin-top: 5px;
        }

        .booking-save-btn:hover {
            background-color: #3B8C6E;
            transform: scale(1.05);
            color: #fff;
        }

        .card-title {
            color: #0C3449;
        }

        .card-body ul li {
            margin-bottom: 6px;
            color: #333;
        }

        .card-body ul li strong {
            color: #0c3449;
        }
    </style>

    <div class="container my-5">
        <h2 class="text-center mb-4 booking-title">Адміністрування бронювань</h2>

        <#if bookings??>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <#list bookings as booking>
                    <div class="col">
                        <div class="card booking-card h-100">
                            <div class="card-body">
                                <h5 class="card-title">Бронювання #${booking.id}</h5>
                                <ul class="list-unstyled mb-3">
                                    <li><strong>Ім'я:</strong> ${booking.client.firstName}</li>
                                    <li><strong>Прізвище:</strong> ${booking.client.lastName}</li>
                                    <li><strong>Телефон:</strong> ${booking.client.phone}</li>
                                    <li><strong>Email:</strong> ${booking.client.email}</li>
                                    <li><strong>Створено:</strong> ${booking.dateCreated?string("yyyy:MM:dd")}</li>
                                    <li><strong>Заїзд:</strong> ${booking.checkIn}</li>
                                    <li><strong>Виїзд:</strong> ${booking.checkOut}</li>
                                    <li><strong>Оплата:</strong> ${booking.payment.paymentMethod}</li>
                                    <li>
                                        <strong>Статус:</strong>
                                        <#switch booking.status?string>
                                            <#case "NOT_PROCESSED">Необроблене<#break>
                                            <#case "CONFIRMED">Підтверджене<#break>
                                            <#case "CANCELLED">Скасоване<#break>
                                            <#default>${booking.status}</#switch>
                                    </li>

                                    <form method="post" action="/manager/bookings/${booking.id}/status">
                                        <label for="statusSelect">Змінити статус:</label>
                                        <select name="status" id="statusSelect" class="form-select mb-3">
                                            <#list statuses as s>
                                                <option value="${s}"
                                                        <#if booking.status == s>selected</#if>>
                                                    ${s.getDisplayName()}
                                                </option>
                                            </#list>
                                        </select>
                                        <div style="text-align: center;">
                                            <button type="submit" class="booking-save-btn">Зберегти</button>
                                        </div>
                                    </form>

                                </ul>
                                <a href="/manager/bookings/${booking.id}" class="booking-btn w-100">
                                    Переглянути деталі
                                </a>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <p class="text-center text-muted mt-5">Бронювань не знайдено.</p>
        </#if>
    </div>
</@p.pages>
