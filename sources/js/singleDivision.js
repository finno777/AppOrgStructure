/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    const dialogMessages = {
        deletePerson: 'Вы уверены, что хотите удалить участника отдела?'
    }

    const confirmation = (text) => {
        const tpl = $(`<div title="Подтвердите действие"><p>${text}</p></div>`);

        tpl.appendTo('body');

        return new Promise((resolve, reject) => {
            tpl.dialog({
                autoOpen: true,
                resizable: false,
                draggable: false,
                modal: true,
                width: 700,
                buttons: [{
                      text: "Да",
                      class: "button button--primary",
                      click: function() {
                        $( this ).dialog('close');
                        tpl.remove();
                        resolve(true);
                      }
                    }, {
                      text: "Нет",
                      class: "button",
                      click: function() {
                        $( this ).dialog('close');
                        tpl.remove();
                        reject(false);
                      }
                }]
            });
        });
    };

    $(document).on('click', byData('remove-person'), function(e) {
        e.preventDefault();
        const id = +$(this).data('remove-person');
        const container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.deletePerson)
            .then(() => {
                preloader.show();
                return window.dataService.removeDivisionPerson(id);
            })
            .then(() => container.remove())
            .catch(console.error)
            .then(() => preloader.hide());
    });
});
