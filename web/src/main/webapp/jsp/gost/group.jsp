<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<portlet:renderURL var="redirectMainURL">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<portlet:renderURL var="createOrgUrl">
    <portlet:param name="action" value="editOrganizationView" />
</portlet:renderURL>


<portlet:renderURL var="editGroupUrl">
    <portlet:param name="action" value="editGroupView" />
    <portlet:param name="idGroup" value="${group.id}"/>
</portlet:renderURL>

<c:set var="isManager" scope="request" value="${false}/"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>

<script type="text/javascript">
  var managementApp = {
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    application: {
      secret_app_id: '${secretAppIdHeader}'
    },
    group: `${group}`
  };
</script>

<div class="government-view government-wrap">

    <a href="${redirectMainURL}" class="government__link">← <span>К организациям</span></a>

    <c:if test="${isManager == true}">
        <div class="governments__header">
            <div class="governments__header-buttons">
                <a href="${createOrgUrl}" class="button button--primary">
                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#plus"></use></svg>
                    Добавить организацию
                </a>
            </div>

            <div class="dropdown dropdown--icon">
                <div class="dropdown__button button button--icon button--settings button--rotate">
                    <svg>
                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                    </svg>
                </div>
                <div class="dropdown__menu">
                    <ul class="dropdown__list">
                        <li class="dropdown__item">
                            <a href="${editGroupUrl}">Редактировать</a>
                            <a href="javascript:void(0)" class="dropdown__item-link--danger">Удалить</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
    
    <h1 class="government-view__header">${group.title}</h1>

    <p>${group.description}</p>

    <c:if test="${not empty group.attachments}">
        <div class="org-person__row">
            <div class="row__title">
                <h3>Документы</h3>
            </div>

            <div class="row__body">
                <div class="org-doc">
                    <c:forEach var="doc" items="${group.attachments}">
                        <a href="${cr}/doc-api/view.doc?uuid=${doc.uuid}" class="org-doc__row" download>
                            <div class="org-doc__img">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                            xlink:href="${cr}/sprite/sprite.svg#${doc.extName}"></use>
                                </svg>
                            </div>
                            <div class="org-doc__desc">
                                <div class="org-doc__name">
                                    ${doc.fileName}
                                </div>
                                <div class="org-doc__size">${doc.extName}, ${doc.size}</div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>

    <c:forEach  items="${group.subGroups}" var="subgroup">
      <div class="government-section government-section--small">
          <h3 class="government-section__title">
            <span>${subgroup.title}</span>
            <a href="#" class="expand" data-expand-subgroup>
                <span>Показать</span>
                <svg class="toggler__arrow-icon"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#arrow_down"></use></svg>
            </a>
          </h3>
          <div class="government-section__list" style="display: none;">

            <c:forEach items="${subgroup.organizations}" var="org">
                <portlet:renderURL var="viewOrgURL">
                    <portlet:param name="action" value="organizationView"/>
                    <portlet:param name="idOrganization" value="${org.id}"/>
                </portlet:renderURL>

                <portlet:renderURL var="editOrgUrl">
                    <portlet:param name="action" value="editOrganizationView"/>
                    <portlet:param name="idOrganization" value="${org.id}"/>
                </portlet:renderURL>

                <div class="government-section__list-item group group--hovered">
                    <div class="group__icon">
                        <c:choose>
                            <c:when test="${org.uuid.length() > 1}">
                                <c:catch var="e">
                                    <c:import url="http://localhost${org.uuid}" />
                                </c:catch>
                                <c:if test="${!empty e}">
                                    <div class="group__icon--empty">
                                        <span hidden>Error: ${e.message}</span>
                                    </div>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <div class="group__icon--empty"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="group__info group__info--centered">
                        <h4 class="group__info-title">
                            <a href="${viewOrgURL}">${org.title}</a>
                        </h4>
                    </div>
                    <c:if test="${isManager == true}">
                        <div class="group__right">
                            <div class="dropdown dropdown--icon">
                                <div class="dropdown__button button button--icon button--settings button--rotate">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                    </svg>
                                </div>
                                <div class="dropdown__menu">
                                    <ul class="dropdown__list">
                                        <li class="dropdown__item">
                                            <a href="${editOrgUrl}">Редактировать</a>
                                            <a href="javascript:void(0)" class="dropdown__item-link--danger">Удалить</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </c:forEach>

        </div>
      </div>
    </c:forEach>
    
</div>


<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/group.js"/>"></script>