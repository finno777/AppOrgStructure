/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/gostValidation'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    $(document).ready(function () {

        $('#typeForm').on('submit', function (e) {
            e.preventDefault();

            var obj = { name: $.trim($('#typeForm__title').val()) };

            gostValidation.setAllValid();

            if (!(gostValidation.validateRequiredValues(obj) && gostValidation.validateValuesLength(obj))) {
                return;
            }

            var id = +$.trim($('#typeForm__id').val());

            var data = {
                id: id && !isNaN(id) ? id : null,
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
//# sourceMappingURL=maps/type-edit.js.map
