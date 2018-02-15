/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/tinymce', 'SHARED/gostFileManager'], function ($) {
    $.fn.reduce = function (fnReduce, initialValue) {
        var values = this,
            previousValue = initialValue;

        values.each(function (index, currentValue) {
            previousValue = fnReduce.call(currentValue, previousValue, currentValue, index, values);
        });

        return previousValue;
    };

    var byData = function byData(name, val) {
        return '[data-' + name + (val ? '="' + val + '"' : '') + ']';
    };

    var deactivate = function deactivate(target, wrapper, classMod) {
        return $(wrapper).not(function () {
            return this.isEqualNode(target) || this.contains(target);
        }).removeClass(classMod);
    };

    $('.government__toggler').on('click', function (e) {
        $(this).toggleClass('government__toggler--active');
    });

    $(document).on('click', function (e) {
        deactivate(e.target, '.government__toggler', 'government__toggler--active');
        deactivate(e.target, '.dropdown.dropdown--icon', 'active');
        $('.subgroup__menu').removeClass('subgroup__menu--active');
    });

    $(document).on('click', '.government-wrap .dropdown__button', function (e) {
        $(this).parent().toggleClass('active');
    });

    $(document).on('click', byData('expand'), function (e) {
        e.preventDefault();
        var current = $(this).hasClass('expand--active');

        $(this).toggleClass('expand--active', !current);
        if (!current) {
            $('span', this).text('Скрыть');
            $($(this).data('expand')).slideDown();
        } else {
            $('span', this).text('Показать');
            $($(this).data('expand')).slideUp();
        }
    });

    var initTiny = function initTiny() {
        tinymce.init({
            selector: '.js-tiny-editor',
            menubar: false,
            statusbar: false,
            relative_urls: false,
            image_advtab: true,
            mode: 'textareas',
            external_plugins: {
                advlist: managementApp.cr + '/lib/tinymce/plugins/advlist/plugin.min.js',
                lists: managementApp.cr + '/lib/tinymce/plugins/lists/plugin.min.js',
                link: managementApp.cr + '/lib/tinymce/plugins/link/plugin.min.js',
                visualchars: managementApp.cr + '/lib/tinymce/plugins/visualchars/plugin.min.js',
                charmap: managementApp.cr + '/lib/tinymce/plugins/charmap/plugin.min.js',
                image: managementApp.cr + '/lib/tinymce/plugins/image/plugin.min.js',
                paste: managementApp.cr + '/lib/tinymce/plugins/paste/plugin.min.js'
            },
            plugins: 'lists advlist link table visualchars charmap image textcolor colorpicker paste',
            toolbar: 'undo redo fontsizeselect | bold italic underline strikethrough | subscript superscript forecolor backcolor | alignleft aligncenter alignright alignjustify  | ' + 'outdent indent | numlist bullist  | customlink | removeformat | photoupload videoupload fileupload',
            body_class: 'mce-body',
            language_url: managementApp.cr + '/lib/tinymce/langs/ru.js',
            content_css: '/eXoResources/plugins/tinymce/skins/lightgray/content.min.css, /eXoResources/plugins/tinymce/skins/lightgray/content.RST.css',
            skin_url: '/eXoResources/plugins/tinymce/skins/lightgray',
            advlist_bullet_styles: 'disc',
            paste_use_dialog: false,
            paste_auto_cleanup_on_paste: true,
            paste_convert_headers_to_strong: false,
            paste_strip_class_attributes: 'all',
            paste_remove_spans: true,
            paste_remove_styles: true,
            paste_retain_style_properties: '',
            style_formats: [{ title: 'Headers',
                items: [{ title: 'Заголовок 1', block: 'h2' }, { title: 'Заголовок 2', block: 'h3' }, { title: 'Заголовок 3', block: 'h4' }] }, { title: 'Blocks',
                items: [{ title: 'Параграф', format: 'p' }] }],
            setup: function setUp(editor) {

                editor.addButton('photoupload', {
                    icon: 'image',
                    tooltip: 'Прикрепить фото',
                    onclick: function onclick() {
                        var options = {
                            appId: managementApp.appId,
                            portalName: managementApp.portalName,
                            appName: managementApp.appName,
                            allowedFileFormats: ['img'],
                            onClose: function close() {
                                var selectedFiles = RSTFileManager.getFiles();
                                selectedFiles.forEach(function (file) {
                                    editor.insertContent('<img src="' + managementApp.cr + '/image-api/view.ido?uuid=' + file.uuid + '" data-uuid="' + file.uuid + '"/>');
                                });
                            }
                        };
                        RSTFileManager.open(options);
                    }
                });

                editor.addButton('videoupload', {
                    icon: 'media',
                    tooltip: 'Прикрепить видео',
                    onclick: function onclick() {
                        var options = {
                            appId: managementApp.appId,
                            portalName: managementApp.portalName,
                            appName: managementApp.appName,
                            allowedFileFormats: ['video'],
                            onClose: function onClose() {
                                var selectedFiles = RSTFileManager.getFiles();
                                selectedFiles.forEach(function (file) {
                                    editor.insertContent('                                            \n                                                    <video width="320" height="240" controls data-uuid="' + file.uuid + '">\n                                                        <source src="' + managementApp.cr + '/media-api/view.media?uuid=' + file.uuid + '" type="video/mp4">\n                                                        <source src="' + managementApp.cr + '/media-api/view.media?uuid=' + file.uuid + '" type="video/ogg">\n                                                        Your browser does not support the video tag.\n                                                     </video>\n                                                ');
                                });
                            }
                        };
                        RSTFileManager.open(options);
                    }
                });

                editor.addButton('fileupload', {
                    icon: 'newdocument',
                    tooltip: 'Прикрепить документ',
                    onclick: function onclick() {
                        var options = {
                            appId: managementApp.appId,
                            portalName: managementApp.portalName,
                            appName: managementApp.appName,
                            allowedFileFormats: ['doc', 'archive', 'text'],
                            onClose: function onClose() {
                                var selectedFiles = RSTFileManager.getFiles();
                                selectedFiles.forEach(function (file) {
                                    editor.insertContent('\n                                        <a href="/documentManager/rest/file/load/' + file.uuid + '" data-uuid="' + file.uuid + '">' + (file.displayName || file.fileName) + '</a>\n                                        <span class="tiny-content__file-size">(' + file.extension + ' , ' + (file.size / 10 / 1024 / 1024).toFixed(2) + ' \u041C\u0431)</span>\n                                    ');
                                });
                            }
                        };
                        RSTFileManager.open(options);
                    }
                });
            }
        });
    };

    $(document).ready(function () {
        initTiny();
    });
});
//# sourceMappingURL=maps/controls.js.map
