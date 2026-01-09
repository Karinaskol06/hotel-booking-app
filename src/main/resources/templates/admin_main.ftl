<#import "admin/main/templ_admin.ftl" as p>
<@p.pages>

    <div class="card shadow-sm mb-4" style="border-left: 6px solid #08A696; border-radius: 12px;">
        <div class="card-body">
            <h2 class="card-title mb-3">Welcome to the Administrator Pages!</h2>
            <p class="card-text">
                This is your access control dashboard. You can manage user roles, logins and passwords,
                as well as add new modules to the system.
            </p>
        </div>
    </div>

    <div class="row g-4 mb-4">
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #8FBABF; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Users</h5>
                    <p class="card-text fs-4">5 active</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #0BBFAD; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Roles</h5>
                    <p class="card-text fs-4">3 in the system</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: #088C7F; border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Modules</h5>
                    <p class="card-text fs-4">2 new available</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Illustration + Info -->
    <div class="card shadow-sm border-0" style="border-radius: 12px;">
        <div class="row g-0">
            <div class="col-md-6">
                <img src="/static/images/admin-main.jpg"
                     class="img-fluid rounded-start"
                     alt="Admin Dashboard Illustration"
                     style="border-radius: 12px 0 0 12px;">
            </div>
            <div class="col-md-6 d-flex align-items-center">
                <div class="card-body">
                    <h5 class="card-title">Tip for Administrators</h5>
                    <p class="card-text">
                        Regularly review roles and monitor account security.
                        Do not forget to back up important data.
                    </p>
                    <p class="card-text"><small class="text-muted">Updated today</small></p>
                </div>
            </div>
        </div>
    </div>

</@p.pages>
