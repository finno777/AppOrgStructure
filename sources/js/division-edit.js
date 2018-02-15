/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/tinymce', 'SHARED/gostFileManager'], $ => {
    const managementApp = window.managementApp;
    const cr = managementApp.cr;
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();

    const orgTypes = {
        organizations: 1,
        groupOrganizations: 2,
        subGroupOrganizations: 3
    };

    const validate = () => {
        const errors = [];

        if(!$.trim($('#divisionForm__name').val()).length) {
            errors.push('Название');
        }

        if(!$.trim(tinymce.get('divisionForm__description').getContent()).length) {
            errors.push('Задачи отдела');
        }

        if(!$.trim($('#icon').html()).length) {
            errors.push('Иконка');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    const orgForm = {
        init() {

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

            $('#divisionForm').on('submit', function(e) {
                e.preventDefault();
                if(!validate()) {
                    return;
                }

                const id = +$('#divisionForm__id').val();
                const governmentId = +$('#divisionForm__governmentId').val();

                const data = {
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
                window.dataService.saveDivision(data).then((res) => {
                    preloader.hide();
                    location = managementApp.links.government;
                }).catch(() => {
                    console.error('error');
                    preloader.hide();
                });
                
            });

        }
    }

    $(document).ready(function () {
        orgForm.init();
    });

});
