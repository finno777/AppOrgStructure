/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    $(document).on('click', byData('action', 'delete'), function(e) {
        e.preventDefault();
        const el = $(this);
        const wrapper = el.parents('.government');

        const id = +el.data('id');
        if(!id || isNaN(id)) {
            return;
        }

        preloader.show();
        window.dataService.deleteGovernment(id)
            .then(() => {
                wrapper.hide().remove();
                preloader.hide();
            }).catch(() => {
                wrapper.hide().remove();
                preloader.hide();
            });
    });
});
