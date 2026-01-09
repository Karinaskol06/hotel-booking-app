<#import "customer/template_customer.ftl" as p>
<#import "/spring.ftl" as s>
<@p.pages>

    <h3 class="text-center mt-4 mb-5">New User Registration</h3>

    <form action="/registration" method="post" class="container d-flex flex-column align-items-center" style="max-width: 500px;">

        <@s.bind "users"/>

        <div class="align-items-center">
            <div class="mb-3 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="username" class="form-label" style="width: 40%;">Username</label>
                <div style="width: 55%;">
                    <@s.formInput "users.username"/>
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>

            <div class="mb-3 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="password" class="form-label" style="width: 40%;">Password</label>
                <div style="width: 55%;">
                    <@s.formInput "users.password"/>
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>

            <@s.bind "clients"/>

            <div class="mb-3 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="firstName" class="form-label" style="width: 40%;">First Name</label>
                <div style="width: 55%;">
                    <@s.formInput "clients.firstName"/>
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>

            <div class="mb-3 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="lastName" class="form-label" style="width: 40%;">Last Name</label>
                <div style="width: 55%;">
                    <@s.formInput "clients.lastName"/>
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>

            <div class="mb-3 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="email" class="form-label" style="width: 40%;">Email</label>
                <div style="width: 55%;">
                    <@s.formInput "clients.email"/>
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>

            <div class="mb-4 w-100 d-flex justify-content-between align-items-lg-baseline">
                <label for="phone" class="form-label" style="width: 40%;">Phone</label>
                <div style="width: 55%;">
                    <@s.formInput "clients.phone" />
                    <@s.showErrors "<div class='text-danger'></div>" />
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-center w-100 mb-4">
            <button type="submit" class="btn btn-outline-success w-50">Add User</button>
        </div>
    </form>

</@p.pages>
