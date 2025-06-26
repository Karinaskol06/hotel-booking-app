<#import "admin/templ_admin.ftl" as p>
<@p.pages>

    <div class="card shadow-sm mb-4" style="border-left: 6px solid #08A696; border-radius: 12px;">
        <div class="card-body">
            <h2 class="card-title mb-3">Вітаємо на сторінках адміністратора!</h2>
            <p class="card-text">Це ваша панель керування доступом. Ви можете змінювати ролі користувачів, їх логіни та паролі, а також додавати нові модулі до системи.</p>
        </div>
    </div>
    <div class="row g-4 mb-4">
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #8FBABF; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Користувачі</h5>
                    <p class="card-text fs-4">5 активних</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #0BBFAD; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Ролі</h5>
                    <p class="card-text fs-4">3 у системі</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #088C7F; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Модулі</h5>
                    <p class="card-text fs-4">2 нових доступних</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Illustration + Info -->
    <div class="card shadow-sm border-0" style="border-radius: 12px;">
        <div class="row g-0">
            <div class="col-md-6">
                <img src="/static/images/admin-main.jpg" class="img-fluid rounded-start" alt="Admin Dashboard Illustration" style="border-radius: 12px 0 0 12px;">
            </div>
            <div class="col-md-6 d-flex align-items-center">
                <div class="card-body">
                    <h5 class="card-title">Порада для адміністратора</h5>
                    <p class="card-text">Регулярно оновлюйте ролі та слідкуйте за безпекою акаунтів користувачів. Не забувайте про резервне копіювання важливих даних.</p>
                    <p class="card-text"><small class="text-muted">Оновлено сьогодні</small></p>
                </div>
            </div>
        </div>
    </div>

</@p.pages>
