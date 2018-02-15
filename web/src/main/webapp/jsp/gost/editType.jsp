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
      cr: '${pageContext.request.contextPath}/',
      currentPage: 'FORM',
      links: {
        redirect: '${redirectMainURL}'
      },
      application: {
          secret_app_id: '${secretAppIdHeader}'
      },
      typeOrganization: `${typeOrganization}`
  };
</script>

<div class="government-wrap gost-form gost-form--edit">
    <a href="${redirectMainURL}" class="government__link">← <span>К подведомственным организациям</span></a>
  
    <div class="government__header">
      <h2 class="government__header-title">Создания типа организации</h2>
    </div>
  
    <form class="gform" id="typeForm">
        <div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>

        <input type="hidden" value="${typeOrganization != null ? typeOrganization.id : ""}" id="typeForm__id">

        <div class="gost-form__section">
            <label for="typeForm__title" class="gost-form__label">Название*</label>
            <div class="gost-form__value" data-prop-name="name" data-required data-max-length="1000">
                <input class="textbox form-field" type="text" id="typeForm__title" value="${typeOrganization != null ? typeOrganization.title : ""}"data-key="Название">
            </div>
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
  <script src="<c:url value="/js/gost/type-edit.js"/>"></script>