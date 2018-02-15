<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>

<div class="other-authorities" id="org-organs">
    <div class="other-authorities__title">
        <h2>Другие органы управления</h2>
    </div>

    <div class="other-authorities__body">
        <c:forEach var="organs" items="${organs}">
            <portlet:renderURL var="urlOrgan">
                <portlet:param name="action" value="renderOrgan"/>
                <portlet:param name="idOrgan" value="${organs.id}"/>
            </portlet:renderURL>

            <a href="${urlOrgan}" class="other-authorities__row">
                <div class="_icon">
                    <svg>
                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                             xlink:href="${cr}/sprite/sprite.svg#organ${organs.id}"></use>
                    </svg>
                </div>
                <div class="_text">${organs.name}</div>
            </a>
        </c:forEach>
    </div>
</div>