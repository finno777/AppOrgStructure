/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    
    $(document).on('click', byData('expand-subgroup'), function(e) {
        e.preventDefault();
        const current = $(this).hasClass('expand--active');

        $(this).toggleClass('expand--active', !current);
        if(!current) {
            $('span', this).text('Скрыть');
            $(this).parents('.government-section').find('.government-section__list').show();
        } else {
            $('span', this).text('Показать');
            $(this).parents('.government-section').find('.government-section__list').hide();
        }
    });
});
