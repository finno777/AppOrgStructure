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

    var dialogMessages = {
        deletePerson: 'Вы уверены, что хотите удалить участника отдела?'
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
                        resolve(true);
                    }
                }, {
                    text: "Нет",
                    class: "button",
                    click: function click() {
                        $(this).dialog('close');
                        tpl.remove();
                        reject(false);
                    }
                }]
            });
        });
    };

    $(document).on('click', byData('remove-person'), function (e) {
        e.preventDefault();
        var id = +$(this).data('remove-person');
        var container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.deletePerson).then(function () {
            preloader.show();
            return window.dataService.removeDivisionPerson(id);
        }).then(function () {
            return container.remove();
        }).catch(console.error).then(function () {
            return preloader.hide();
        });
    });
});
//# sourceMappingURL=maps/singleDivision.js.map
