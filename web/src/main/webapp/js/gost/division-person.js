/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/gostFileManager'], function ($) {
    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };
    var preloader = function () {
        return $('<div class="preloader" style="display: none;"/>').appendTo('body');
    }();

    var validate = function validate() {
        var errors = [];

        if (!$.trim($('#personForm__name').val()).length) {
            errors.push('ФИО');
        }

        if (!$.trim($('#personForm__position').val()).length) {
            errors.push('Должность');
        }

        if (!$.trim($('#personForm__phone').val()).length) {
            errors.push('Телефон');
        }

        if (!$.trim($('#personForm__email').val()).length) {
            errors.push('E-mail');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    var orgFormAttachs = {
        IMAGE: [],
        DOC: []
    };

    var transformToAttachmentsModel = function transformToAttachmentsModel(file, isPreview) {
        return {
            id: null,
            preview: isPreview === 'true',
            extName: file.extension,
            fileName: file.fileName,
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

    var debounce = function debounce(func, wait, immediate) {
        var timeout;

        return function () {
            var context = this,
                args = arguments;
            var later = function later() {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    };

    var selectedPerson = null;

    var selectPerson = function selectPerson(person) {
        console.log('selected', person);
        selectedPerson = person;
        $('#personForm__name').val(person.name);
        $('#personForm__phone').val(person.phone);
        $('#personForm__email').val(person.email);
        $('#personForm__personId').val(person.id);
        if (person.attachments && person.attachments.length) {
            resetPreview();
            renderPreview($('#photo'), person.attachments[person.attachments.length - 1]);
        }
    };

    var personTemplateDropdown = function personTemplateDropdown(item) {
        var tpl = $('<li><span>' + item.name + '</span></li>');
        tpl.find('span').on('click', function (e) {
            console.log('click');
            e.stopPropagation();
            selectPerson(item);
            $(byData('search-list')).hide();
        });
        return tpl;
    };

    var search = debounce(function () {
        var val = $('#personForm__name').val();
        var list = $(byData('search-list'));
        var loader = $(byData('search-preloader'));

        loader.show();

        window.dataService.getPersons({
            fio: val,
            roles: ['MANAGER']
        }).then(function (res) {
            list.empty();
            res.forEach(function (item) {
                list.append(personTemplateDropdown(item));
            });
            list.show();
            loader.hide();
        }).catch(function () {
            loader.hide();
        });
    }, 500);

    $(document).on('click', '#addFile', function (e) {
        e.preventDefault();

        var $previewContainer = $('#photo');
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
                console.log(selectedFiles);

                selectedFiles.forEach(function (file) {
                    var attach = transformToAttachmentsModel(file, isPreviewField);
                    if (isPreviewField === 'true') {
                        resetPreview();
                    }
                    orgFormAttachs[attach.type].push(attach);
                    renderPreview($previewContainer, attach);
                });
            }
        };
        RSTFileManager.open(options);
    });

    $(document).ready(function () {

        $(document).on('click', function (e) {
            $(byData('search-list')).not(function () {
                return $(e.target).parents(byData('search-prevent')).length;
            }).hide();
        });

        $(byData('search-arrow')).on('click', function () {
            if (!$.trim($('#personForm__name').val()).length) {
                search();
                return;
            }
            $(byData('search-list')).toggle();
        });
        $('#personForm__name').on({
            keydown: function keydown() {
                search();
            }
        });

        $('#personForm').on('submit', function (e) {
            e.preventDefault();
            if (!validate()) {
                return;
            }

            var id = +$('#personForm__id').val();
            var divisionId = +$('#personForm__divisionId').val();
            var personId = +$('#personForm__personId').val();

            var data = {
                id: !!id && !isNaN(id) ? id : null,
                position: $.trim($('#personForm__position').val()),
                division: {
                    id: !!divisionId && !isNaN(divisionId) ? divisionId : null
                },
                person: $.extend(selectedPerson || {}, {
                    id: !!personId && !isNaN(personId) ? personId : null,
                    name: $.trim($('#personForm__name').val()),
                    phone: $.trim($('#personForm__phone').val()),
                    email: $.trim($('#personForm__email').val())
                })
            };

            data.person.attachments = collectFiles();

            preloader.show();
            window.dataService.saveDivisionPerson(data).then(function (res) {
                preloader.hide();
                location = managementApp.links.division;
            }).catch(function (err) {
                console.error(err);
                preloader.hide();
            });
        });
    });
});
//# sourceMappingURL=maps/division-person.js.map
