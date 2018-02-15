/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/gostFileManager'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    const validate = () => {
        const errors = [];

        if(!$.trim($('#personForm__name').val()).length) {
            errors.push('ФИО');
        }

        if(!$.trim($('#personForm__position').val()).length) {
            errors.push('Должность');
        }

        if(!$.trim($('#personForm__phone').val()).length) {
            errors.push('Телефон');
        }

        if(!$.trim($('#personForm__email').val()).length) {
            errors.push('E-mail');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    const orgFormAttachs = {
        IMAGE: [],
        DOC: [],
    };

    const transformToAttachmentsModel = (file, isPreview) => ({
        id: null,
        preview: isPreview === 'true',
        extName: file.extension,
        fileName: file.fileName,
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
                break;
        }
    };

    $(document).on('click', '#addFile',function (e) {
        e.preventDefault();
        
        const $previewContainer = $('#photo');
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
                console.log(selectedFiles);

                selectedFiles.forEach(function (file) {
                    const attach = transformToAttachmentsModel(file, isPreviewField);
                    if (isPreviewField === 'true') {
                        resetPreview();
                    }
                    orgFormAttachs[attach.type].push(attach);
                    renderPreview($previewContainer, attach);
                });
            },
        };
        RSTFileManager.open(options);
    });

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

    const debounce = (func, wait, immediate) => {
        var timeout;
        
        return function() {
            var context = this, args = arguments;
            var later = function() {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    }

    let selectedPerson = null;

    const selectPerson = (person) => {
        console.log('selected', person);
        selectedPerson = person;
        $('#personForm__name').val(person.name);
        $('#personForm__phone').val(person.phone);
        $('#personForm__email').val(person.email);
        $('#personForm__personId').val(person.id);
        if(person.attachments && person.attachments.length) {
            resetPreview();
            renderPreview($('#photo'), person.attachments[person.attachments.length - 1]);
        }
    }

    const personTemplateDropdown = (item) => {
        const tpl = $(`<li><span>${item.name}</span></li>`);
        tpl.find('span').on('click', (e) => {
            console.log('click');
            e.stopPropagation();
            selectPerson(item);
            $(byData('search-list')).hide();
        });
        return tpl;
    };

    const search = debounce(() => {
        const val = $('#personForm__name').val();
        const list = $(byData('search-list'));
        const loader = $(byData('search-preloader'));

        loader.show();
    
        window.dataService.getPersons({
            fio: val,
            roles: [ 'MANAGER' ]
        }).then((res) => {
            list.empty();
            res.forEach((item) => {
                list.append(personTemplateDropdown(item));
            });
            list.show();
            loader.hide();
        }).catch(() => {
            loader.hide();
        });
    }, 500);

    $(document).ready(function() {
        $(document).on('click', (e) => {
            $(byData('search-list'))
                .not(function() {
                    return $(e.target).parents(byData('search-prevent')).length;
                }).hide();
        });

        $(byData('search-arrow')).on('click', function() {
            if(!$.trim($('#personForm__name').val()).length) {
                search();
                return;
            }
            $(byData('search-list')).toggle();
        });

        $('#personForm__name').on({
            keydown() {
                search();
            }
        });

        $('#personForm').on('submit', function(e) {
            e.preventDefault();
            if(!validate()) {
                return;
            }

            const orgId = +$('#personForm__organizationId').val();
            const id = +$('#personForm__id').val();
            const personId = +$('#personForm__personId').val();

            const data = {
                id: !!id && !isNaN(id) ? id : null,
                position: $.trim($('#personForm__position').val()),
                organization: {
                    id: !!orgId &&  !isNaN(orgId) ? orgId : null,
                },
                person: $.extend(selectedPerson || {}, {
                    id: !!personId && !isNaN(personId) ? personId : null,
                    name: $.trim($('#personForm__name').val()),
                    phone: $.trim($('#personForm__phone').val()),
                    email: $.trim($('#personForm__email').val())
                }),
                
            };

            data.person.attachments = collectFiles();

            preloader.show();
            window.dataService.saveOrganizationPerson(data)
                .then((res) => {
                    preloader.hide();
                    location = managementApp.links.organization;
                }).catch((err) => {
                    console.error(err);
                    preloader.hide();
                });
            
        });
    });
});
