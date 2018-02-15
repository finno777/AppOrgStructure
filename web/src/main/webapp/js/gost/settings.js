/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* global orgStruct, gostAirNavigation, $ */
require(['SHARED/jquery'], function ($) {
    var cr = orgStruct.cr;
    var usersData = [];
    var organsData = [];
    var $form = $('#org-setting');

    function initializeDropdowns() {
        $(document).on('click', '.org-page .dropdown__button', function () {
            var currentVisibility = $(this).parent('.dropdown').hasClass('active');
            $('.dropdown').removeClass('active');

            if (!currentVisibility) {
                var $dropdown = $(this).parent('.dropdown');
                $dropdown.addClass('active');

                if ($dropdown.hasClass('dropdown--fixed')) {
                    var $dropdownMenu = $(this).siblings('.dropdown__menu');
                    var buttonOffset = this.getBoundingClientRect();
                    $dropdownMenu.css('top', buttonOffset.top + 'px');
                    $dropdownMenu.css('right', $(window).width() - buttonOffset.right + 'px');
                }
            }
        });

        $(document).on('mouseup', function (e) {
            var $dropdown = $('.org-page .dropdown');
            if (!$dropdown.is(e.target) && $dropdown.has(e.target).length === 0) {
                $dropdown.removeClass('active');
            }
        });

        $(window).on('scroll resize orientationchange', function () {
            var $dropdown = $('.org-page .dropdown--fixed');

            $dropdown.each(function () {
                $(this).removeClass('active');
            });
        });
    }

    var Settings = {
        checkRow: function checkRow() {
            $form.find('.list__row').not('.list__row--static').each(function () {
                if ($(this).is('[data-organ]')) {
                    organsData.push({
                        'id': $(this).data('id'),
                        'sitePosition': $(this).data('position')
                    });
                } else {
                    usersData.push({
                        'id': $(this).data('id'),
                        'sitePosition': $(this).data('position')
                    });
                }
            });
        },

        changePosition: function changePosition() {
            $('body').on('click', '.js-move-up, .js-move-down', function () {
                $(this).parents('.dropdown').removeClass('active');

                var $parent = $(this).parents('.list__row');
                var position = $parent.data('position');
                var id = $parent.data('id');
                var rowHtml = $parent.html();

                if ($(this).hasClass('js-move-down')) {
                    var positionNeighbor = $parent.next().data('position');
                    $parent.next().data('position', position).attr('data-position', position);
                    $parent.next().after('<div class="list__row" ' + ($parent.is('[data-organ]') ? 'data-oragn' : '') + ' data-position="' + positionNeighbor + '" data-id="' + id + '">' + rowHtml + '</div>');
                    $parent.remove();
                } else {
                    var _positionNeighbor = $parent.prev().data('position');
                    $parent.prev().data('position', position).attr('data-position', position);
                    $parent.prev().before('<div class="list__row" ' + ($parent.is('[data-organ]') ? 'data-oragn' : '') + ' data-position="' + _positionNeighbor + '" data-id="' + id + '">' + rowHtml + '</div>');
                    $parent.remove();
                }
            });
        },

        save: function save() {
            var that = this;
            var savedUsers = false;
            var savedOrgans = false;

            $('.js-settings-save').on('click', function () {
                that.checkRow();

                $.ajax({
                    headers: orgStruct.adminsHeaders,
                    url: cr + '/rest-api/orgStructure/savePeople.action',
                    dataType: 'json',
                    type: "POST",
                    data: JSON.stringify(usersData),
                    success: function success(data) {
                        console.log(data);
                    },
                    error: function error(err) {
                        console.log(err);
                    },
                    statusCode: {
                        200: function _() {
                            savedUsers = true;

                            if (savedUsers && savedOrgans) {
                                window.location.href = orgStruct.redirectUrl;
                            }
                        }
                    }
                });

                $.ajax({
                    headers: orgStruct.adminsHeaders,
                    url: cr + '/rest-api/orgStructure/saveOrgans.action',
                    dataType: 'json',
                    type: "POST",
                    data: JSON.stringify(organsData),
                    success: function success(data) {
                        console.log(data);
                    },
                    error: function error(err) {
                        console.log(err);
                    },
                    statusCode: {
                        200: function _() {
                            savedOrgans = true;

                            if (savedUsers && savedOrgans) {
                                window.location.href = orgStruct.redirectUrl;
                            }
                        }
                    }
                });
            });
        },

        removeAction: function removeAction() {
            $('.js-remove-action').on('click', function () {
                var that = $(this);

                $.ajax({
                    headers: {
                        "Accept": '*/*',
                        "gtn.access.appid": orgStruct.adminsHeaders['gtn.access.appid']
                    },
                    url: that.data('url') + '?' + $.param({ "id": that.data('id') }),
                    type: 'DELETE',
                    processData: false,
                    contentType: false,
                    statusCode: {
                        200: function _() {
                            that.parents('.list__row').remove();
                        }
                    }
                });
            });
        },

        init: function init() {
            this.save();
            this.changePosition();
            this.removeAction();
        }
    };

    $(document).ready(function () {
        initializeDropdowns($);
        Settings.init();
    });
});
//# sourceMappingURL=maps/settings.js.map
