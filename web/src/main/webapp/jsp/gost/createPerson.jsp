<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="hasBiography" scope="request" value="false"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<script type="text/javascript">
    orgStruct = {
        cr: '${cr}',
        redirectUrl: '${urlRedirectMain}',
        role: '${role}',
        personId: '${person.id}',
        person: `${person}`,
        managerId: '${managerId}',
        organId: '${organId}',
        appId: '${appId}',
        portalName: '${portalName}',
        appName: '${appName}',
        adminsHeaders: {
            '${headerAppId}': '${secretAppIdHeader}',
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        currentNodeId: '${nodeId}',
        dialog: null
    };
</script>

<section class="page-org">
    <div class="org-columns">
        <main class="org-columns__main">
            <c:choose>
                <c:when test="${role == 'MANAGER'}">
                    <jsp:include page="blocks/form-manager.jsp"/>
                </c:when>

                <c:when test="${role == 'DEPUTY'}">
                    <jsp:include page="blocks/form-deputy.jsp"/>
                </c:when>

                <c:when test="${role == 'ADVISER'}">
                    <jsp:include page="blocks/form-adviser.jsp"/>
                </c:when>

                <c:otherwise>
                    <jsp:include page="blocks/form-member.jsp"/>
                </c:otherwise>
            </c:choose>
        </main>

        <c:if test="${role == 'MEMBER'}">
            <aside class="org-columns__sidebar">
                <div class="sidemenu">
                    <c:if test="${not empty managers}">
                        <div class="sidemenu__section">
                            <div class="sidemenu__title">Руководитель</div>
                            <ul class="sidemenu__list">
                                <c:forEach var="manager" items="${managers}">
                                    <portlet:renderURL var="renderManager">
                                        <portlet:param name="action" value="renderPerson"/>
                                        <portlet:param name="id" value="${manager.id}"/>
                                    </portlet:renderURL>

                                    <li><a href="${renderManager}">${manager.name}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <c:if test="${not empty deputes}">
                        <div class="sidemenu__section">
                            <div class="sidemenu__title">Заместители</div>
                            <ul class="sidemenu__list">
                                <c:forEach var="depute" items="${deputes}">
                                    <c:if test="${depute.id != person.id}">
                                        <portlet:renderURL var="renderDepute">
                                            <portlet:param name="action" value="renderPerson"/>
                                            <portlet:param name="id" value="${depute.id}"/>
                                        </portlet:renderURL>

                                        <li><a href="${renderDepute}">${depute.name}</a></li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <c:if test="${not empty organs and fn:length(organs) > 1}">
                        <div class="sidemenu__section">
                            <div class="sidemenu__title">Коллегиальные органы</div>
                            <ul class="sidemenu__list">
                                <c:forEach var="organSidebar" items="${organs}">
                                    <c:if test="${organSidebar.id != organId}">
                                        <portlet:renderURL var="renderOrgan">
                                            <portlet:param name="action" value="renderOrgan"/>
                                            <portlet:param name="idOrgan" value="${organSidebar.id}"/>
                                        </portlet:renderURL>

                                        <li><a href="${renderOrgan}">${organSidebar.name}</a></li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </aside>
        </c:if>
    </div>
</section>

<script src="<c:url value="/js/gost/form.js?ver=125233"/>"></script>