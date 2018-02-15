/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/gostFileManager', 'SHARED/gostValidation'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    var buildLink = function buildLink(name, handler) {
        var btn = $('<li class="subgroup__menu-item"><a href="#">' + name + '</a></li>');
        btn.on('click', handler);
        return btn;
    };

    var subgroupTemplate = function subgroupTemplate(subgroup) {
        var tpl = $('\n            <div class="subgroup">\n                <input type="text" class="subgroup__input" value="' + (subgroup ? subgroup.title : '') + '" data-subgroup-id="' + (subgroup ? subgroup.id : '') + '">\n                <button class="subgroup__button" type="button">\n                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + managementApp.cr + 'sprite/sprite.svg#settings"></use></svg>\n                </button>\n                <ul class="subgroup__menu"></ul>\n            </div>\n        ');

        var container = tpl.find('.subgroup__menu');

        tpl.find('.subgroup__button').on('click', function (e) {
            e.stopPropagation();
            container.toggleClass('subgroup__menu--active');
        });

        buildLink('Удалить', function (e) {
            e.preventDefault();
            tpl.remove();
        }).appendTo(container);

        return tpl;
    };

    var getTypeOrganization = function getTypeOrganization() {
        var item = $('#groupForm__typeOrganization option:selected');
        if (!item || !item.val().length || isNaN(+item.val())) {
            return null;
        }

        return +item.val();
    };

    var collectSubgroups = function collectSubgroups(groupid) {
        return $('#groupForm__subgroups .subgroup').reduce(function (prev, item) {
            var $input = $('.subgroup__input', item);
            var val = $.trim($input.val());
            var id = $input.data('subgroup-id');

            if (val.length) {
                prev.push({
                    deleted: false,
                    title: val,
                    id: id ? +id : null,
                    group: groupid ? {
                        id: groupid
                    } : null
                });
            }

            return prev;
        }, []);
    };

    var validator = {
        required: function required() {
            return { validator: function validator(val) {
                    return !!val.trim().length;
                }, message: 'Не заполнено обязательное поле' };
        },
        maxlength: function maxlength(max) {
            return { validator: function validator(val) {
                    return val.trim().length <= max;
                }, message: '\u041F\u0440\u0435\u0432\u044B\u0448\u0430\u0435\u0442 ' + max + ' \u0441\u0438\u043C\u0432\u043E\u043B\u043E\u0432' };
        }
    };

    var rules = {
        title: [validator.required(), validator.maxlength(248)],
        titleShort: [validator.required(), validator.maxlength(64)]

    };

    var validate = function validate() {
        var errors = [];

        ;['title', 'titleShort'].forEach(function (prop) {
            if (rules[prop].some(function (x) {
                if (x.validator($.trim($('#groupForm__' + prop).val()))) return false;

                gostValidation.setFieldInvalid($(byData('prop-name', prop)), x.message);
                return true;
            })) {
                errors.push($(byData('key'), byData('prop-name', prop)).data('key'));
            }
        });

        if (!getTypeOrganization()) {
            errors.push($(byData('key'), byData('prop-name', 'typeOrganization')).data('key'));
            gostValidation.setFieldInvalid($(byData('prop-name', 'typeOrganization')), validator.required().message);
        }

        if ($.trim(tinymce.get('groupForm__description').getContent()).length > 8192) {
            errors.push($(byData('key'), byData('prop-name', 'description')).data('key'));
            gostValidation.setFieldInvalid($(byData('prop-name', 'description')), validator.maxlength(8192).message);
        }

        // if(!$.trim($('#icon').html()).length) {
        //     errors.push('Иконка');
        // }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    //#region

    var orgFormAttachs = {
        IMAGE: [],
        DOC: []
    };

    var transformToAttachmentsModel = function transformToAttachmentsModel(file, isPreview) {
        return {
            id: null,
            preview: isPreview === 'true',
            extName: file.extension,
            fileName: file.displayName || file.fileName,
            size: file.size,
            type: file.type,
            uniqID: file.uniqID,
            uuid: file.uuid
        };
    };

    var resetPreview = function resetPreview() {
        orgFormAttachs.IMAGE = orgFormAttachs.IMAGE.filter(function (item) {
            if (item.preview === true && item.id) {
                $.ajax({
                    headers: {
                        'gtn.access.appid': managementApp.application.secret_app_id
                    },
                    url: managementApp.cr + '/file/removeAttachment.action?id=' + item.id,
                    type: 'POST',
                    processData: false,
                    contentType: false
                });
            }
            return item.preview !== true;
        });
    };

    var renderPreview = function renderPreview($previewContainer, fileInfo) {
        switch (fileInfo.type) {
            case 'IMAGE':
                if (fileInfo.preview) {
                    $previewContainer.empty();
                }
                $previewContainer.append('\n                        <div class="preview-item" data-extension="' + fileInfo.extName + '" data-filename="' + fileInfo.fileName + '" data-size="' + fileInfo.size + '" data-uniqid="' + fileInfo.uniqID + '" data-id="' + fileInfo.id + '" data-uuid="' + fileInfo.uuid + '" data-type="' + fileInfo.type + '" data-path="' + managementApp.cr + '/image-api/view.ido?uuid=' + fileInfo.uuid + '">\n                            <img src="' + managementApp.cr + '/image-api/view.ido?uuid=' + fileInfo.uuid + '" class="preview-item__img">\n                            <div class="preview-item__remove-btn">\n                                <svg>\n                                     <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + managementApp.cr + '/sprite/sprite.svg#icon-close"></use>\n                                </svg>\n                            </div>\n                        </div>\n                 ');
                break;
            default:
                $previewContainer.append('\n                        <div class="preview-item preview-item--doc" data-extension="' + fileInfo.extName + '" data-filename="' + fileInfo.fileName + '" data-uniqid="' + fileInfo.uniqID + '" data-size="' + fileInfo.size + '" data-id="' + fileInfo.id + '" data-uuid="' + fileInfo.uuid + '" data-type="' + fileInfo.type + '" data-path="' + managementApp.cr + '/image-doc/view.ido?uuid=' + fileInfo.uuid + '">\n                            <div class="preview-item__doc-pic">\n                                <svg>\n                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + managementApp.cr + '/sprite/sprite.svg#' + fileInfo.extName.toLowerCase() + '"></use>\n                                </svg>\n                            </div>\n                            <span class="preview-item__doc">' + fileInfo.fileName + '</span>\n                            <div class="preview-item__remove-btn preview-item__remove-btn--transparent">\n                                <svg>\n                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="' + managementApp.cr + '/sprite/sprite.svg#icon-close"></use>\n                                </svg>\n                            </div>\n                        </div>\n                    ');
                break;
        }
    };

    var bytesToSize = function bytesToSize(bytes) {
        var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        if (bytes === 0) return '0 Byte';
        var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
        return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
    };

    var collectFiles = function collectFiles() {
        var attachments = [];

        $('.preview-item').each(function () {
            attachments.push({
                extName: $(this).data('extension'),
                fileName: $(this).data('filename'),
                size: bytesToSize($(this).data('size')),
                uniqID: $(this).data('uniqid'),
                type: $(this).data('type'),
                uuid: $(this).data('uuid'),
                path: $(this).data('type') === 'IMAGE' ? managementApp.cr + '/image-api/view.ido?uuid=' + $(this).data('uuid') : managementApp.cr + '/doc-api/view.ido?uuid=' + $(this).data('uuid')
            });
        });

        return attachments;
    };

    if (managementApp.subgroups) {
        managementApp.subgroups.forEach(function (item) {
            $('#groupForm__subgroups').prepend(subgroupTemplate(item));
        });
    } else {
        $('#groupForm__subgroups').prepend(subgroupTemplate());
    }

    if (managementApp.attachments) {
        managementApp.attachments.forEach(function (attach) {
            orgFormAttachs.DOC.push(attach);
            renderPreview($('#files'), attach);
        });
    }

    $(document).on('click', '#addSubgroup', function (e) {
        e.preventDefault();
        $('#groupForm__subgroups').prepend(subgroupTemplate());
    });

    $(document).on('click', '#addFile', function (e) {
        e.preventDefault();

        var $previewContainer = $('#files');
        var $fileField = $(this);
        var isPreviewField = $fileField.attr('data-preview');
        var allowedFormats = $fileField.attr('data-allowedFormats').split(', ');
        var maxFiles = +$fileField.attr('data-maxFileCount');

        var options = {
            appId: managementApp.appId,
            portalName: managementApp.portalName,
            appName: managementApp.appName,
            allowedFileFormats: allowedFormats,
            constraints: {
                // max files count in FileManager is 20
                maxFileCount: maxFiles && maxFiles <= 20 ? maxFiles : 20
            },
            onClose: function close() {
                var selectedFiles = RSTFileManager.getFiles();

                selectedFiles.forEach(function (file) {
                    var attach = transformToAttachmentsModel(file, isPreviewField);
                    if (isPreviewField === 'true') {
                        resetPreview();
                    }
                    orgFormAttachs.DOC.push(attach);
                    renderPreview($previewContainer, attach);
                });
            }
        };
        RSTFileManager.open(options);
    });

    $(document).on('click', '.preview-item__remove-btn', function () {
        $(this).parents('.preview-item').remove();
    });

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

    //#endregion

    $('#groupForm').on('submit', function (e) {
        e.preventDefault();

        gostValidation.setAllValid();

        if (!validate()) {
            gostValidation.scrollToFirstError();
            return;
        }

        var id = +$.trim($('#groupForm__id').val());

        var data = {
            id: id && !isNaN(id) ? id : null,
            title: $.trim($('#groupForm__title').val()),
            titleShort: $.trim($('#groupForm__titleShort').val()),
            typeOrganization: {
                id: getTypeOrganization()
            },
            description: $.trim(tinymce.get('groupForm__description').getContent()),
            subGroups: collectSubgroups(id),
            uuid: function () {
                var ico = $('#icon img');

                return ico.length ? ico.attr('src') : '0';
            }(),
            deleted: false,
            attachments: collectFiles()
        };

        preloader.show();
        window.dataService.saveGroupOrganizations(data).then(function (res) {
            preloader.hide();
            location = managementApp.links.redirect;
        }).catch(function (err) {
            console.error(err);
            preloader.hide();
        });
    });

    $('#preloader').remove();
});
//# sourceMappingURL=maps/group-edit.js.map
