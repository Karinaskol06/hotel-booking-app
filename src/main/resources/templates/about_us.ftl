<#import "customer/template_customer.ftl" as p>

<@p.pages>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <title>About Us</title>
    </head>
    <body>

    <section style="background-color: #f3f3f3; padding: 80px 0;" class="mb-5">
        <div style="max-width: 1200px; margin: 0 auto; display: flex; flex-wrap: wrap;">

            <!-- Image -->
            <div style="flex: 0 0 40%; padding: 0 10px; text-align: center;">
                <div style="position: relative; width: 100%; padding-top: 100%; border-radius: 16px; overflow: hidden;">
                    <img src="/static/images/about_us/about_us1.jpg"
                         style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;" />
                </div>
            </div>

            <!-- Text -->
            <div style="flex: 0 0 60%; padding: 0 10px; display: flex; align-items: center;">
                <div style="max-width: 460px; margin: 0 auto;">
                    <h4 style="color: #242925; text-transform: uppercase; font-size: 12px; font-weight: 600; margin-bottom: 10px;">
                        Welcome to
                    </h4>
                    <h1 style="color: #242925; font-size: 2.5rem; font-family: serif; margin-bottom: 20px;">
                        Velour Hotel
                    </h1>
                    <p style="color: #242925; margin: 0 0 30px 0; padding-top: 20px;">
                        Our hotel is a blend of modern comfort, refined design, and genuine hospitality.
                        Relax by the sea and enjoy tranquility and top-level service.
                    </p>

                </div>
            </div>

        </div>
    </section>

    <!-- Services -->
    <section class="section" style="padding: 60px 0; text-align: center;">
        <div class="features" style="display: flex; justify-content: center; flex-wrap: wrap; gap: 60px;">
            <div class="feature" style="width: 300px;">
                <h6>Spa & Wellness</h6>
                <p>Relax in our modern spa with massages, saunas, and a salt room.</p>
            </div>
            <div class="feature" style="width: 300px;">
                <h6>24/7 Service</h6>
                <p>Concierge, transfers, excursion assistance — we are always here for you.</p>
            </div>
            <div class="feature" style="width: 300px;">
                <h6>Private <br> Beach</h6>
                <p>Enjoy peace and comfort on our private beachfront.</p>
            </div>
        </div>
    </section>

    <section class="work-section">
        <h2 style="text-align: center;">Our Advantages</h2>
        <div class="work-columns" style="display: flex; justify-content: center; gap: 30px; flex-wrap: wrap; margin-top: 40px;">
            <div class="work-item" style="width: 280px; text-align: center;">
                <div style="aspect-ratio: 1 / 1; overflow: hidden; border-radius: 12px; margin-bottom: 12px;">
                    <img src="/static/images/about_us/about_us2.jpg" style="width: 100%; height: 100%; object-fit: cover;" />
                </div>
                <h6>Luxury Interior Design</h6>
            </div>
            <div class="work-item" style="width: 280px; text-align: center;">
                <div style="aspect-ratio: 1 / 1; overflow: hidden; border-radius: 12px; margin-bottom: 12px;">
                    <img src="/static/images/about_us/about_us3.jpg" style="width: 100%; height: 100%; object-fit: cover;" />
                </div>
                <h6>Atmospheric Bar & Lounge Area</h6>
            </div>
            <div class="work-item" style="width: 280px; text-align: center;">
                <div style="aspect-ratio: 1 / 1; overflow: hidden; border-radius: 12px; margin-bottom: 12px;">
                    <img src="/static/images/about_us/about_us4.jpg" style="width: 100%; height: 100%; object-fit: cover;" />
                </div>
                <h6>Personalized Service</h6>
            </div>
        </div>
    </section>

    <!-- Additional information -->
    <section class="about-section mt-3" style="display: flex; align-items: center; gap: 70px; flex-wrap: wrap;">
        <div class="about-text" style="flex: 1; min-width: 280px; position: relative;">
            <h2 class="text-center mb-4">Why Choose Us</h2>
            <p>Every detail of our hotel is designed for your maximum comfort: comfortable beds, natural materials in the interior, and a cozy atmosphere.</p>
            <p>Guests appreciate our care, cleanliness, food quality, and impeccable service.</p>
            <a href="/#apartment-classes" style="
                  position: absolute;
                  bottom: -90px;
                  left: 50%;
                  transform: translateX(-50%);
                  display: inline-block;
                  padding: 1.1rem 40px;
                  font-size: 1rem;
                  font-weight: 700;
                  text-transform: capitalize;
                  color: #242925;
                  border: 1px solid #242925;
                  border-radius: 2.5rem;
                  text-decoration: none;">
                Book Now
            </a>
        </div>
        <div class="about-image" style="flex: 0 0 400px; max-width: 100%;">
            <img src="/static/images/about_us/about_us5.jpg" style="width: 100%; border-radius: 16px; object-fit: cover;" />
        </div>
    </section>

    <div style="max-width: 1200px; margin: 0 auto; padding: 0 5px;">
        <div class="d-flex justify-content-between gap-2 mb-3 mt-4">
            <#if apartment_classes??>
                <#list apartment_classes as apartClass>
                    <a href="/apartment_class/${apartClass.id}" class="text-decoration-none flex-grow-1">
                        <button type="button" class="btn custom-button">
                            ${apartClass.apartmentClass}
                        </button>
                    </a>
                </#list>
            </#if>
        </div>
    </div>

    <!-- Reviews -->
    <section class="section" style="padding: 60px 20px; margin-top: 40px; margin-bottom: 25px">
        <h2 style="text-align: center;">Guest Reviews</h2>
        <div style="display: flex; justify-content: center; flex-wrap: wrap; gap: 30px; margin-top: 40px;">
            <div style="max-width: 300px; background-color: #f3f3f3; padding: 20px; border-radius: 12px;">
                <p>"The best hotel we’ve ever stayed at! Extremely polite staff and delicious breakfasts."</p>
                <strong>– Polina S.</strong>
            </div>
            <div style="max-width: 300px; background-color: #f3f3f3; padding: 20px; border-radius: 12px;">
                <p>"A perfect place to relax. The infinity pool view is breathtaking!"</p>
                <strong>– Nataliia S.</strong>
            </div>
            <div style="max-width: 300px; background-color: #f3f3f3; padding: 20px; border-radius: 12px;">
                <p>"We will definitely come back! The interior is very stylish, everything is new and clean."</p>
                <strong>– Artem T.</strong>
            </div>
        </div>
    </section>

    </body>
    </html>
</@p.pages>
