<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>

<div id="orgNav" class="anchors-list__wrap">
    <ul class="anchors-list">
        <c:forEach var="user" items="${people}" varStatus="loop">
            <c:if test="${user.role == 'MANAGER' or user.role == 'DEPUTY'}">
                <li class="anchors-list__item">
                    <div class="dropdown dropdown--icon">
                        <a class="dropdown__button anchors-list__link button button--icon ${loop.count == 1 ? 'active' : ''}"
                           href="#user-${user.id}">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                     xlink:href="${cr}/sprite/sprite.svg#user"></use>
                            </svg>
                        </a>
                        <div class="dropdown__menu">
                            <ul class="dropdown__list">
                                <li class="dropdown__item">
                                        ${user.name}
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
            </c:if>
        </c:forEach>

        <c:if test="${not empty organs}">
            <li class="anchors-list__item">
                <div class="dropdown dropdown--icon">
                    <a class="dropdown__button anchors-list__link button button--icon"
                       href="#org-organs">
                        <svg>
                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                 xlink:href="${cr}/sprite/sprite.svg#organs"></use>
                        </svg>
                    </a>
                    <div class="dropdown__menu">
                        <ul class="dropdown__list">
                            <li class="dropdown__item">
                                Другие органы управления
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
        </c:if>
    </ul>
</div>