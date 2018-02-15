/* global orgStruct, gostAirNavigation, $ */
require(['SHARED/jquery'], function ($) {
    const cr = orgStruct.cr;
    const usersData = [];
    const organsData = [];
    const $form = $('#org-setting');

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

    const Settings = {
        checkRow: function () {
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

        changePosition: function () {
            $('body').on('click', '.js-move-up, .js-move-down', function () {
                $(this).parents('.dropdown').removeClass('active');

                let $parent = $(this).parents('.list__row');
                let position = $parent.data('position');
                let id = $parent.data('id');
                let rowHtml = $parent.html();

                if ($(this).hasClass('js-move-down')) {
                    let positionNeighbor = $parent.next().data('position');
                    $parent.next().data('position', position).attr('data-position', position);
                    $parent.next().after(`<div class="list__row" ${$parent.is('[data-organ]') ? 'data-oragn' : ''} data-position="${positionNeighbor}" data-id="${id}">${rowHtml}</div>`);
                    $parent.remove();
                } else {
                    let positionNeighbor = $parent.prev().data('position');
                    $parent.prev().data('position', position).attr('data-position', position);
                    $parent.prev().before(`<div class="list__row" ${$parent.is('[data-organ]') ? 'data-oragn' : ''} data-position="${positionNeighbor}" data-id="${id}">${rowHtml}</div>`);
                    $parent.remove();
                }
            });
        },

        save: function () {
            let that = this;
            let savedUsers = false;
            let savedOrgans = false;

            $('.js-settings-save').on('click', function () {
                that.checkRow();

                $.ajax({
                    headers: orgStruct.adminsHeaders,
                    url: `${cr}/rest-api/orgStructure/savePeople.action`,
                    dataType: 'json',
                    type: "POST",
                    data: JSON.stringify(usersData),
                    success: function (data) {
                        console.log(data);
                    },
                    error: function (err) {
                        console.log(err);
                    },
                    statusCode: {
                        200: function () {
                            savedUsers = true;

                            if (savedUsers && savedOrgans) {
                                window.location.href = orgStruct.redirectUrl
                            }
                        }
                    }
                });

                $.ajax({
                    headers: orgStruct.adminsHeaders,
                    url: `${cr}/rest-api/orgStructure/saveOrgans.action`,
                    dataType: 'json',
                    type: "POST",
                    data: JSON.stringify(organsData),
                    success: function (data) {
                        console.log(data);
                    },
                    error: function (err) {
                        console.log(err);
                    },
                    statusCode: {
                        200: function () {
                            savedOrgans = true;

                            if (savedUsers && savedOrgans) {
                                window.location.href = orgStruct.redirectUrl
                            }
                        }
                    }
                });
            });
        },

        removeAction: function () {
            $('.js-remove-action').on('click', function () {
                let that = $(this);

                $.ajax({
                    headers: {
                        "Accept": '*/*',
                        "gtn.access.appid": orgStruct.adminsHeaders['gtn.access.appid']
                    },
                    url: that.data('url') + '?' + $.param({"id": that.data('id')}),
                    type: 'DELETE',
                    processData: false,
                    contentType: false,
                    statusCode: {
                        200: function () {
                            that.parents('.list__row').remove();
                        }
                    }
                });
            });
        },

        init: function () {
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