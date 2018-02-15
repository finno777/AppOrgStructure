/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };

    $(document).on('click', byData('expand-subgroup'), function (e) {
        e.preventDefault();
        var current = $(this).hasClass('expand--active');

        $(this).toggleClass('expand--active', !current);
        if (!current) {
            $('span', this).text('Скрыть');
            $(this).parents('.government-section').find('.government-section__list').show();
        } else {
            $('span', this).text('Показать');
            $(this).parents('.government-section').find('.government-section__list').hide();
        }
    });
});
//# sourceMappingURL=maps/group.js.map
