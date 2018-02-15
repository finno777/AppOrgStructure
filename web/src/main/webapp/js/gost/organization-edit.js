/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/tinymce', 'SHARED/gostFileManager'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    var collectPhones = function collectPhones(orgId) {
        return $('#groupForm__phones .subgroup').reduce(function (prev, item) {
            var val = $.trim($('.subgroup__input', item).val());
            var id = +$('.subgroup__input', item).data('id');

            if (val.length) {
                prev.push({
                    id: id && !isNaN(id) ? id : null,
                    phone: val,
                    organization: orgId ? { id: orgId } : null
                });
            }

            return prev;
        }, []);
    };

    var getSubgroup = function getSubgroup() {
        var item = $('#orgForm__typeOrganization option:selected');
        if (!item || !item.val().length || isNaN(+item.val())) {
            return null;
        }

        return +item.val();
    };

    var buildLink = function buildLink(name, handler) {
        var btn = $('<li class="subgroup__menu-item"><a href="#">' + name + '</a></li>');
        btn.on('click', handler);
        return btn;
    };

    var phoneTemplate = function phoneTemplate(val, id) {
        var tpl = $('\n            <div class="subgroup">\n                <input type="text" class="subgroup__input" value="' + (val || '') + '" data-id="' + id + '">\n                <button class="subgroup__button" type="button">\n                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + managementApp.cr + 'sprite/sprite.svg#settings"></use></svg>\n                </button>\n                <ul class="subgroup__menu"></ul>\n            </div>\n        ');

        var container = tpl.find('.subgroup__menu');

        tpl.find('.subgroup__button').on('click', function (e) {
            e.stopPropagation();
            container.toggleClass('subgroup__menu--active');
        });

        buildLink('Удалить', function (e) {
            e.preventDefault();
            console.log('remove');
            tpl.remove();
        }).appendTo(container);

        return tpl;
    };

    var validate = function validate() {
        var errors = [];

        if (!$.trim($('#orgForm__title').val()).length) {
            errors.push('Полное наименование');
        }

        if (!$.trim($('#orgForm__titleShort').val()).length) {
            errors.push('Краткое наименование');
        }

        if (!getSubgroup()) {
            errors.push('Подгруппа организаций');
        }

        // if(!$.trim($('#icon').html()).length) {
        //     errors.push('Иконка');
        // }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    $(document).on('click', '#addPhone', function (e) {
        e.preventDefault();
        $('#groupForm__phones').prepend(phoneTemplate());
    });

    $(document).ready(function () {

        if ($(byData('phone')).length) {
            $(byData('phone')).each(function () {
                $(this).replaceWith(phoneTemplate($(this).data('phone'), $(this).data('id')));
            });
        } else {
            $('#groupForm__phones').prepend(phoneTemplate());
        }

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

        $('#orgForm').on('submit', function (e) {
            e.preventDefault();
            if (!validate()) {
                return;
            }

            var id = +$.trim($('#typeForm__id').val());

            var data = {
                id: !!id && !isNaN(id) ? id : null,
                title: $.trim($('#orgForm__title').val()),
                titleShort: $.trim($('#orgForm__titleShort').val()),
                description: $.trim(tinymce.get('orgForm__description').getContent()),
                phones: collectPhones(!!id && !isNaN(id) ? id : null),
                address: $.trim($('#orgForm__address').val()),
                addressActual: $.trim($('#orgForm__addressActual').val()),
                fax: $.trim($('#orgForm__fax').val()),
                mail: $.trim($('#orgForm__email').val()),
                site: $.trim($('#orgForm__site').val()),
                provision: $.trim(tinymce.get('orgForm__provision').getContent()),
                subGroupOrganizations: {
                    id: getSubgroup()
                },
                uuid: function () {
                    var ico = $('#icon img');

                    return ico.length ? ico.attr('src') : '0';
                }(),
                deleted: false
            };

            preloader.show();
            window.dataService.saveOrganization(data).then(function (res) {
                preloader.hide();
                location = managementApp.links.redirect;
            }).catch(function (err) {
                console.error(err);
                preloader.hide();
            });
        });
    });
});
//# sourceMappingURL=maps/organization-edit.js.map
