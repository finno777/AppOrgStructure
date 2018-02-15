/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    
    $(document).on('click', byData('remove-person'), function(e) {
        e.preventDefault();
        const personId = $(this).data('remove-person');
        const organId = orgStruct.organId;
        const el = $(this);

        dataService.removePersonOrgan({
            personId,
            organId
        }).then(() => {
            el.parents('.org-members__row').remove();
        }); 
    });
});
