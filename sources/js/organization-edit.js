/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird', 'SHARED/tinymce', 'SHARED/gostFileManager'], $ => {
    const byData = (name, val) => `[data-${name}${val ? `="${val}"` : ''}]`;
    const preloader = (() =>  $('<div class="preloader" style="display: none;"/>').appendTo('body'))();
    
    const collectPhones = (orgId) => $('#groupForm__phones .subgroup').reduce((prev, item) => {
        const val = $.trim($('.subgroup__input', item).val());
        const id = +$('.subgroup__input', item).data('id');

        if(val.length) {
            prev.push({
                id: id && !isNaN(id) ? id : null,
                phone: val,
                organization: orgId ? { id: orgId } : null
            });
        }

        return prev;
        
    }, []);

    const getSubgroup = () => {
        const item = $('#orgForm__typeOrganization option:selected');
        if(!item || !item.val().length || isNaN(+item.val())) {
            return null;
        }

        return +item.val();
    }
    
    const buildLink = (name, handler) => {
        const btn = $(`<li class="subgroup__menu-item"><a href="#">${name}</a></li>`);
        btn.on('click', handler);
        return btn;
    };

    const phoneTemplate = (val, id) => {
        const tpl = $(`
            <div class="subgroup">
                <input type="text" class="subgroup__input" value="${val || ''}" data-id="${id}">
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
            console.log('remove');
            tpl.remove();
        }).appendTo(container);

        return tpl;
    };

    const validate = () => {
        const errors = [];

        if(!$.trim($('#orgForm__title').val()).length) {
            errors.push('Полное наименование');
        }

        if(!$.trim($('#orgForm__titleShort').val()).length) {
            errors.push('Краткое наименование');
        }

        if(!getSubgroup()) {
            errors.push('Подгруппа организаций');
        }

        // if(!$.trim($('#icon').html()).length) {
        //     errors.push('Иконка');
        // }

        $(byData('errors')).text(errors.join(', ')).parent().toggle(!!errors.length);

        return !errors.length;
    };

    
    $(document).on('click', '#addPhone', (e) => {
        e.preventDefault();
        $('#groupForm__phones').prepend(phoneTemplate());
    });

    $(document).ready(function() {

        if($(byData('phone')).length) {
            $(byData('phone')).each(function() {
                $(this).replaceWith(phoneTemplate($(this).data('phone'), $(this).data('id')));
            });
        } else {
            $('#groupForm__phones').prepend(phoneTemplate());
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

        $('#orgForm').on('submit', function(e) {
            e.preventDefault();
            if(!validate()) {
                return;
            }

            const id = +$.trim($('#typeForm__id').val());

            const data = {
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
                uuid: (() => {
                    const ico = $('#icon img');

                    return ico.length ? ico.attr('src') : '0';
                })(),
                deleted: false
            };

            preloader.show();
            window.dataService.saveOrganization(data)
                .then((res) => {
                    preloader.hide();
                    location = managementApp.links.redirect;
                }).catch((err) => {
                    console.error(err);
                    preloader.hide();
                });
            
        });



    });
});