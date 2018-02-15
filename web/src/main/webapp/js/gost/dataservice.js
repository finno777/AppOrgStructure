/* This file is generated — do not edit by hand! */
/* eslint-disable */
'use strict';

/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], function ($) {
    var secret = !!window.managementApp ? window.managementApp.application.secret_app_id : window.orgStruct.adminsHeaders['gtn.access.appid'];
    var cr = !!window.managementApp ? window.managementApp.cr : window.orgStruct.cr;

    var common = {
        headers: { 'gtn.access.appid': secret },
        contentType: 'application/json',
        dataType: 'json'
    };

    var commonPostPromise = function commonPostPromise(controller, method, data) {
        return new Promise(function (resolve, reject) {
            $.ajax($.extend({}, common, {
                url: '' + cr + controller + '/' + method + '.action',
                type: 'POST',
                data: JSON.stringify(data),
                success: function success(data) {
                    resolve(data);
                },
                error: function error(err) {
                    reject(err);
                }
            }));
        });
    };

    var commonDeletePromise = function commonDeletePromise(controller, method, id) {
        return new Promise(function (resolve, reject) {
            $.ajax($.extend({}, common, {
                url: '' + cr + controller + '/' + method + '.action?id=' + id,
                type: 'DELETE',
                success: function success(data) {
                    resolve(data);
                },
                error: function error(err) {
                    reject(err);
                }
            }));
        });
    };

    var deletePromise = function deletePromise(controller, method, params) {
        return new Promise(function (resolve, reject) {
            $.ajax($.extend({}, common, {
                url: cr + '/' + controller + '/' + method + '.action?' + Object.keys(params).map(function (x) {
                    return x + '=' + params[x];
                }).join('&'),
                type: 'DELETE',
                success: function success(data) {
                    resolve(data);
                },
                error: function error(err) {
                    reject(err);
                }
            }));
        });
    };

    window.dataService = window.dataService || {
        saveOrganization: function saveOrganization(data) {
            return commonPostPromise('subdivision', 'saveOrganization', data);
        },
        save: function save(data) {
            return commonPostPromise('government', 'saveGovernment', data);
        },
        saveGovernmentPerson: function saveGovernmentPerson(data) {
            return commonPostPromise('government', 'saveGovernmentPerson', data);
        },
        saveTypeOrganization: function saveTypeOrganization(data) {
            return commonPostPromise('subdivision', 'saveTypeOrganization', data);
        },
        saveGroupOrganizations: function saveGroupOrganizations(data) {
            return commonPostPromise('subdivision', 'saveGroupOrganizations', data);
        },
        saveDivision: function saveDivision(data) {
            return commonPostPromise('government', 'saveDivision', data);
        },
        saveDivisionPerson: function saveDivisionPerson(data) {
            return commonPostPromise('government', 'saveDivisionPerson', data);
        },
        removeCurator: function removeCurator(id) {
            return commonDeletePromise('government', 'removeCurator', id);
        },
        deleteGovernment: function deleteGovernment(id) {
            return commonDeletePromise('government', 'deleteGovernment', id);
        },
        deleteTypeOrganization: function deleteTypeOrganization(id) {
            return commonDeletePromise('subdivision', 'deleteTypeOrganization', id);
        },
        deleteOrganization: function deleteOrganization(id) {
            return commonDeletePromise('subdivision', 'deleteOrganization', id);
        },
        deleteGroupOrganizations: function deleteGroupOrganizations(id) {
            return commonDeletePromise('subdivision', 'deleteGroupOrganizations', id);
        },
        deleteDivision: function deleteDivision(id) {
            return commonDeletePromise('government', 'deleteDivision', id);
        },
        removeDivisionPerson: function removeDivisionPerson(id) {
            return commonDeletePromise('government', 'removeDivisionPerson', id);
        },
        saveOrganizationPerson: function saveOrganizationPerson(data) {
            return commonPostPromise('subdivision', 'saveOrganizationPerson', data);
        },
        getPersons: function getPersons(data) {
            return commonPostPromise('government', 'getPersons', data);
        },
        removePersonOrgan: function removePersonOrgan(data) {
            return deletePromise('rest-api/orgStructure', 'removePersonOrgan', data);
        },
        removeAttachment: function removeAttachment(id) {
            return new Promise(function (resolve, reject) {
                $.ajax($.extend({}, common, {
                    url: cr + 'file/removeAttachment.action?id=' + item.id,
                    type: 'POST',
                    success: function success(data) {
                        resolve(data);
                    },
                    error: function error(err) {
                        reject(err);
                    },

                    processData: false,
                    contentType: false
                }));
            });
        }
    };
});
//# sourceMappingURL=maps/dataservice.js.map
