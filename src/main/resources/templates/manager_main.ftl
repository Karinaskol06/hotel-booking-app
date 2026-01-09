<#import "manager/main/templ_manager.ftl" as p>
<@p.pages>

    <!-- Welcome Card -->
    <div class="card shadow-sm mb-4" style="border-left: 6px solid #738cbf; border-radius: 12px;">
        <div class="card-body">
            <h2 class="card-title mb-3">Welcome to the Manager Dashboard!</h2>
            <p class="card-text">
                This is your control panel. Here you can efficiently manage apartments,
                bookings, reviews, and other system features.
            </p>
        </div>
    </div>

    <!-- Stats Overview -->
    <div class="row g-4 mb-4">
        <div class="col-md-4">
            <div class="card text-white" style="background-color: rgba(122, 159, 191, 0.76); border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Apartment Classes</h5>
                    <p class="card-text fs-4">5 active</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: rgba(242, 135, 5, 0.72); border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Apartments</h5>
                    <p class="card-text fs-4">8 available</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card text-white" style="background-color: rgba(89, 108, 0, 0.69); border-radius: 12px;">
                <div class="card-body">
                    <h5 class="card-title">Bookings</h5>
                    <p class="card-text fs-4">Check new requests</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Illustration + Info -->
    <div class="card shadow-sm border-0" style="border-radius: 12px;">
        <div class="row g-0">
            <div class="col-md-6">
                <img src="/static/images/manager-main.png"
                     class="img-fluid rounded-start"
                     alt="Manager Dashboard Illustration"
                     style="border-radius: 12px 0 0 12px;">
            </div>
            <div class="col-md-6 d-flex align-items-center">
                <div class="card-body">
                    <h5 class="card-title">Tip of the Day</h5>
                    <p class="card-text">
                        Regularly review new feedback and bookings to improve customer service
                        and avoid misunderstandings.
                    </p>
                    <p class="card-text">
                        <small class="text-muted">Updated today</small>
                    </p>
                </div>
            </div>
        </div>
    </div>

</@p.pages>
