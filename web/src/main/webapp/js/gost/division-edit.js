/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/tinymce', 'SHARED/gostFileManager'], function ($) {
    var managementApp = window.managementApp;
    var cr = managementApp.cr;
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    var orgTypes = {
        organizations: 1,
        groupOrganizations: 2,
        subGroupOrganizations: 3
    };

    var validate = function validate() {
        var errors = [];

        if (!$.trim($('#divisionForm__name').val()).length) {
            errors.push('Название');
        }

        if (!$.trim(tinymce.get('divisionForm__description').getContent()).length) {
            errors.push('Задачи отдела');
        }

        if (!$.trim($('#icon').html()).length) {
            errors.push('Иконка');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    var orgForm = {
        init: function init() {

            $(document).on('click', '#addIcon', function (e) {
                e.preventDefault();

                var options = {
                    appId: managementApp.appId,
                    portalName: managementApp.portalName,
                    appName: 'IconStore',
                    fmType: 'icons',
                    allowedFileFormats: ["all"],
                    constraints: {
                        maxFileCount: 1
                    },
                    onClose: function close() {
                        $('#icon').empty().append($('<img src="' + RSTFileManager.getFiles()[0].path + '" alt="\u0418\u043A\u043E\u043D\u043A\u0430">'));
                    }
                };
                RSTFileManager.open(options);
            });

            $('#divisionForm').on('submit', function (e) {
                e.preventDefault();
                if (!validate()) {
                    return;
                }

                var id = +$('#divisionForm__id').val();
                var governmentId = +$('#divisionForm__governmentId').val();

                var data = {
                    id: !!id && !isNaN(id) ? id : null,
                    title: $.trim($('#divisionForm__name').val()),
                    uuid: $('#icon img').attr('src'),
                    description: $.trim(tinymce.get('divisionForm__description').getContent()),
                    deleted: false,
                    government: {
                        id: governmentId
                    }
                };

                preloader.show();
                window.dataService.saveDivision(data).then(function (res) {
                    preloader.hide();
                    location = managementApp.links.government;
                }).catch(function () {
                    console.error('error');
                    preloader.hide();
                });
            });
        }
    };

    $(document).ready(function () {
        orgForm.init();
    });
});
//# sourceMappingURL=maps/division-edit.js.map
