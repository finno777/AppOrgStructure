/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    $(document).on('click', byData('action', 'delete'), function (e) {
        e.preventDefault();
        var el = $(this);
        var wrapper = el.parents('.government');

        var id = +el.data('id');
        if (!id || isNaN(id)) {
            return;
        }

        preloader.show();
        window.dataService.deleteGovernment(id).then(function () {
            wrapper.hide().remove();
            preloader.hide();
        }).catch(function () {
            wrapper.hide().remove();
            preloader.hide();
        });
    });
});
//# sourceMappingURL=maps/governments.js.map
