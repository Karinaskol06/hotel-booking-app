

<#import "customer/template_customer.ftl" as p>

<@p.pages>
    <style>
        .contact-subtitle {
            color: #262118;
        }
        .contact-list li {
            margin-bottom: 0.5rem;
            font-size: 1.1rem;
        }
        .contact-icon {
            color: #E78200;
            margin-right: 0.5rem;
        }
        .card-custom {
            background-color: white;
            border-left: 5px solid #A76009;
            box-shadow: 0 15px 26px rgba(0, 0, 0, 0.15);
        }
        .social-icons a {
            font-size: 1.4rem;
            margin-right: 15px;
            color: #0F3A2B;
            transition: color 0.3s ease;
        }
        .social-icons a:hover {
            color: #E78200;
        }
    </style>
    <div class="container py-4">
        <h3 class="text-center contact-header mb-2">Контакти</h3>

        <div class="row justify-content-center mb-4">
            <div class="col-md-8">
                <#include "customer/buttonGroup.ftl">
            </div>
        </div>

        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card card-custom p-4">
                    <h4 class="contact-subtitle mb-3">Зв'яжіться з нами за потреби</h4>
                    <ul class="list-unstyled contact-list">
                        <li><i class="bi bi-telephone-fill contact-icon"></i>
                            <strong>Телефон:</strong> +38 (099) 234-56-78
                        </li>
                        <li><i class="bi bi-envelope-fill contact-icon"></i>
                            <strong>Електронна пошта:</strong> velourhotel@gmail.com
                        </li>
                        <li><i class="bi bi-chat-dots-fill contact-icon"></i>
                            <strong>Месенджер:</strong> Telegram / Viber / WhatsApp
                        </li>
                    </ul>

                    <hr class="my-4">

                    <h5 class="contact-subtitle mb-2">Ми в соцмережах</h5>
                    <p>Слідкуйте за нашими оновленнями, новинами та спеціальними пропозиціями:</p>
                    <div class="social-icons">
                        <a href="https://facebook.com" target="_blank"><i class="bi bi-facebook"></i></a>
                        <a href="https://instagram.com" target="_blank"><i class="bi bi-instagram"></i></a>
                        <a href="https://linkedin.com" target="_blank"><i class="bi bi-linkedin"></i></a>
                        <a href="https://twitter.com" target="_blank"><i class="bi bi-twitter-x"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

</@p.pages>
