/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    var dialogMessages = {
        TypeOrganization: {
            deleteConfirmation: 'Вы уверены, что хотите удалить тип организации? Вместе с типом организации будут удалены входящие в неё организации и группы организаций.'
        },
        Organization: {
            deleteConfirmation: 'Вы уверены, что хотите удалить организацию?'
        },
        GroupOrganizations: {
            deleteConfirmation: 'Вы уверены, что хотите удалить группу организаций?'
        }
    };

    var confirmation = function confirmation(text) {
        var tpl = $('<div title="\u041F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0435 \u0434\u0435\u0439\u0441\u0442\u0432\u0438\u0435"><p>' + text + '</p></div>');

        tpl.appendTo('body');

        return new Promise(function (resolve, reject) {
            tpl.dialog({
                autoOpen: true,
                resizable: false,
                draggable: false,
                modal: true,
                width: 700,
                buttons: [{
                    text: "Да",
                    class: "button button--primary",
                    click: function click() {
                        $(this).dialog('close');
                        tpl.remove();
                        resolve();
                    }
                }, {
                    text: "Нет",
                    class: "button",
                    click: function click() {
                        $(this).dialog('close');
                        tpl.remove();
                        reject();
                    }
                }]
            });
        });
    };

    $(document).on('click', byData('remove'), function (e) {
        e.preventDefault();
        var id = $(this).data('remove');
        var type = $(this).data('type');
        var container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages[type].deleteConfirmation).then(function () {
            preloader.show();
            return window.dataService['delete' + type](id);
        }).then(function () {
            container.remove();
        }).catch(function (err) {
            return console.error(err);
        }).then(preloader.hide);
    });
});
//# sourceMappingURL=maps/subdivision.js.map
