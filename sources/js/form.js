/* eslint-disable */
/* global orgStruct, tinymce, $, gostValidation */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/gostDatepicker', 'SHARED/tinymce', 'SHARED/gostValidation', 'SHARED/gostFileManager'], $ => {
    const $form = $('#org-form');
    const $formFooterErrors = $form.find('.js-formFooter-errors');
    let cr = orgStruct.cr;
    let dataSet = {};
    let dataLength = {};
    let personId = orgStruct.personId ? orgStruct.personId : null;
    let organId = orgStruct.organId ? orgStruct.organId : null;
    let emptyFields = [];
    const orgFormAttachs = {
        IMAGE: [],
        DOC: [],
    };

    let fieldsValue = orgStruct.organ ? {} : {
        attributes: [],
        attachments: [],
        curatorGovernments: []
    };

    const orgForm = {
        checkInput: function () {
            const that = this;
            $formFooterErrors.text('');
            emptyFields = [];
            fieldsValue = Object.assign({}, orgStruct.organ ? {} : {
                role: orgStruct.role,
                attributes: [],
                attachments: [],
                curatorGovernments: []
            }, orgStruct.member ? {
                attachments: [],
                organId: +organId
            } : {});

            if (personId) {
                fieldsValue['id'] = personId
            } else if (orgStruct.organ && organId) {
                fieldsValue['id'] = organId
            }

            if (orgStruct.role === 'ADVISER') {
                fieldsValue.attributes.push({
                    'key': 'person',
                    'value': orgStruct.managerId
                });
            }

            gostValidation.setAllValid();

            $form.find('[data-required]').each(function () {
                if ($(this).find('select').length !== 0) {
                    if ($(this).find('option:selected').attr('disabled') !== 'disabled') {
                        dataSet[$(this).data('prop-name')] = $(this).find('select').val();
                    } else {
                        dataSet[$(this).data('prop-name')] = '';
                        emptyFields.push($(this).find('select').data('key'));
                    }
                } else if ($(this).is('[data-attach]')) {
                    if ($(this).next().children().length !== 0) {
                        dataSet[$(this).data('prop-name')] = true;
                    } else {
                        dataSet[$(this).data('prop-name')] = '';
                        emptyFields.push($(this).data('key'));
                    }
                } else {
                    dataSet[$(this).data('prop-name')] = $(this).find('.form-field').val();

                    if ($(this).find('.form-field').val() === '') {
                        emptyFields.push($(this).find('.form-field').data('key'));
                    }
                }
            });

            $form.find('[data-max-length]').each(function () {
                if ($(this).find('.js-tiny-editor').length !== 0) {
                    dataLength[$(this).data('prop-name')] = tinymce.get($(this).find('.js-tiny-editor').attr('id')).getBody().textContent
                } else {
                    dataLength[$(this).data('prop-name')] = $(this).find('.form-field, .form-attr').val();
                }
            });

            gostValidation.validateRequiredValues(dataSet);
            gostValidation.validateValuesLength(dataLength);

            $form.find('.form-field').each(function () {
                let fieldId = $(this).attr('id').substring($(this).attr('id').indexOf('-') + 1);

                if ($(this).val() !== '') {
                    if ($(this).is('select')) {
                        if ($(this).find('option:selected').attr('disabled') !== 'disabled') {
                            fieldsValue[fieldId] = $(this).find('option:selected').text();
                        }
                    } else {
                        if (fieldId === 'phone') {
                            fieldsValue[fieldId] = fieldsValue.phone ? fieldsValue.phone + ', ' + $(this).val() : $(this).val();
                        } else {
                            fieldsValue[fieldId] = $(this).val();
                        }
                    }
                }
            });

            $form.find('.form-attr').each(function () {
                if ($(this).val() !== '') {
                    if ($(this).is('select')) {
                        fieldsValue.attributes.push({
                            'key': $(this).data('key'),
                            'value': $(this).find('option:selected').text()
                        });
                    } else {
                        fieldsValue.attributes.push({
                            'key': $(this).data('key'),
                            'value': $(this).val()
                        });
                    }
                }
            });

            $form.find('.form-attach').each(function () {
                const attachments = [];

                $('.gost-form__preview').each(function () {
                    $(this).find('.preview-item').each(function () {
                        attachments.push({
                            extName: $(this).data('extension'),
                            fileName: $(this).data('filename'),
                            size: that.bytesToSize($(this).data('size')),
                            uniqID: $(this).data('uniqid'),
                            type: $(this).data('type'),
                            uuid: $(this).data('uuid'),
                            path: $(this).data('type') === 'IMAGE' ? `${cr}/image-api/view.ido?uuid=${$(this).data('uuid')}` : `${cr}/doc-api/view.ido?uuid=${$(this).data('uuid')}`
                        });
                    });
                });

                fieldsValue.attachments = attachments;
            });

            $form.find('.form-structure').each(function () {
                let $select = $(this).find('select');
                let $input = $(this).find('input');

                fieldsValue.curatorGovernments.push({
                    'id': null,
                    'description': $input.val() !== '' ? $input.val() : '',
                    'government': {
                        'id': $select.find('option:selected').val()
                    }
                });
            });

            $form.find('.js-tiny-editor').each(function () {
                let content = tinymce.get($(this).attr('id')).getContent();
                let fieldId = $(this).attr('id').substring($(this).attr('id').indexOf('-') + 1);

                if (content !== '') {
                    if (orgStruct.organ) {
                        fieldsValue[fieldId] = content;
                    } else {
                        fieldsValue.attributes.push({
                            'key': $(this).data('key'),
                            'value': content
                        });
                    }
                }
            });

            $formFooterErrors.text(emptyFields.join(', '));
        },


        addFiles: function () {
            const that = this;

            $(document).on('click', '.js-add-file', function () {
                const $fileField = $(this);
                const $previewContainer = $fileField.parents('.gost-form__section').find('.gost-form__preview');
                const isPreviewField = $fileField.attr('data-preview');
                const allowedFormats = $fileField.attr('data-allowedFormats').split(', ');
                const maxFiles = +$fileField.attr('data-maxFileCount');

                const options = {
                    appId: orgStruct.appId,
                    portalName: orgStruct.portalName,
                    appName: orgStruct.appName,
                    allowedFileFormats: allowedFormats,
                    constraints: {
                        // max files count in FileManager is 20
                        maxFileCount: maxFiles && maxFiles <= 20 ? maxFiles : 20,
                    },
                    onClose: function close() {
                        const selectedFiles = RSTFileManager.getFiles();
                        selectedFiles.forEach(function (file) {
                            const attach = that.transformToAttachmentsModel(file, isPreviewField);
                            if (isPreviewField === 'true') {
                                that.resetPreview();
                            }
                            orgFormAttachs[attach.type].push(attach);
                            that.renderPreview($previewContainer, attach);
                        });
                    },
                };
                RSTFileManager.open(options);
            });
        },

        bytesToSize: function (bytes) {
            let sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
            if (bytes === 0) return '0 Byte';
            let i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
            return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
        },

        transformToAttachmentsModel: function (file, isPreview) {
            const attachment = {
                id: null,
                preview: isPreview === 'true',
                fileName: file.displayName || file.fileName,
                extName: file.extension,
                size: file.size,
                type: file.type,
                uniqID: file.uniqID,
                uuid: file.uuid,
            };

            return attachment;
        },

        resetPreview: function () {
            orgFormAttachs.IMAGE = orgFormAttachs.IMAGE.filter(function (item) {
                if (item.preview === true && item.id) {
                    $.ajax({
                        headers: orgStruct.adminsHeaders,
                        url: `${cr}/file/removeAttachment.action?id=${item.id}`,
                        type: 'POST',
                        processData: false,
                        contentType: false
                    });
                }
                return item.preview !== true;
            });
        },

        renderPreview: function ($previewContainer, fileInfo) {
            switch (fileInfo.type) {
                case 'IMAGE':
                    if (fileInfo.preview) {
                        $previewContainer.empty();
                    }
                    $previewContainer.append(`
                            <div class="preview-item" data-extension="${fileInfo.extName}" data-filename="${fileInfo.fileName}" data-size="${fileInfo.size}" data-uniqid="${fileInfo.uniqID}" data-id="${fileInfo.id}" data-uuid="${fileInfo.uuid}" data-type="${fileInfo.type}" data-path="${cr}/image-api/view.ido?uuid=${fileInfo.uuid}">
                                <img src="${cr}/image-api/view.ido?uuid=${fileInfo.uuid}" class="preview-item__img">
                                <div class="preview-item__remove-btn">
                                    <svg>
                                         <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#icon-close"></use>
                                    </svg>
                                </div>
                            </div>
                     `);
                    break;
                case 'DOC':
                    $previewContainer.append(`
                            <div class="preview-item preview-item--doc" data-extension="${fileInfo.extName}" data-filename="${fileInfo.fileName}" data-uniqid="${fileInfo.uniqID}" data-size="${fileInfo.size}" data-id="${fileInfo.id}" data-uuid="${fileInfo.uuid}" data-type="${fileInfo.type}" data-path="${cr}/image-doc/view.ido?uuid=${fileInfo.uuid}">
                                <div class="preview-item__doc-pic">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#${fileInfo.extName.toLowerCase()}"></use>
                                    </svg>
                                </div>
                                <span class="preview-item__doc">${fileInfo.fileName}</span>
                                <div class="preview-item__remove-btn preview-item__remove-btn--transparent">
                                    <svg>
                                          <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#icon-close"></use>
                                       </svg>
                                </div>
                            </div>
                    `);
                    break;
                default:
                    break;
            }
        },

        requestForm: function () {
            $.ajax({
                headers: orgStruct.adminsHeaders,
                url: (() => {
                    const urlFunc = method => `${cr}/rest-api/orgStructure/${method}.action`;
                    if (orgStruct.member) {
                        return urlFunc('savePersonOrgan');
                    }

                    return orgStruct.organ ? urlFunc('saveOrgan') : urlFunc('savePerson');
                })(),
                dataType: 'json',
                type: "POST",
                data: JSON.stringify(fieldsValue),
                statusCode: {
                    200: function () {
                        window.location.href = orgStruct.redirectUrl
                    }
                }
            });
        },

        events: function () {
            let that = this;

            $form.submit(function (e) {
                e.preventDefault();
                that.checkInput();

                if ($form.find('.error-info').length) {
                    gostValidation.scrollToFirstError();
                } else {
                    that.requestForm();
                }
            });

            $(document).on('click', '.preview-item__remove-btn', function () {
                const $fileContainer = $(this).closest('.preview-item');
                const fileType = $fileContainer.attr('data-type');
                const fileID = parseInt($fileContainer.attr('data-id'), 10);
                const fileUID = parseInt($fileContainer.attr('data-uuid'), 10);
                if (fileID) {
                    // $.ajax({
                    //     url: `${cr}/file/uploadFile.action`,
                    //     data: formData,
                    //     type: 'POST',
                    //     beforeSend: request => {
                    //         request.setRequestHeader('gtn.access.appid', orgStruct.adminsHeaders['gtn.access.appid']);
                    //     },
                    //     processData: false,
                    //     contentType: false,
                    //     success: data => {
                    //         orgFormAttachs[fileType] = orgFormAttachs[fileType]
                    //             .filter(function (item) {
                    //                 return item.id !== fileID;
                    //             });
                    //         $fileContainer.remove();
                    //     },
                    //     error: err => {
                    //         console.log(err);
                    //     },
                    // });
                    
                    $fileContainer.remove();
                } else {
                    orgFormAttachs[fileType] = orgFormAttachs[fileType]
                        .filter(function (item) {
                            return item.uuid !== fileUID;
                        });

                    $fileContainer.remove();
                }
            });
        },

        addPhone: function () {
            let that = this;
            const $addPhone = $('.js-add-phone');
            const $addPhoneBtn = $addPhone.find('._btn');

            $addPhoneBtn.on('click', function () {
                $addPhone.parent().append(`<div class="gost-form__value edit-field"><input id="manager${$addPhone.parent().find('.gost-form__value').length}-phone" type="text" class="textbox form-field" value="">${that.renderEditBox("Удалить поле")}</div>`);
            });
        },

        renderEditBox: function (linkText) {
            return `<div class="edit-box"><button class="edit-box__btn" type="button"><svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#icon-settings"></use></svg></button><ul class="edit-box__list"><li class="_item"><button class="_btn danger" type="button">${linkText}</button></li></ul></div>`;
        },

        addStructure: function () {
            let that = this;
            const list = $('.js-governments-list').html();
            const $addStructure = $('.js-add-structure');
            const $addStructureBtn = $addStructure.find('._btn');
            const structureBlockHeader = `<div class="bunch__field"><label class="gost-form__label">Курируемое структурное подразделение</label><div class="gost-form__value edit-field"><select class="js-select" data-key="Курируемое структурное подразделение">${list}</select>${that.renderEditBox("Удалить связку")}</div></div>`;
            const structureBlockBottom = '<div class="bunch__field"><label class="gost-form__label">Описание</label><input type="text" class="textbox" data-key="Описание" value=""></div>';
            const structureBlock = `<div class="gost-form__bunch form-structure">${structureBlockHeader} ${structureBlockBottom}</div>`;

            $addStructureBtn.on('click', function () {
                $addStructure.parent().find('.gost-form__section').append(structureBlock);
                $('.js-select').selectmenu();
            });
        },

        editBox: function () {
            $(document).on('click', '.edit-box__btn', function () {
                if ($(this).hasClass('active')) {
                    $(this).removeClass('active');
                    $(this).next().hide();
                } else {
                    $(this).addClass('active');
                    $(this).next().show();
                }
            });

            $(document).on('click', '.edit-box__list ._btn', function () {
                if ($(this).parents('.gost-form__bunch').length) {
                    $(this).parents('.gost-form__bunch').remove();
                } else {
                    $(this).parents('.edit-field').remove();
                }
            });

            $(document).on('mouseup', e => {
                const $dropdown = $('.edit-box__list');
                if (!$dropdown.is(e.target) && $dropdown.has(e.target).length === 0) {
                    $dropdown.hide();
                }
            });
        },

        init: function () {
            this.events();
            this.addPhone();
            this.addStructure();
            this.editBox();
            this.addFiles();
        }
    };

    const orgApp = {
        tinyInit: function () {
            tinymce.init({
                selector: '.js-tiny-editor',
                menubar: false,
                statusbar: false,
                relative_urls: false,
                image_advtab: true,
                mode: 'textareas',
                external_plugins: {
                    advlist: `${cr}/lib/tinymce/plugins/advlist/plugin.min.js`,
                    lists: `${cr}/lib/tinymce/plugins/lists/plugin.min.js`,
                    link: `${cr}/lib/tinymce/plugins/link/plugin.min.js`,
                    visualchars: `${cr}/lib/tinymce/plugins/visualchars/plugin.min.js`,
                    charmap: `${cr}/lib/tinymce/plugins/charmap/plugin.min.js`,
                    image: `${cr}/lib/tinymce/plugins/image/plugin.min.js`,
                    paste: `${cr}/lib/tinymce/plugins/paste/plugin.min.js`,
                },
                plugins: 'lists advlist link paste',
                toolbar1: `undo redo fontsizeselect | bold italic underline strikethrough | subscript superscript forecolor backcolor | alignleft aligncenter alignright alignjustify  | ' +
                'outdent indent | numlist bullist  | customlink | removeformat | photoupload videoupload fileupload`,
                body_class: 'mce-body',
                language_url: `${cr}/lib/tinymce/langs/ru.js`,
                content_css: `/eXoResources/plugins/tinymce/skins/lightgray/content.min.css?v=${orgStruct.versionUI}, /eXoResources/plugins/tinymce/skins/lightgray/content.RST.css?v=${orgStruct.versionUI}`,
                skin_url: '/eXoResources/plugins/tinymce/skins/lightgray',
                advlist_bullet_styles: 'disc',
                paste_use_dialog: false,
                paste_auto_cleanup_on_paste: true,
                paste_convert_headers_to_strong: false,
                paste_strip_class_attributes: 'all',
                paste_remove_spans: true,
                paste_remove_styles: true,
                paste_retain_style_properties: '',
                style_formats: [
                    { title: 'Headers',
                        items: [
                            { title: 'Заголовок 1', block: 'h2' },
                            { title: 'Заголовок 2', block: 'h3' },
                            { title: 'Заголовок 3', block: 'h4' },
                        ] },
                    { title: 'Blocks',
                        items: [
                            { title: 'Параграф', format: 'p' },
                        ] },
                ],
                setup: function setUp(editor) {
                    editor.addButton('photoupload', {
                        icon: 'image',
                        tooltip: 'Прикрепить фото',
                        onclick() {
                            const options = {
                                appId: orgStruct.appId,
                                portalName: orgStruct.portalName,
                                appName: orgStruct.appName,
                                allowedFileFormats: ['img'],
                                onClose: function close() {
                                    const selectedFiles = RSTFileManager.getFiles();
                                    selectedFiles.forEach(function (file) {
                                        editor.insertContent(`<img src="${cr}/image-api/view.ido?uuid=${file.uuid}" data-uuid="${file.uuid}"/>`);
                                    });
                                },
                            };
                            RSTFileManager.open(options);
                        },
                    });
                    
                    editor.addButton('videoupload', {
                        icon: 'media',
                        tooltip: 'Прикрепить видео',
                        onclick() {
                            const options = {
                                appId: orgStruct.appId,
                                portalName: orgStruct.portalName,
                                appName: orgStruct.appName,
                                allowedFileFormats: ['video'],
                                onClose() {
                                    const selectedFiles = RSTFileManager.getFiles();
                                    selectedFiles.forEach(function (file) {
                                        editor.insertContent(`                                            
                                                        <video width="320" height="240" controls data-uuid="${file.uuid}">
                                                            <source src="${cr}/media-api/view.media?uuid=${file.uuid}" type="video/mp4">
                                                            <source src="${cr}/media-api/view.media?uuid=${file.uuid}" type="video/ogg">
                                                            Your browser does not support the video tag.
                                                         </video>
                                                    `);
                                    });
                                },
                            };
                            RSTFileManager.open(options);
                        },
                    });
    
                    editor.addButton('fileupload', {
                        icon: 'newdocument',
                        tooltip: 'Прикрепить документ',
                        onclick() {
                            const options = {
                                appId: orgStruct.appId,
                                portalName: orgStruct.portalName,
                                appName: orgStruct.appName,
                                allowedFileFormats: ['doc', 'archive', 'text'],
                                onClose() {
                                    const selectedFiles = RSTFileManager.getFiles();
                                    selectedFiles.forEach(function (file) {
                                        editor.insertContent(`
                                            <a href="/documentManager/rest/file/load/${file.uuid}" data-uuid="${file.uuid}">${file.displayName || file.fileName}</a>
                                            <span class="tiny-content__file-size">(${file.extension} , ${(file.size / 10 / 1024 / 1024).toFixed(2)} Мб)</span>
                                        `);
                                    });
                                },
                            };
                            RSTFileManager.open(options);
                        },
                    });
                },
            });
        },

        init: function () {
            this.tinyInit();

            $('.js-select').selectmenu();

            $('.gost-form .datepckr__input').each(function () {
                const $el = $(this);
                const val = $el.data('date') || null;

                if(val) {
                    $el.gostDatepicker();
                    setTimeout(() => {
                        $el.datepicker('setDate', $.datepicker.parseDate( "dd.mm.yy", val ));
                    }, 0);
                } else {
                    $el.gostDatepicker();
                }
            });
        }
    };

    $(document).ready(function () {
        orgApp.init();
        orgForm.init();
    });
});
