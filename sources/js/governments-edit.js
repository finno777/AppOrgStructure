/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/tinymce', 'SHARED/gostFileManager'], $ => {
    const managementApp = window.managementApp;
    const cr = managementApp.cr;
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();
    const curatorTemplate = (selected) => `
        <div class="curator">
        
            <div class="curator__border"></div>

            <label class="control__label">ФИО куратора</label>
            <div class="curator__control">
                <div class="curator__control-input">
                <select>
                    <option selected>Не выбрано</option>
                    ${
                        managementApp.curators().reduce((template, item) => {
                            return `${template}<option value="${item.id}" ${selected && item.id === selected.id? 'selected' : ''}>${item.name}</option>`;
                        }, '')
                    }
                </select>
                    <svg class="curator__control-input-arrow"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#arrow_down"></use></svg>
                </div>
                <button class="curator__control-button" type="button">
                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use></svg>
                </button>
                <div class="curator__control-dropdown" style="display: none;">
                    <a href="#" data-curator="remove">Удалить связку</a>
                </div>
            </div>
        
            <label class="control__label">Описание</label>
            <div class="curator__control">
                <div class="curator__control-input">
                    <input type="text" id="curator__description" value="${selected ? selected.description : ''}">
                </div>
            </div>
        </div>
    `;

    const orgTypes = {
        organizations: 1,
        groupOrganizations: 2,
        subGroupOrganizations: 3
    };

    const populateChecklistItems = (groups, orgtype) => groups
        .reduce((template, item) => {
            return `${template}<li><label><input type="checkbox" data-org-type="${orgtype}" value="${item.id}"> <span>${item.title}</span></label></li>`;
        }, '');

    const renderChecklist = (items, id = 'checklist') => `
        <div class="checklist" id="${id}">
            <span class="checklist__title">Не выбрано</span>
            <ul class="checklist__list">
                ${items}
            </ul>
        </div>
    `;

    const collectChecklist = (orgtype) => 
        $(`.checklist input[data-org-type="${orgtype}"]:checked`).reduce((res, item) => {
            res.push({
                id: +$(item).val()
            });
            return res;
        }, []);

    const validate = () => {
        const errors = [];

        if(!$.trim($('#governmentEditForm__title').val()).length) {
            errors.push('Наименования управления');
        }

        if(!$.trim($('#icon').html()).length) {
            errors.push('Иконка');
        }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    const collectCurators = () => {
        const res = [];
        $('.curator').each(function() {
            const $el = $(this);
            const val = $el.find('select').val();
            if(!!+val) {
                const source = managementApp.government ? $.grep(managementApp.government.curators, x => x.id === +val) : [];
                res.push({
                    id: source.length ? source[0].linkId : null,
                    description: $.trim($el.find('#curator__description').val()),
                    person: {
                        id: +val,
                    }
                });
            }
        });
        return res;
    }

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

    const orgForm = {
        bindHandlers() {
            // Handlers
            $('#governmentEditForm').on('submit', function(e) {
                e.preventDefault();
                if(!validate()) {
                    return;
                }

                const data = {
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
                window.dataService.save(data)
                    .then((res) => {
                        preloader.hide();
                        location = managementApp.links.redirectMainURL
                    }).catch((err) => {
                        console.error(err);
                        preloader.hide();
                    });
                
            });

            $(document).on('click', '.curator__control-button', function(e) {
                e.stopImmediatePropagation();
                $(this).next('.curator__control-dropdown').toggle();
            });


            $('#addCurator').on('click', function(e) {
                e.preventDefault();
                $('#curators').prepend(curatorTemplate());
            });

            $(document).on('click', byData('curator', 'remove'), function(e) {
                e.preventDefault();
                const wrapper = $(this).parents('.curator');
                const id = +$('select option:selected', wrapper).val();
                const sourceItem = managementApp.government ? $.grep(managementApp.government.curators, x => x.id === id) : [];
                
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

            
            $(document).on('click', function(e) {
                $('.curator__control-dropdown').hide();
                $('.checklist').not(function() {
                    return this.isEqualNode(e.target) || this.contains(e.target);
                }).removeClass('checklist--active');
            });

            $(document).on('click', '.checklist__title', function(e) {
                $(this).parent().toggleClass('checklist--active');
            });

            $(document).on('change', '.checklist input', function() {
                const wrap = $(this).parents('.checklist');

                $('.checklist__title', wrap).text(
                    $('label', wrap).reduce((res, item) => {
                        if(!$(item).find('input').is(':checked')) {
                            return res;
                        }
                        
                        res.push($(item).find('span').text());
                        return res;
                    }, []).join(', ') || 'Не выбрано'
                );
            });
        },
        init() {
            orgForm.bindHandlers();

            $(renderChecklist(`
                ${populateChecklistItems(managementApp.organizations(), orgTypes.organizations)}
                ${populateChecklistItems(managementApp.groupOrganizations(), orgTypes.groupOrganizations)}
            `, 'organizationsChecklist')).appendTo('#organizations');

            $(renderChecklist(
                populateChecklistItems(managementApp.subGroupOrganizations(), orgTypes.subGroupOrganizations),
                'subOrganizationsChecklist'
            )).appendTo('#suborganizations');
            
            // Init form with existing data
            if(managementApp.government) {
                $.each(managementApp.government.curators, (i, item) => {
                    $('#curators').prepend(curatorTemplate(item))
                } );

                $('#organizationsChecklist input').each(function() {

                    $(this).prop('checked', (
                        managementApp.government.organizations.indexOf(+$(this).val()) > -1 &&
                        +$(this).data('org-type') === orgTypes.organizations
                    ) || (
                        managementApp.government.groupOrganizations.indexOf(+$(this).val()) > -1 &&
                        +$(this).data('org-type') === orgTypes.groupOrganizations
                    ));
                    $(this).trigger('change');
                });
                
                $('#subOrganizationsChecklist input').each(function() {
                    $(this).prop('checked', 
                        (
                            managementApp.government.subGroupOrganizations.indexOf(+$(this).val()) > -1 &&
                            +$(this).data('org-type') === orgTypes.subGroupOrganizations
                        )
                    );
                    $(this).trigger('change');
                });

            } else {
                $('#curators').prepend(curatorTemplate());
            }

            
        }
    }

    $(document).ready(function () {
        orgForm.init();
    });

});
