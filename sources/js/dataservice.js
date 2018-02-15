/* eslint-disable */
require(['SHARED/jquery', 'SHARED/jqueryUI', 'SHARED/bluebird'], $ => {
    const secret = !!window.managementApp ? window.managementApp.application.secret_app_id : window.orgStruct.adminsHeaders['gtn.access.appid'];
    const cr = !!window.managementApp ? window.managementApp.cr : window.orgStruct.cr;

    const common = {
        headers: { 'gtn.access.appid': secret },
        contentType: 'application/json',
        dataType: 'json'
    };

    const commonPostPromise = (controller, method, data) => new Promise(function (resolve, reject) {
        $.ajax($.extend({}, common, {
            url: `${cr}${controller}/${method}.action`,
            type: 'POST',
            data: JSON.stringify(data),
            success(data) {
                resolve(data);
            },
            error(err) {
                reject(err);
            }
        }));
    });

    const commonDeletePromise = (controller, method, id) => new Promise(function (resolve, reject) {
        $.ajax($.extend({}, common, {
            url: `${cr}${controller}/${method}.action?id=${id}`,
            type: 'DELETE',
            success(data) {
                resolve(data);
            },
            error(err) {
                reject(err);
            }
        }));
    });
    
    const deletePromise = (controller, method, params) => new Promise(function (resolve, reject) {
        $.ajax($.extend({}, common, {
            url: `${cr}/${controller}/${method}.action?` + Object.keys(params).map(x => `${x}=${params[x]}`).join('&'),
            type: 'DELETE',
            success(data) {
                resolve(data);
            },
            error(err) {
                reject(err);
            }
        }));
    });

    window.dataService = (window.dataService || {
        saveOrganization: data => commonPostPromise('subdivision', 'saveOrganization', data),
        save: data => commonPostPromise('government', 'saveGovernment', data),
        saveGovernmentPerson: data => commonPostPromise('government', 'saveGovernmentPerson', data),
        saveTypeOrganization: data => commonPostPromise('subdivision', 'saveTypeOrganization', data),
        saveGroupOrganizations: data => commonPostPromise('subdivision', 'saveGroupOrganizations', data),
        saveDivision: data => commonPostPromise('government', 'saveDivision', data),
        saveDivisionPerson: data => commonPostPromise('government', 'saveDivisionPerson', data),
        removeCurator: id => commonDeletePromise('government', 'removeCurator', id),
        deleteGovernment: id => commonDeletePromise('government', 'deleteGovernment', id),
        deleteTypeOrganization: id => commonDeletePromise('subdivision', 'deleteTypeOrganization', id),
        deleteOrganization: id => commonDeletePromise('subdivision', 'deleteOrganization', id),
        deleteGroupOrganizations: id => commonDeletePromise('subdivision', 'deleteGroupOrganizations', id),
        deleteDivision: id => commonDeletePromise('government', 'deleteDivision', id),
        removeDivisionPerson: id => commonDeletePromise('government', 'removeDivisionPerson', id),
        saveOrganizationPerson: data => commonPostPromise('subdivision', 'saveOrganizationPerson', data),
        getPersons: data => commonPostPromise('government', 'getPersons', data),
        removePersonOrgan: data => deletePromise('rest-api/orgStructure', 'removePersonOrgan', data),
        removeAttachment: id => new Promise(function (resolve, reject) {
            $.ajax($.extend({}, common, {
                url: `${cr}file/removeAttachment.action?id=${item.id}`,
                type: 'POST',
                success(data) {
                    resolve(data);
                },
                error(err) {
                    reject(err);
                },
                processData: false,
                contentType: false
            }));
        })
    });
});