<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="hasManager" value="false"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<c:forEach var="user" items="${people}">
    <c:if test="${user.role == 'MANAGER'}">
        <c:set var="hasManager" value="true"/>
    </c:if>
</c:forEach>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
    <portlet:param name="editMode" value="false"/>
</portlet:renderURL>

<style>
    .org-columns__sidebar {
		display: none;
    }
</style>

<script type="text/javascript">
    orgStruct = {
        cr: '${cr}',
        redirectUrl: '${urlRedirectMain}',
        adminsHeaders: {
            '${headerAppId}': '${secretAppIdHeader}',
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        currentNodeId: "${nodeId}"
    };
</script>

<portlet:renderURL var="createManager">
    <portlet:param name="action" value="createPerson"/>
    <portlet:param name="role" value="MANAGER"/>
</portlet:renderURL>

<portlet:renderURL var="createDeputy">
    <portlet:param name="action" value="createPerson"/>
    <portlet:param name="role" value="DEPUTY"/>
</portlet:renderURL>

<portlet:renderURL var="createOrgan">
    <portlet:param name="action" value="renderAddOrgan"/>
</portlet:renderURL>

<portlet:renderURL var="editView">
    <portlet:param name="action" value="redirectMain"/>
    <portlet:param name="editMode" value="true"/>
</portlet:renderURL>

<c:choose>
    <c:when test="${editMode}">
        <jsp:include page="blocks/settings.jsp"/>

        <script src="<c:url value="/js/gost/settings.js"/>"></script>
    </c:when>

    <c:otherwise>
        <jsp:include page="blocks/air-nav.jsp"/>

        <section class="org-page">
            <div class="org-columns">
                <main class="org-columns__main">
                    <c:if test="${fn:contains(roles, 'ALL') || fn:contains(roles, 'MANAGER')}">
                        <div class="org-panel">
                            <div class="org-panel__add">
                                <div class="dropdown">
                                    <button class="dropdown__button button button--primary button--plus">Добавить
                                    </button>
                                    <div class="dropdown__menu">
                                        <ul class="dropdown__list">
                                            <c:choose>
                                                <c:when test="${hasManager}">
                                                    <li class="dropdown__item">
                                                        <a href="${createDeputy}">Заместителя</a>
                                                    </li>

                                                    <li class="dropdown__item">
                                                        <a href="${createOrgan}">Органы управления</a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="dropdown__item">
                                                        <a href="${createManager}">Руководителя</a>
                                                    </li>

                                                    <li class="dropdown__item">
                                                        <a href="${createDeputy}">Заместителя</a>
                                                    </li>

                                                    <li class="dropdown__item">
                                                        <a href="${createOrgan}">Органы управления</a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="org-panel__edit">
                                <div class="dropdown dropdown--icon dropdown--fixed">
                                    <button class="dropdown__button button button--icon button--rotate">
                                        <svg>
                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                 xlink:href="${cr}/sprite/sprite.svg#icon-settings"></use>
                                        </svg>
                                    </button>
                                    <div class="dropdown__menu">
                                        <ul class="dropdown__list">
                                            <li class="dropdown__item">
                                                <a href="${editView}">Редактировать</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <div class="org-list">
                        <jsp:include page="blocks/org-profile.jsp"/>
                    </div>

                    <c:if test="${not empty organs}">
                        <jsp:include page="blocks/other-authorities.jsp"/>
                    </c:if>
                </main>
            </div>
        </section>

        <script src="<c:url value="/js/gost/main.js"/>"></script>
    </c:otherwise>
</c:choose>