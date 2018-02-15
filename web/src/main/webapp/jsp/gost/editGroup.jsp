<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>
<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<portlet:renderURL var="redirectMainURL">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<script type="text/javascript">
  var managementApp = {
      appId: '${appId}',
      portalName: '${portalName}',
      appName: '${appName}',
      cr: '${pageContext.request.contextPath}/',
      currentPage: 'FORM',
      links: {
        redirect: '${redirectMainURL}'
      },
      application: {
          secret_app_id: '${secretAppIdHeader}'
      },
      <c:if test="${group.subGroups != null && !group.subGroups.isEmpty()}">
        subgroups: [
          <c:forEach items="${group.subGroups}" var="subg">
          {
            id: ${subg.id},
            title: '${subg.title}'
          },
          </c:forEach>
        ],
      </c:if>
      <c:if test="${group.attachments != null && !group.attachments.isEmpty()}">
        attachments: [
          <c:forEach items="${group.attachments}" var="attach">
          {
            id: ${attach.id},
            uuid: ${attach.uuid},
            uniqID: ${attach.uniqID},
            extName: '${attach.extName}',
            fileName: '${attach.fileName}',
            size: '${attach.size}',
            type: '${attach.type}',
          },
          </c:forEach>
        ],
      </c:if>
      group: `${group}`
  };
</script>

<div class="preloader" id="preloader"></div>

<div class="government-wrap gost-form gost-form--edit">
    <a href="${redirectMainURL}" class="government__link">← <span>К подведомственным организациям</span></a>
  
    <div class="government__header">
      <h2 class="government__header-title">Добавление группы организаций</h2>
    </div>
  
    <form class="gform" id="groupForm">
        <div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>

        <input type="hidden" value="${group != null ? group.id : ""}" id="groupForm__id">

        <div class="gost-form__section">
            <label for="groupForm__title" class="gost-form__label control__label--required">Название</label>
            <div class="gost-form__value" data-prop-name="title">
                <input class="textbox form-field" type="text" id="groupForm__title" value="${group != null ? group.title : ""}" data-key="Название">
            </div>
        </div>
        
        <div class="gost-form__section">
            <label for="groupForm__titleShort" class="gost-form__label control__label--required">Краткое наименование</label>
            <div class="gost-form__value" data-prop-name="titleShort">
                <input class="textbox form-field" type="text" id="groupForm__titleShort" value="${group != null ? group.titleShort : ""}" data-key="Краткое наименование">
            </div>
        </div>

        <div class="gost-form__section">
            <label for="groupForm__typeOrganization" class="gost-form__label control__label--required">Относится к типу организации</label>
            <div class="gost-form__value control__input-wrapper" data-prop-name="typeOrganization">
                <select id="groupForm__typeOrganization" class="textbox control__input control__input--wide" data-key="Тип организации">
                  <option value="">Не выбрано</option>
                  <c:forEach items="${typeOrganizations}" var="toItem">
                      <option value="${toItem.id}" 
                        <c:if test="${group != null && group.typeOrganization != null && group.typeOrganization.id == toItem.id}">
                        selected
                        </c:if>
                      >${toItem.title}</option>
                  </c:forEach>
                </select>
                <svg class="control__input-addon"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#arrow_down"></use></svg>
            </div>
        </div>

        <div class="gost-form__section">
            <label for="groupForm__description" class="gost-form__label">Описание</label>
            <div class="gost-form__value gost-form__value--inline-error" data-prop-name="description">
                <textarea class="js-tiny-editor" type="text" id="groupForm__description" data-key="Описание">${group != null ? group.description : ""}</textarea>
            </div>
        </div>


        <div class="control">
          <label class="control__label">Иконка</label>
          <a href="#" id="addIcon" class="government__link government__link--gaps government__link--block">+ <span>Добавить иконку</span></a>
          <div id="icon">
            <c:if test="${group.uuid.length() > 1}">
              <img src="${group.uuid}" alt="Иконка">
            </c:if>
          </div>
        </div>

        <div class="gform__divider"></div>

        <div class="control">
          <label class="control__label">Файлы</label>
          
          <a href="#" id="addFile" class="government__link government__link--gaps government__link--block" data-preview="true" data-type="doc" data-allowedformats="doc, presentation, archive, text">+ <span>Прикрепить файл</span></a>
          
          <label class="control__label">Разрешенные форматы: doc, docx, pdf, txt, xls, xlsx, rtf, wps, ppt, pptx, odt, odf, vsd и odp</label>
          
          <div class="control-gap gost-form__preview" id="files"></div>
  
        </div>

        <div class="gform__divider"></div>

        <div class="control">
          <label class="control__label">Подгруппы организаций, в состав которых будут входить организации</label>
          
          <a href="#" id="addSubgroup" class="government__link government__link--gaps government__link--block">+ <span>Добавить подгруппу организаций</span></a>
          
          <div class="control-gap" id="groupForm__subgroups">
              
          </div> <!--groups-->
  
        </div>
  
        <div class="gform__divider"></div>
  
        <div class="gform__errors">
          Не заполнены поля: <span class="gform__errors-list" data-errors></span>
        </div>
  
        <div class="gform__buttons">
          <button class="button button--primary">Сохранить изменения</button>
          <a class="button" href="${redirectMainURL}">Отмена</a>
        </div>
  
    </form>
  </div>
  
  
  <script src="<c:url value="/js/gost/controls.js"/>"></script>
  <script src="<c:url value="/js/gost/dataservice.js"/>"></script>
  <script src="<c:url value="/js/gost/group-edit.js"/>"></script>