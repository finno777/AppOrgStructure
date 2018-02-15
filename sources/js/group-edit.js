/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/gostFileManager', 'SHARED/gostValidation'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    const buildLink = (name, handler) => {
        const btn = $(`<li class="subgroup__menu-item"><a href="#">${name}</a></li>`);
        btn.on('click', handler);
        return btn;
    };

    const subgroupTemplate = (subgroup) => {
        const tpl = $(`
            <div class="subgroup">
                <input type="text" class="subgroup__input" value="${subgroup ? subgroup.title : ''}" data-subgroup-id="${subgroup ? subgroup.id : ''}">
                <button class="subgroup__button" type="button">
                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${managementApp.cr}sprite/sprite.svg#settings"></use></svg>
                </button>
                <ul class="subgroup__menu"></ul>
            </div>
        `);

        const container = tpl.find('.subgroup__menu');

        tpl.find('.subgroup__button').on('click', function(e) {
            e.stopPropagation();
            container.toggleClass('subgroup__menu--active');
        });


        buildLink('Удалить', e => {
            e.preventDefault();
            tpl.remove();
        }).appendTo(container);

        return tpl;
    };

    const getTypeOrganization = () => {
        const item = $('#groupForm__typeOrganization option:selected');
        if(!item || !item.val().length || isNaN(+item.val())) {
            return null;
        }

        return +item.val();
    }

    const collectSubgroups = (groupid) => $('#groupForm__subgroups .subgroup').reduce((prev, item) => {
        const $input = $('.subgroup__input', item);
        const val = $.trim($input.val());
        const id = $input.data('subgroup-id');

        if(val.length) {
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

    const validator = {
        required: () => ({ validator: val => !!val.trim().length, message: 'Не заполнено обязательное поле' }),
        maxlength: max => ({ validator: val => val.trim().length <= max, message: `Превышает ${max} символов` }),
    }

    const rules = {
        title: [
            validator.required(),
            validator.maxlength(248)
        ],
        titleShort: [
            validator.required(),
            validator.maxlength(64)
        ],
        
    }

    const validate = () => {
        const errors = [];

        ;['title', 'titleShort'].forEach(prop => {
            if(rules[prop].some(x => {
                if(x.validator($.trim($(`#groupForm__${prop}`).val()))) return false;

                gostValidation.setFieldInvalid($(byData('prop-name', prop)), x.message);
                return true;
            })) {
                errors.push($(byData('key'), byData('prop-name', prop)).data('key'));
            }
        });

        if(!getTypeOrganization()) {
            errors.push($(byData('key'), byData('prop-name', 'typeOrganization')).data('key'));
            gostValidation.setFieldInvalid($(byData('prop-name', 'typeOrganization')), validator.required().message);
        }
        
        if($.trim(tinymce.get('groupForm__description').getContent()).length > 8192) {
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

    const orgFormAttachs = {
        IMAGE: [],
        DOC: [],
    };

    const transformToAttachmentsModel = (file, isPreview) => ({
        id: null,
        preview: isPreview === 'true',
        extName: file.extension,
        fileName: file.displayName || file.fileName,
        size: file.size,
        type: file.type,
        uniqID: file.uniqID,
        uuid: file.uuid,
    });

    const resetPreview = () => {
        orgFormAttachs.IMAGE = orgFormAttachs.IMAGE.filter(function (item) {
            if (item.preview === true && item.id) {
                $.ajax({
                    headers: {
                        'gtn.access.appid': managementApp.application.secret_app_id
                    },
                    url: `${managementApp.cr}/file/removeAttachment.action?id=${item.id}`,
                    type: 'POST',
                    processData: false,
                    contentType: false
                });
            }
            return item.preview !== true;
        });
    }

    const renderPreview = ($previewContainer, fileInfo) => {
        switch (fileInfo.type) {
            case 'IMAGE':
                if (fileInfo.preview) {
                    $previewContainer.empty();
                }
                $previewContainer.append(`
                        <div class="preview-item" data-extension="${fileInfo.extName}" data-filename="${fileInfo.fileName}" data-size="${fileInfo.size}" data-uniqid="${fileInfo.uniqID}" data-id="${fileInfo.id}" data-uuid="${fileInfo.uuid}" data-type="${fileInfo.type}" data-path="${managementApp.cr}/image-api/view.ido?uuid=${fileInfo.uuid}">
                            <img src="${managementApp.cr}/image-api/view.ido?uuid=${fileInfo.uuid}" class="preview-item__img">
                            <div class="preview-item__remove-btn">
                                <svg>
                                     <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${managementApp.cr}/sprite/sprite.svg#icon-close"></use>
                                </svg>
                            </div>
                        </div>
                 `);
                break;
            default:
                $previewContainer.append(`
                        <div class="preview-item preview-item--doc" data-extension="${fileInfo.extName}" data-filename="${fileInfo.fileName}" data-uniqid="${fileInfo.uniqID}" data-size="${fileInfo.size}" data-id="${fileInfo.id}" data-uuid="${fileInfo.uuid}" data-type="${fileInfo.type}" data-path="${managementApp.cr}/image-doc/view.ido?uuid=${fileInfo.uuid}">
                            <div class="preview-item__doc-pic">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${managementApp.cr}/sprite/sprite.svg#${fileInfo.extName.toLowerCase()}"></use>
                                </svg>
                            </div>
                            <span class="preview-item__doc">${fileInfo.fileName}</span>
                            <div class="preview-item__remove-btn preview-item__remove-btn--transparent">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${managementApp.cr}/sprite/sprite.svg#icon-close"></use>
                                </svg>
                            </div>
                        </div>
                    `);
                break;
        }
    };

    const bytesToSize = (bytes) => {
        let sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
        if (bytes === 0) return '0 Byte';
        let i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
        return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
    }

    const collectFiles = () => {
        const attachments = [];

        $('.preview-item').each(function () {
            attachments.push({
                extName: $(this).data('extension'),
                fileName: $(this).data('filename'),
                size: bytesToSize($(this).data('size')),
                uniqID: $(this).data('uniqid'),
                type: $(this).data('type'),
                uuid: $(this).data('uuid'),
                path: $(this).data('type') === 'IMAGE' ? `${managementApp.cr}/image-api/view.ido?uuid=${$(this).data('uuid')}` : `${managementApp.cr}/doc-api/view.ido?uuid=${$(this).data('uuid')}`
            });
        });

        return attachments;
    }

    if(managementApp.subgroups) {
        managementApp.subgroups.forEach(item => {
            $('#groupForm__subgroups').prepend(subgroupTemplate(item));
        });
    } else {
        $('#groupForm__subgroups').prepend(subgroupTemplate());
    }
    
    if(managementApp.attachments) {
        managementApp.attachments.forEach(function (attach) {
            orgFormAttachs.DOC.push(attach);
            renderPreview($('#files'), attach);
        });
    }


    $(document).on('click', '#addSubgroup', (e) => {
        e.preventDefault();
        $('#groupForm__subgroups').prepend(subgroupTemplate());
    });

    $(document).on('click', '#addFile',function (e) {
        e.preventDefault();
        
        const $previewContainer = $('#files');
        const $fileField = $(this);
        const isPreviewField = $fileField.attr('data-preview');
        const allowedFormats = $fileField.attr('data-allowedFormats').split(', ');
        const maxFiles = +$fileField.attr('data-maxFileCount');

        const options = {
            appId: managementApp.appId,
            portalName: managementApp.portalName,
            appName: managementApp.appName,
            allowedFileFormats: allowedFormats,
            constraints: {
                // max files count in FileManager is 20
                maxFileCount: maxFiles && maxFiles <= 20 ? maxFiles : 20,
            },
            onClose: function close() {
                const selectedFiles = RSTFileManager.getFiles();

                selectedFiles.forEach(function (file) {
                    const attach = transformToAttachmentsModel(file, isPreviewField);
                    if (isPreviewField === 'true') {
                        resetPreview();
                    }
                    orgFormAttachs.DOC.push(attach);
                    renderPreview($previewContainer, attach);
                });
            },
        };
        RSTFileManager.open(options);
    });

    $(document).on('click', '.preview-item__remove-btn', function() {
        $(this).parents('.preview-item').remove();
    });

    $(document).on('click', '#addIcon',function (e) {
        e.preventDefault();
        
        const options = {
            appId: managementApp.appId,
            portalName: managementApp.portalName,
            appName:'IconStore',
            fmType: 'icons',
            allowedFileFormats: ["all"],
            constraints: {
                maxFileCount: 1,
            },
            onClose: function close() {
                $('#icon')
                    .empty()
                    .append($(`<img src="${ RSTFileManager.getFiles()[0].path }" alt="Иконка">`));
            },
        };
        RSTFileManager.open(options);
    });

    //#endregion

    $('#groupForm').on('submit', function (e) {
        e.preventDefault();

        gostValidation.setAllValid();
        
        if(!validate()) {
            gostValidation.scrollToFirstError();
            return;
        }

        const id = +$.trim($('#groupForm__id').val());

        const data = {
            id: id && !isNaN(id) ? id: null,
            title: $.trim($('#groupForm__title').val()),
            titleShort: $.trim($('#groupForm__titleShort').val()),
            typeOrganization: {
                id: getTypeOrganization()
            },
            description: $.trim(tinymce.get('groupForm__description').getContent()),
            subGroups: collectSubgroups(id),
            uuid: (() => {
                const ico = $('#icon img');

                return ico.length ? ico.attr('src') : '0';
            })(),
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
