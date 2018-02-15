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
    var curatorTemplate = function curatorTemplate(selected) {
        return '\n        <div class="curator">\n        \n            <div class="curator__border"></div>\n\n            <label class="control__label">\u0424\u0418\u041E \u043A\u0443\u0440\u0430\u0442\u043E\u0440\u0430</label>\n            <div class="curator__control">\n                <div class="curator__control-input">\n                <select>\n                    <option selected>\u041D\u0435 \u0432\u044B\u0431\u0440\u0430\u043D\u043E</option>\n                    ' + managementApp.curators().reduce(function (template, item) {
            return template + '<option value="' + item.id + '" ' + (selected && item.id === selected.id ? 'selected' : '') + '>' + item.name + '</option>';
        }, '') + '\n                </select>\n                    <svg class="curator__control-input-arrow"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + cr + '/sprite/sprite.svg#arrow_down"></use></svg>\n                </div>\n                <button class="curator__control-button" type="button">\n                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + cr + 'sprite/sprite.svg#settings"></use></svg>\n                </button>\n                <div class="curator__control-dropdown" style="display: none;">\n                    <a href="#" data-curator="remove">\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0441\u0432\u044F\u0437\u043A\u0443</a>\n                </div>\n            </div>\n        \n            <label class="control__label">\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435</label>\n            <div class="curator__control">\n                <div class="curator__control-input">\n                    <input type="text" id="curator__description" value="' + (selected ? selected.description : '') + '">\n                </div>\n            </div>\n        </div>\n    ';
    };

    var orgTypes = {
        organizations: 1,
        groupOrganizations: 2,
        subGroupOrganizations: 3
    };

    var populateChecklistItems = function populateChecklistItems(groups, orgtype) {
        return groups.reduce(function (template, item) {
            return template + '<li><label><input type="checkbox" data-org-type="' + orgtype + '" value="' + item.id + '"> <span>' + item.title + '</span></label></li>';
        }, '');
    };

    var renderChecklist = function renderChecklist(items) {
        var id = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : 'checklist';
        return '\n        <div class="checklist" id="' + id + '">\n            <span class="checklist__title">\u041D\u0435 \u0432\u044B\u0431\u0440\u0430\u043D\u043E</span>\n            <ul class="checklist__list">\n                ' + items + '\n            </ul>\n        </div>\n    ';
    };

    var collectChecklist = function collectChecklist(orgtype) {
        return $('.checklist input[data-org-type="' + orgtype + '"]:checked').reduce(function (res, item) {
            res.push({
                id: +$(item).val()
            });
            return res;
        }, []);
    };

    var validate = function validate() {
        var errors = [];

        if (!$.trim($('#governmentEditForm__title').val()).length) {
            errors.push('Наименования управления');
        }

        if (!$.trim($('#icon').html()).length) {
            errors.push('Иконка');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    var collectCurators = function collectCurators() {
        var res = [];
        $('.curator').each(function () {
            var $el = $(this);
            var val = $el.find('select').val();
            if (!!+val) {
                var source = managementApp.government ? $.grep(managementApp.government.curators, function (x) {
                    return x.id === +val;
                }) : [];
                res.push({
                    id: source.length ? source[0].linkId : null,
                    description: $.trim($el.find('#curator__description').val()),
                    person: {
                        id: +val
                    }
                });
            }
        });
        return res;
    };

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

    var orgForm = {
        bindHandlers: function bindHandlers() {
            // Handlers
            $('#governmentEditForm').on('submit', function (e) {
                e.preventDefault();
                if (!validate()) {
                    return;
                }

                var data = {
                    title: $.trim($('#governmentEditForm__title').val()),
                    description: $.trim(tinymce.get('governmentEditForm__description').getContent()),
                    id: managementApp.government ? managementApp.government.id : null,
                    uuid: $('#icon img').attr('src'),
                    deleted: false,
                    curators: collectCurators(),
                    organizations: collectChecklist(orgTypes.organizations),
                    groupOrganizations: collectChecklist(orgTypes.groupOrganizations),
                    subGroupOrganizations: collectChecklist(orgTypes.subGroupOrganizations),
                    person: managementApp.government && managementApp.government.person ? managementApp.government.person : null
                };

                preloader.show();
                window.dataService.save(data).then(function (res) {
                    preloader.hide();
                    location = managementApp.links.redirectMainURL;
                }).catch(function (err) {
                    console.error(err);
                    preloader.hide();
                });
            });

            $(document).on('click', '.curator__control-button', function (e) {
                e.stopImmediatePropagation();
                $(this).next('.curator__control-dropdown').toggle();
            });

            $('#addCurator').on('click', function (e) {
                e.preventDefault();
                $('#curators').prepend(curatorTemplate());
            });

            $(document).on('click', byData('curator', 'remove'), function (e) {
                e.preventDefault();
                var wrapper = $(this).parents('.curator');
                var id = +$('select option:selected', wrapper).val();
                var sourceItem = managementApp.government ? $.grep(managementApp.government.curators, function (x) {
                    return x.id === id;
                }) : [];

                // if(sourceItem.length) {
                //     preloader.show();
                //     window.dataService.removeCurator(sourceItem[0].linkId)
                //         .then(() => {
                //             wrapper.remove();
                //             preloader.hide();
                //             managementApp.government.curators = managementApp.government ? $.grep(managementApp.government.curators, x => x.id !== id) : [];
                //         }).catch((err) => {
                //             console.error(err);
                //             preloader.hide();
                //         });
                // } else {
                wrapper.remove();
                // }
            });

            $(document).on('click', function (e) {
                $('.curator__control-dropdown').hide();
                $('.checklist').not(function () {
                    return this.isEqualNode(e.target) || this.contains(e.target);
                }).removeClass('checklist--active');
            });

            $(document).on('click', '.checklist__title', function (e) {
                $(this).parent().toggleClass('checklist--active');
            });

            $(document).on('change', '.checklist input', function () {
                var wrap = $(this).parents('.checklist');

                $('.checklist__title', wrap).text($('label', wrap).reduce(function (res, item) {
                    if (!$(item).find('input').is(':checked')) {
                        return res;
                    }

                    res.push($(item).find('span').text());
                    return res;
                }, []).join(', ') || 'Не выбрано');
            });
        },
        init: function init() {
            orgForm.bindHandlers();

            $(renderChecklist('\n                ' + populateChecklistItems(managementApp.organizations(), orgTypes.organizations) + '\n                ' + populateChecklistItems(managementApp.groupOrganizations(), orgTypes.groupOrganizations) + '\n            ', 'organizationsChecklist')).appendTo('#organizations');

            $(renderChecklist(populateChecklistItems(managementApp.subGroupOrganizations(), orgTypes.subGroupOrganizations), 'subOrganizationsChecklist')).appendTo('#suborganizations');

            // Init form with existing data
            if (managementApp.government) {
                $.each(managementApp.government.curators, function (i, item) {
                    $('#curators').prepend(curatorTemplate(item));
                });

                $('#organizationsChecklist input').each(function () {

                    $(this).prop('checked', managementApp.government.organizations.indexOf(+$(this).val()) > -1 && +$(this).data('org-type') === orgTypes.organizations || managementApp.government.groupOrganizations.indexOf(+$(this).val()) > -1 && +$(this).data('org-type') === orgTypes.groupOrganizations);
                    $(this).trigger('change');
                });

                $('#subOrganizationsChecklist input').each(function () {
                    $(this).prop('checked', managementApp.government.subGroupOrganizations.indexOf(+$(this).val()) > -1 && +$(this).data('org-type') === orgTypes.subGroupOrganizations);
                    $(this).trigger('change');
                });
            } else {
                $('#curators').prepend(curatorTemplate());
            }
        }
    };

    $(document).ready(function () {
        orgForm.init();
    });
});
//# sourceMappingURL=maps/governments-edit.js.map
