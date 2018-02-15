<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<portlet:renderURL var="redirectMainURL">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<c:if test="${organization != null}">
  <portlet:renderURL var="viewOrgURL">
      <portlet:param name="action" value="organizationView"/>
      <portlet:param name="idOrganization" value="${organization.id}"/>
  </portlet:renderURL>
</c:if>

<script type="text/javascript">
  var managementApp = {
    appId: '${appId}',
    portalName: '${portalName}',
    appName: '${appName}',
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    links: {
      redirect: '${organization != null ? viewOrgURL : redirectMainURL}'
    },
    application: {
      secret_app_id: '${secretAppIdHeader}'
    }
  };
</script>

<div class="government-wrap">
  <a href="${redirectMainURL}" class="government__link">← <span>К подведомственным организациям</span></a>

  <div class="government__header">
    <h2 class="government__header-title">
      <c:choose>
          <c:when test="${organization != null}">
              Редактирование организации
          </c:when>
          <c:otherwise>
              Добавление организации
          </c:otherwise>
      </c:choose>
    </h2>
  </div>

  <form class="gform" id="orgForm">
      <div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>
      
      <div class="control">
        <label class="control__label control__label--required" for="orgForm__title">Полное наименование</label>
        <input class="control__input" type="text" id="orgForm__title" value="${organization != null ? organization.title : ""}">
      </div>
      
      <div class="control">
        <label class="control__label control__label--required" for="orgForm__titleShort">Краткое наименование</label>
        <input class="control__input" type="text" id="orgForm__titleShort" value="${organization != null ? organization.titleShort : ""}">
      </div>

      <div class="control">
        <label class="control__label control__label--required" for="orgForm__titleShort">Относится к подгруппе организаций</label>

        <div class="control__input-wrapper">
          <select id="orgForm__typeOrganization" class="control__input control__input--wide">
            <option value="">Не выбрано</option>
            <c:forEach items="${subGroupOrganizations}" var="toItem">
                <option value="${toItem.id}" 
                  <c:if test="${organization != null && organization.subGroupOrganizations != null && organization.subGroupOrganizations.id == toItem.id}">
                  selected
                  </c:if>
                >${toItem.title}</option>
            </c:forEach>
            <svg class="control__input-addon"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#arrow_down"></use></svg>
          </select>
        </div>

      </div>

      <div class="control">
        <div class="control__label">Описание</div>
        <textarea class="js-tiny-editor" id="orgForm__description">${organization != null ? organization.description : ""}</textarea>
      </div>

      <div class="control">
        <label class="control__label">Иконка</label>
        <a href="#" id="addIcon" class="government__link government__link--gaps government__link--block">+ <span>Добавить иконку</span></a>
        <div id="icon">
          <c:if test="${organization.uuid.length() > 1}">
            <img src="${organization.uuid}" alt="Иконка">
          </c:if>
        </div>
      </div>
  
      <div class="gform__divider"></div>
      
      <input type="hidden" value="${organization != null ? organization.id : ""}" id="typeForm__id">

      <div class="control">
        <label class="control__label" for="orgForm__address">Адрес</label>
        <input class="control__input" type="text" id="orgForm__address" value="${organization != null ? organization.address : ""}">
      </div>
      
      <div class="control">
        <label class="control__label" for="orgForm__addressActual">Фактический адрес</label>
        <input class="control__input" type="text" id="orgForm__addressActual" value="${organization != null ? organization.addressActual : ""}">
      </div>
      
      <div class="control">
        <label class="control__label">Телефон</label>
        
        <a href="#" id="addPhone" class="government__link government__link--gaps government__link--block">+ <span>Добавить телефон</span></a>
        
        <div class="control-gap" id="groupForm__phones">
          <c:if test="${organization != null}">
            <c:forEach items="${organization.phones}" var="p">
                <div data-phone="${p.phone}" data-id="${p.id}"></div>
            </c:forEach>
          </c:if>
        </div> <!--groups-->

      </div>
      
      <div class="control">
        <label class="control__label" for="orgForm__fax">Факс</label>
        <input class="control__input" type="text" id="orgForm__fax" value="${organization != null ? organization.fax : ""}">
      </div>
      
      <div class="control">
        <label class="control__label" for="orgForm__email">E-mail</label>
        <input class="control__input" type="text" id="orgForm__email" value="${organization != null ? organization.mail : ""}">
      </div>
      
      <div class="control">
        <label class="control__label" for="orgForm__site">Сайт</label>
        <input class="control__input" type="text" id="orgForm__site" value="${organization != null ? organization.site : ""}">
      </div>

      <div class="control">
        <div class="control__label">Положение</div>
        <textarea class="js-tiny-editor" id="orgForm__provision">${organization != null ? organization.provision : ""}</textarea>
      </div>

      <div class="gform__divider"></div>

      <div class="gform__errors">
        Не заполнены поля: <span class="gform__errors-list" data-errors></span>
      </div>

      <div class="gform__buttons">
        <button class="button button--primary">
          <c:choose>
              <c:when test="${organization != null}">
                  Сохранить изменения
              </c:when>
              <c:otherwise>
                  Добавить организацию
              </c:otherwise>
          </c:choose>
        </button>
        <a class="button" href="${redirectMainURL}">Отмена</a>
      </div>

  </form>
</div>


<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/organization-edit.js"/>"></script>