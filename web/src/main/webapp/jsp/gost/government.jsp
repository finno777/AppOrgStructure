<%--
  Created by IntelliJ IDEA.
  User: Martynov.I
  Date: 10.08.2017
  Time: 12:28
--%>
<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>
<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<portlet:renderURL var="divisionsURL">
    <portlet:param name="action" value="divisionsView" />
</portlet:renderURL>

<portlet:renderURL var="editGovernmentURL">
    <portlet:param name="action" value="editGovernmentView" />
</portlet:renderURL>

<portlet:renderURL var="editListGovernmentURL">
    <portlet:param name="action" value="editListGovernmentView" />
</portlet:renderURL>

<portlet:renderURL var="singledDivisionsURL">
    <portlet:param name="action" value="singledDivisionsView" />
</portlet:renderURL>


<c:set var="isManager" scope="request" value="${false}"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>


<script type="text/javascript">
    var managementApp = {
        cr: '${pageContext.request.contextPath}/',
        currentPage: 'FORM',
        application: {
            secret_app_id: '${secretAppIdHeader}'
        },
        governmentsEmpty: "${governments.isEmpty()}",
        roles: "${roles.toString()}",
        governments: "${governments}"
    };
</script>

<div class="governments government-wrap">
    <div class="governments__header">
        <c:if test="${isManager == true}">
            <div class="governments__header-buttons">
                <a href="${editGovernmentURL}" class="button button--primary">
                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#plus"></use></svg>
                    Добавить управление
                </a>
            </div>
        </c:if>

        <!-- <div class="dropdown dropdown--icon">
            <div class="dropdown__button button button--icon button--settings button--rotate">
                <svg>
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                </svg>
            </div>
            <div class="dropdown__menu">
                <ul class="dropdown__list">
                    <li class="dropdown__item">
                        <a href="javascript:void(0)">Порядок управлений</a>
                    </li>
                </ul>
            </div>
        </div> -->
    </div>

    <div class="governments__list">

        <c:forEach items="${governments}" var="item">
            
            <portlet:renderURL var="editGoverURL">
                <portlet:param name="action" value="editGovernmentView"/>
                <portlet:param name="id" value="${item.id}"/>
            </portlet:renderURL>

            <portlet:renderURL var="viewGoverURL">
                <portlet:param name="action" value="singleGovernmentView"/>
                <portlet:param name="id" value="${item.id}"/>
            </portlet:renderURL>

            <div class="governments__item government">
                <div class="government__icon">
                    <c:choose>
                        <c:when test="${item.uuid.length() > 1}">
                            <c:catch var="e">
                                <c:import url="http://localhost${item.uuid}" />
                            </c:catch>
                            <c:if test="${!empty e}">
                                <div class="group__icon--empty">
                                    <span hidden>Error: ${e.message}</span>
                                </div>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="government__icon--empty"></div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="government__info">
                    <a class="government__info-title" href="${viewGoverURL}">${item.title}</a>
                    <div class="government__info-label">
                        <c:if test="${item.person != null}">
                            ${item.person.person.name}
                        </c:if>
                    </div>
                </div>
                <div class="government__right">
                    <c:if test="${isManager == true}">
                        <div class="dropdown dropdown--icon admin-groups-menu">
                            <div class="dropdown__button button button--icon button--settings button--rotate">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                </svg>
                            </div>
                            <div class="dropdown__menu">
                                <ul class="dropdown__list">
                                    <li class="dropdown__item">
                                        <a href="${editGoverURL}">Редактировать</a>
                                        <a href="#" data-action="delete" data-id="${item.id}" class="dropdown__item-link--danger">Удалить</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/governments.js"/>"></script>