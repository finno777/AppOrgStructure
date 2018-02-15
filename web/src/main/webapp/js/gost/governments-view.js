/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], function ($) {
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var dialogMessages = {
        division: {
            deleteConfirmation: 'Вы уверены, что хотите удалить отдел?'
        },
        government: {
            deleteConfirmation: 'Вы уверены, что хотите удалить управление? Так же будут удалены все отделы, относящиеся к данному управлению.',
            deleteCuratorConfirmation: 'Вы уверены, что хотите удалить куратора управления?'
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

    $(document).on('click', byData('delete-division'), function (e) {
        e.preventDefault();
        var id = +$(this).data('delete-division');
        var container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.division.deleteConfirmation).then(function () {
            preloader.show();
            return window.dataService.deleteDivision(id);
        }).then(function () {
            container.remove();
        }).catch(function (err) {
            return console.error(err);
        }).then(preloader.hide);
    });

    $(document).on('click', byData('delete-curator'), function (e) {
        e.preventDefault();
        var id = +$(this).data('delete-curator');
        var container = $(this).parents(byData('remove-container'));

        confirmation(dialogMessages.government.deleteCuratorConfirmation).then(function () {
            preloader.show();
            return window.dataService.removeCurator(id);
        }).then(function () {
            container.remove();
        }).catch(function (err) {
            return console.error(err);
        }).then(preloader.hide);
    });

    $(document).on('click', byData('delete-government'), function (e) {
        e.preventDefault();
        var id = +$(this).data('delete-government');

        confirmation(dialogMessages.government.deleteConfirmation).then(function () {
            preloader.show();
            return window.dataService.deleteGovernment(id);
        }).then(function () {
            location = managementApp.redirect;
        }).catch(function (err) {
            return console.error(err);
        }).then(preloader.hide);
    });
});
//# sourceMappingURL=maps/governments-view.js.map
