<#import "customer/template_customer.ftl" as p>
<@p.pages>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Playwrite+AU+SA:wght@100..400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/styles.css">

    <div class="container mt-3 mb-3">
        <#include "customer/buttonGroup.ftl">
    </div>

    <!-- cards -->
    <h2 class="text-center mt-3" id="apartment-classes">Класи апартаментів</h2>
    <div class="container pt-4">
      <#if apartment_classes??>
        <div class="row row-cols-2 g-4">
          <#list apartment_classes as apartClass>
            <div class="col">
              <div class="card h-100" style="width: 100%; max-width: 540px; height: 300px;">
                <div class="row g-0 h-100">
                  <div class="col-md-4">
                    <a href="/apartment_class/${apartClass.id}">
                      <img src="${apartClass.linkImg}" class="img-fluid rounded-start"
                         alt="..." style="width: 100%; height: 100%; object-fit: cover;">
                    </a>
                  </div>
                  <div class="col-md-8">
                    <div class="card-body d-flex flex-column">
                      <h4 class="card-title"><b>${apartClass.apartmentClass}</b></h4>
                      <p class="card-text flex-grow-1">${apartClass.classDescription}</p>
                      <p class="card-text"><small class="text-body-secondary">${apartClass.facilities}</small></p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </#list>
        </div>
      </#if>
    </div>

</@p.pages>