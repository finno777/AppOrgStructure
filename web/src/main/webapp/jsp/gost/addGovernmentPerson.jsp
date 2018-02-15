<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<portlet:renderURL var="viewGoverURL">
    <portlet:param name="action" value="singleGovernmentView"/>
    <portlet:param name="id" value="${government.id}"/>
</portlet:renderURL>


<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<script type="text/javascript">
  var managementApp = {
    appId: '${appId}',
    portalName: '${portalName}',
    appName: '${appName}',
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    application: {
      secret_app_id: '${secretAppIdHeader}'
    },
    links: {
      government: '${viewGoverURL}'
    }
  };
</script>

<div class="government-wrap">
  <a href="${viewGoverURL}" class="government__link">← <span>К управлению</span></a>

  <div class="government__header">
    <h2 class="government__header-title">Добавление руководителя</h2>
  </div>

  <form class="gform" id="personForm">
      <div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>

      <input type="hidden" value="${government != null ? government.id : ""}" id="personForm__governmentId">
      <input type="hidden" value="${government != null && government.person != null ? government.person.id : ""}" id="personForm__personLinkId">
      <input type="hidden" value="${government != null && government.person != null && government.person.person != null ? government.person.person.id : ""}" id="personForm__personId">
      
      <div class="control">
        <label class="control__label control__label--required" for="personForm__name">ФИО</label>
        <div class="control__wrapper" data-search-prevent>
          <input class="control__input" type="text" id="personForm__name" value="${government != null && government.person != null ? government.person.person.name : ""}">
          <svg class="toggler__arrow-icon" data-search-arrow><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#arrow_down"></use></svg>
          <div class="preloader preloader--local" data-search-preloader></div>
          <ul class="control__dropdown" data-search-list></ul>
        </div>
      </div>

      <div class="control">
        <label class="control__label" for="personForm__position">Должность</label>
        <textarea class="control__input" type="text" id="personForm__position">${government != null ? government.person.position : ""}</textarea>
      </div>
    
      <div class="control">
        <label class="control__label" for="personForm__phone">Телефон</label>
        <input class="control__input" type="text" id="personForm__phone" value="${government != null ? government.person.person.phone : ""}">
      </div>
    
      <div class="control">
        <label class="control__label" for="personForm__email">E-mail</label>
        <input class="control__input" type="text" id="personForm__email" value="${government != null ? government.person.person.email : ""}">
      </div>

      <div class="gform__divider"></div>

      <div class="control">
        <label class="control__label">Фотография*:</label>
        
        <a href="#" id="addFile" data-preview="true" data-type="img" data-allowedformats="img" data-maxfilecount="1" class="government__link government__link--gaps government__link--block">+ <span>Добавить фотографию</span></a>
        
        <div class="gost-form__preview control-gap" id="photo">
            <c:if test="${not empty government.person.person.attachments}">
              <div class="preview-item"
                data-extension="${government.person.person.attachments[government.person.person.attachments.size() - 1].extName}"
                data-filename="${government.person.person.attachments[government.person.person.attachments.size() - 1].fileName}"
                data-size="${government.person.person.attachments[government.person.person.attachments.size() - 1].size}"
                data-uniqid="${government.person.person.attachments[government.person.person.attachments.size() - 1].uniqID}"
                data-id="${government.person.person.attachments[government.person.person.attachments.size() - 1].id}"
                data-uuid="${government.person.person.attachments[government.person.person.attachments.size() - 1].uuid}"
                data-type="${government.person.person.attachments[government.person.person.attachments.size() - 1].type}"
                data-path="${cr}image-api/view.ido?uuid=${government.person.person.attachments[government.person.person.attachments.size() - 1].uuid}">
                  <img src="${cr}image-api/view.ido?uuid=${government.person.person.attachments[government.person.person.attachments.size() - 1].uuid}" class="preview-item__img">
                  <div class="preview-item__remove-btn">
                      <svg>
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#icon-close"></use>
                      </svg>
                  </div>
              </div>
            </c:if>
        </div>

        <label class="control__label">Разрешенные форматы: gif, jpeg, jpg, bmp и png</label>

      </div>
      
      <div class="gform__divider"></div>

      <div class="gform__errors">
        Не заполнены поля: <span class="gform__errors-list" data-errors></span>
      </div>

      <div class="gform__buttons">
        <button class="button button--primary">Сохранить изменения</button>
        <a class="button" href="${viewGoverURL}">Отмена</a>
      </div>

  </form>
</div>


<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/governments-person.js"/>"></script>