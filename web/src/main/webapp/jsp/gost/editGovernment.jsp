<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>



<c:choose>
    <c:when test="${government != null}">
        <portlet:renderURL var="redirectMainURL">
            <portlet:param name="action" value="singleGovernmentView"/>
            <portlet:param name="id" value="${government.id}"/>
        </portlet:renderURL>
    </c:when>
    <c:otherwise>
        <portlet:renderURL var="redirectMainURL">
            <portlet:param name="action" value="redirectMain"/>
        </portlet:renderURL>
    </c:otherwise>
</c:choose>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

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
      redirectMainURL: '${redirectMainURL}'
    },
    <c:if test="${government != null}">
      government: {
        id: ${government.id},
        <c:if test="${government.person != null}">
        person: {
          id: ${government.person.id}
        },
        </c:if>
        curators: [
          <c:forEach items="${government.curators}" var="item">
          {
            linkId: ${item.id},
            id: ${item.person.id},
            description: '${item.description}'
          },
          </c:forEach>
        ],
        organizations: [
          <c:forEach items="${government.organizations}" var="item">
              ${item.id},
          </c:forEach>
        ],
        groupOrganizations: [
          <c:forEach items="${government.groupOrganizations}" var="item">
              ${item.id},
          </c:forEach>
        ],
        subGroupOrganizations: [
          <c:forEach items="${government.subGroupOrganizations}" var="item">
              ${item.id},
          </c:forEach>
        ]

      },
    </c:if>
    organizations: function() {
      return [
        <c:forEach items="${organizations}" var="item">
         {
            id: ${item.id},
            title: '${item.title}',
            deleted: false
         },
        </c:forEach>
      ]
    },
    groupOrganizations:  function() {
      return [
        <c:forEach items="${groupOrganizations}" var="item">
         {
            id: ${item.id},
            title: '${item.title}',
            deleted: false
         },
        </c:forEach>
      ]
    },
    subGroupOrganizations: function() {
      return [
        <c:forEach items="${subGroupOrganizations}" var="item">
         {
            id: ${item.id},
            title: '${item.title}',
            deleted: false
         },
        </c:forEach>
      ]
    },
    curators: function() {
      return [
        <c:forEach items="${curators}" var="item">
         {
            id: ${item.id},
            name: '${item.name}'
         },
        </c:forEach>
      ]
    }
  };
</script>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<div class="government-edit government-wrap">
    <a href="${redirectMainURL}" class="government__link">← <span>К управлению</span></a>

		<div class="government__header">
			<h2 class="government__header-title">
          <c:choose>
              <c:when test="${government != null}">
                  Редактирование управления
              </c:when>
              <c:otherwise>
                  Добавление управления
              </c:otherwise>
          </c:choose>
        </h2>
		</div>
		<form class="gform" id="governmentEditForm">
			<div class="gform__info">Поля, отмеченные символом (*), обязательны для заполнения</div>

			<div class="control">
				<label class="control__label control__label--required" for="governmentEditForm__title">Наименования управления</label>
				<input class="control__input" type="text" id="governmentEditForm__title" value="${government.title}">
			</div>

      <div class="control">
        <label class="control__label control__label--required">Иконка</label>
        <a href="#" id="addIcon" class="government__link government__link--gaps government__link--block">+ <span>Добавить иконку</span></a>
        <div id="icon">
          <c:if test="${government.uuid.length() > 1}">
            <img src="${government.uuid}" alt="Иконка">
          </c:if>
        </div>
			</div>

      <div class="gform__divider"></div>

      <div class="control" id="organizations">
				<label class="control__label control__label--bold">Подведомственные организации</label>
        <!--orgs-->
			</div>

      <div class="control" id="suborganizations">
        <label class="control__label control__label--bold">Территориальные органы</label>
        <!--Sub orgs-->
			</div>

      <div class="gform__divider"></div>

			<div class="control">
				<div class="control__label control__label--required">Положение об управлении</div>
				<textarea class="js-tiny-editor" type="text" id="governmentEditForm__description">${government.description}</textarea>
			</div>
  
      <div class="gform__divider"></div>
      
      <div class="control">
				<label class="control__label control__label--bold">Кураторы</label>
        
        <a href="#" id="addCurator" class="government__link government__link--block">+ <span>Добавить куратора</span></a>
  
        <div class="control-gap" id="curators"></div> <!-- Curators -->

			</div>

      <div class="gform__divider"></div>

      <div class="gform__errors">
        Не заполнены поля: <span class="gform__errors-list" data-errors></span>
      </div>

      <div class="gform__buttons">
        <c:choose>
            <c:when test="${government != null}">
                <button class="button button--primary">Сохранить изменения</button>
            </c:when>
            <c:otherwise>
                <button class="button button--primary">Добавить управление</button>
            </c:otherwise>
        </c:choose>
        <a class="button" href="${redirectMainURL}">Отмена</a>
      </div>
		</form>
</div>

<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/governments-edit.js"/>"></script>