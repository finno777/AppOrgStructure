/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    const dialogMessages = {
        TypeOrganization: {
            deleteConfirmation: 'Вы уверены, что хотите удалить тип организации? Вместе с типом организации будут удалены входящие в неё организации и группы организаций.'
        },
        Organization: {
            deleteConfirmation: 'Вы уверены, что хотите удалить организацию?'
        },
        GroupOrganizations: {
            deleteConfirmation: 'Вы уверены, что хотите удалить группу организаций?'
        }
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
                        resolve();
                      }
                    }, {
                      text: "Нет",
                      class: "button",
                      click: function() {
                        $( this ).dialog('close');
                        tpl.remove();
                        reject();
                      }
                }]
            });
        });
    };

    $(document).on('click', byData('remove'), function(e) {
        e.preventDefault();
        const id = $(this).data('remove');
        const type = $(this).data('type');
        const container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages[type].deleteConfirmation)
            .then(() => {
                preloader.show();
                return window.dataService[`delete${type}`](id);
            })
            .then(() => {
                container.remove();
            })
            .catch((err) => console.error(err))
            .then(preloader.hide);
    });
});
