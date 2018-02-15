<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>

<div class="org-supervised">
    <div class="org-supervised__title js-acco-mobile">
        <h3>Курируемые структурные подразделения</h3>
        <div class="_icon">
            <svg>
                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                     xlink:href="${cr}/sprite/sprite.svg#icon-acco-small"></use>
            </svg>
        </div>
    </div>

    <ul class="org-supervised__list">
        <li>
            <div class="_icon">
                <svg>
                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                         xlink:href="${cr}/sprite/sprite.svg#structure-admin"></use>
                </svg>
            </div>
            <div class="_text">
                <h4>Контрольно-ревизионное управление</h4>
            </div>
        </li>

        <li>
            <div class="_icon">
                <svg>
                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                         xlink:href="${cr}/sprite/sprite.svg#structure-secret"></use>
                </svg>
            </div>
            <div class="_text">
                <h4>Режимно-серкретный отдел</h4>
                <p>В части региональной политики Федерального агентства</p>
            </div>
        </li>
    </ul>
</div>