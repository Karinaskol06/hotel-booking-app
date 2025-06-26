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
