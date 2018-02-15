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

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<script type="text/javascript">
  var managementApp = {
    appId: '${appId}',
    portalName: '${portalName}',
    appName: '${appName}',
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    links: {
      government: '${viewGoverURL}'
    },
    application: {
      secret_app_id: '${secretAppIdHeader}'
    }
  };
</script>


<div class="government-wrap">
  <a href="${viewGoverURL}" class="government__link">← <span>К управлению</span></a>

  <div class="government__header">
    <h2 class="government__header-title">
      <c:choose>
          <c:when test="${divisions != null}">
              Редактирование отдела
          </c:when>
          <c:otherwise>
              Добавление отдела
          </c:otherwise>
      </c:choose>
    </h2>
  </div>

  <form class="gform" id="divisionForm">
      <div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>
      
      <input type="hidden" value="${government.id}" id="divisionForm__governmentId">
      <input type="hidden" value="${divisions != null ? divisions.id : ""}" id="divisionForm__id">

      <div class="control">
        <label class="control__label control__label--required" for="divisionForm__name">Название</label>
        <input class="control__input" type="text" id="divisionForm__name" value="${divisions != null ? divisions.title : ""}">
      </div>

      <div class="control">
        <label class="control__label control__label--required">Иконка</label>
        <a href="#" id="addIcon" class="government__link government__link--gaps government__link--block">+ <span>Добавить иконку</span></a>
        <div id="icon">
          <c:if test="${divisions.uuid.length() > 1}">
            <img src="${divisions.uuid}" alt="Иконка">
          </c:if>
        </div>
      </div>

      <div class="control control-gap">
        <div class="control__label control__label--required">Задачи отдела</div>
        <textarea class="js-tiny-editor" type="text" id="divisionForm__description">${divisions != null ? divisions.description : ""}</textarea>
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
<script src="<c:url value="/js/gost/division-edit.js"/>"></script>