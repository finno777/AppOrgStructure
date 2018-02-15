<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>

<div class="org-profile">
    <div class="org-profile__main">
        <div class="org-profile__img">
            <c:choose>
                <c:when test="${attachPerson != null && !attachPerson.isEmpty()}">
                    <img src="${cr}/image-api/view.ido?uuid=${attachPerson[attachPerson.size() - 1].uuid}&imageHeight=230&imageWidth=350" alt="">
                </c:when>
                <c:otherwise>
                    <div class="group__icon--empty"></div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="org-profile__desc">
            <div class="desc__title">
                <h2>${person.name}</h2>
                <p>${person.position}</p>
            </div>

            <ul class="desc__options">
                <c:if test="${not empty person.phone}">
                    <li>
                        <ul class="desc__options desc__options--line">
                            <c:if test="${not empty person.phone}">
                                <li>
                                    <div class="_icon">
                                        <svg>
                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                xlink:href="${cr}/sprite/sprite.svg#icon-phone"></use>
                                        </svg>
                                    </div>
                                    <div class="_text">
                                        <c:forEach var="item" items="${fn:split(person.phone,', ')}">
                                            <a href="tel:${item}" class="link-text">${item}</a>;
                                        </c:forEach>
                                    </div>
                                </li>
                            </c:if>

                            <c:forEach var="attribute" items="${attribute}">
                                <c:if test="${attribute.key  == 'Факс'}">
                                    <li>
                                        <div class="_icon">
                                            <svg>
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                     xlink:href="${cr}/sprite/sprite.svg#icon-fax"></use>
                                            </svg>
                                        </div>
                                        <div class="_text">${attribute.value}</div>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${not empty person.email}">
                    <li>
                        <div class="_icon">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                     xlink:href="${cr}/sprite/sprite.svg#icon-email"></use>
                            </svg>
                        </div>
                        <div class="_text"><a href="mailto:${person.email}" class="link-text">${person.email}</a></div>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</div>