/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], $ => {
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const dialogMessages = {
        division: {
            deleteConfirmation: 'Вы уверены, что хотите удалить отдел?'
        },
        government: {
            deleteConfirmation: 'Вы уверены, что хотите удалить управление? Так же будут удалены все отделы, относящиеся к данному управлению.',
            deleteCuratorConfirmation: 'Вы уверены, что хотите удалить куратора управления?'
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

    
    $(document).on('click', byData('delete-division'), function(e) {
        e.preventDefault();
        const id = +$(this).data('delete-division');
        const container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.division.deleteConfirmation)
            .then(() => {
                preloader.show();
                return window.dataService.deleteDivision(id);
            })
            .then(() => {
                container.remove();
            })
            .catch((err) => console.error(err))
            .then(preloader.hide);
    });
    
    $(document).on('click', byData('delete-curator'), function(e) {
        e.preventDefault();
        const id = +$(this).data('delete-curator');
        const container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.government.deleteCuratorConfirmation)
            .then(() => {
                preloader.show();
                return window.dataService.removeCurator(id);
            })
            .then(() => {
                container.remove();
            })
            .catch((err) => console.error(err))
            .then(preloader.hide);
    });
    
    $(document).on('click', byData('delete-government'), function(e) {
        e.preventDefault();
        const id = +$(this).data('delete-government');

        confirmation(dialogMessages.government.deleteConfirmation)
            .then(() => {
                preloader.show();
                return window.dataService.deleteGovernment(id);
            })
            .then(() => {
                location = managementApp.redirect;
            })
            .catch((err) => console.error(err))
            .then(preloader.hide);
    });
    
});
