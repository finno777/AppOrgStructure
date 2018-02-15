<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<portlet:renderURL var="divisionPersonURL">
    <portlet:param name="action" value="addDivisionPersonView"/>
    <portlet:param name="id" value="${division.id}"/>
    <portlet:param name="governmentId" value="${government.id}"/>
</portlet:renderURL>

<portlet:renderURL var="viewGoverURL">
    <portlet:param name="action" value="singleGovernmentView"/>
    <portlet:param name="id" value="${government.id}"/>
</portlet:renderURL>


<portlet:renderURL var="editDivisionURL">
    <portlet:param name="action" value="editDivisionsView"/>
    <portlet:param name="id" value="${division.id}"/>
    <portlet:param name="governmentId" value="${government.id}"/>
</portlet:renderURL>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>
<c:set var="isManager" scope="request" value="${false}"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>


<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<script type="text/javascript">
  var managementApp = {
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    application: {
      secret_app_id: '${secretAppIdHeader}'
    },
    division: `${division}`
  };
</script>


<div class="government-wrap">
    <a href="${viewGoverURL}" class="government__link">← <span>К управлению</span></a>
    
    <c:if test="${isManager == true}">
        <div class="governments__header">
                <div class="governments__header-buttons">
                    <a href="${divisionPersonURL}" class="button button--primary">
                        <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#plus"></use></svg>
                        Добавить участника
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
                            <a href="${editDivisionURL}">Редактирование</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>

    <h2 style="text-transform: uppercase;">${government.title}</h2>
    <h3>${division.title}</h3>

    <c:if test="${!division.personList.isEmpty()}">
        <div class="persons">
            <c:forEach items="${division.personList}" var="p">
                <portlet:renderURL var="editPersonUrl">
                    <portlet:param name="action" value="addDivisionPersonView"/>
                    <portlet:param name="divisionPersonId" value="${p.id}"/>
                    <portlet:param name="id" value="${division.id}"/>
                    <portlet:param name="governmentId" value="${government.id}"/>
                </portlet:renderURL>

                <div class="persons__item" data-remove-container>
                    <div class="persons__img">
                        <c:choose>
                            <c:when test="${!p.person.attachments.isEmpty()}">
                                <img src="${cr}image-api/view.ido?uuid=${p.person.attachments[p.person.attachments.size() - 1].uuid}">
                            </c:when>
                            <c:otherwise>
                                <div class="persons__img--empty"></div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="persons__info">
                        <div class="persons__info-name">${p.person.name}</div>
                        <div class="persons__info-position">${p.position}</div>
                        <div class="persons__info-contacts">
                        <div class="persons__info-contacts-item">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#phone"></use>
                            </svg>
                            <a href="tel:${p.person.phone}" class="link-text">${p.person.phone}</a>
                        </div>
                        <div class="persons__info-contacts-item">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#email"></use>
                            </svg>
                            <a href="mailto:${p.person.email}" class="link-text">${p.person.email}</a>
                        </div>
                        </div>
                    </div>
                    
                    <c:if test="${isManager == true}">
                        <div class="dropdown dropdown--icon persons__button">
                            <div class="dropdown__button button button--icon button--settings button--rotate">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                </svg>
                            </div>
                            <div class="dropdown__menu">
                                <ul class="dropdown__list">
                                    <li class="dropdown__item">
                                        <a href="${editPersonUrl}">Редактирование</a>
                                        <a href="#" class="dropdown__item-link--danger" data-remove-person="${p.id}">Удалить</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                </div>
            </c:forEach>
        </div>
    </c:if>

    <div class="division-task">
      <h5 class="division-task__title">Задачи отдела:</h5>
      <p>${division.description}</p>
    </div>
</div>

<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/singleDivision.js"/>"></script>