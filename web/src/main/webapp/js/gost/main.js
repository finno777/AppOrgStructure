/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
/* global orgStruct, gostAirNavigation, $ */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/gostAirNavigation'], function ($) {
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

    var orgInit = {
        toggle: function toggle() {
            var $toggleBtn = $('.row__toggle').not('.binded');
            var busy = false;

            $toggleBtn.each(function () {
                $(this).addClass('binded');
                var btnText = $(this).find('span').text();

                $(this).on('click', function (e) {
                    if (busy) {
                        return false;
                    }

                    busy = true;

                    if ($(this).hasClass('active')) {
                        $(this).next().slideUp(function () {
                            busy = false;
                        });
                        $(this).removeClass('active').find('span').text(btnText);
                    } else {
                        $(this).next().slideDown(function () {
                            busy = false;
                        });
                        $(this).addClass('active').find('span').text($(this).data('close'));
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
                            window.location.href = orgStruct.redirectUrl;
                        }
                    }
                });
            });
        },

        mobileAccordion: function mobileAccordion() {
            $('.js-acco-mobile').on('click', function (e) {
                if ($(window).width() < 768) {
                    if ($(e.currentTarget).hasClass('active')) {
                        $(e.currentTarget).removeClass('active');
                    } else {
                        $(e.currentTarget).addClass('active');
                    }
                }
            });
        },


        airNav: function airNav() {
            gostAirNav.init('#orgNav');
        },

        init: function init() {
            this.toggle();
            this.removeAction();
            this.mobileAccordion();
            this.airNav();

            $(window).on('resize', this.airNav());
        }
    };

    $(document).ready(function () {
        initializeDropdowns($);

        $('.js-select').selectmenu();

        orgInit.init();
    });
});
//# sourceMappingURL=maps/main.js.map
