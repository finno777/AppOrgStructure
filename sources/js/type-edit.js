/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/gostValidation'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    $(document).ready(() => {
        
        $('#typeForm').on('submit', function (e) {
            e.preventDefault();

            const obj = { name: $.trim($('#typeForm__title').val()) }

            gostValidation.setAllValid();

            if(!(gostValidation.validateRequiredValues(obj) && gostValidation.validateValuesLength(obj))) {
                return;
            }

            const id = +$.trim($('#typeForm__id').val());

            const data = {
                id: id && !isNaN(id) ? id: null,
                title: obj.name
            };

            preloader.show();
            window.dataService.saveTypeOrganization(data).then(function (res) {
                preloader.hide();
                location = managementApp.links.redirect;
            }).catch(function (err) {
                console.error(err);
                preloader.hide();
            });
        });
    });
});
