/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };

    $(document).on('click', byData('remove-person'), function (e) {
        e.preventDefault();
        var personId = $(this).data('remove-person');
        var organId = orgStruct.organId;
        var el = $(this);

        dataService.removePersonOrgan({
            personId: personId,
            organId: organId
        }).then(function () {
            el.parents('.org-members__row').remove();
        });
    });
});
//# sourceMappingURL=maps/organ.js.map
