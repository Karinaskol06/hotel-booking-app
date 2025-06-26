<#import "customer/template_customer.ftl" as p>
<@p.pages>
    <h2 class="text-center mt-5 mb-4">Форма аутентифікації користувача</h2>

    <div class="d-flex justify-content-center align-items-center">
        <form method="post" action="/login" style="width: 450px;">
            <div class="form-floating mb-3">
                <input type="text" name="username" class="form-control" id="floatingInput" placeholder="Введіть ім'я користувача">
                <label for="floatingInput">Username</label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Введіть пароль">
                <label for="floatingPassword">Password</label>
            </div>

            <div class="d-flex flex-column align-items-center" style="width: 200px; margin: 0 auto;">
                <a href="/registration" class="btn btn-outline-secondary mb-2 w-100">
                    До форми реєстрації
                </a>
                <button type="submit" class="btn btn-outline-success w-100">
                    Увійти
                </button>
            </div>
        </form>
    </div>
</@p.pages>
