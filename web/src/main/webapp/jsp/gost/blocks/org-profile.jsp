<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="faxs" class="java.util.LinkedHashMap"/>
<jsp:useBean id="photos" class="java.util.LinkedHashMap"/>

<c:forEach var="itemAttr" items="${attributes}">
    <c:if test="${itemAttr.key == 'Факс'}">
        <c:set target="${faxs}" property="${itemAttr.person.id.toString()}" value="${itemAttr.value}"/>
    </c:if>
</c:forEach>

<c:forEach var="item" items="${allAttachPerson}">
    <c:set target="${photos}" property="${item.person.id.toString()}" value="${[item.attachment]}"/>
</c:forEach>

<c:if test="${not empty managers}">
    <c:forEach var="manager" items="${managers}">
        <c:set var="idManager" value="${manager.id}" />

        <div class="org-profile" id="user-${manager.id}">
            <div class="org-profile__main">
                <div class="org-profile__img">
                    <c:if test="${not empty manager.attachments}">
                        <img src="${cr}/image-api/view.ido?uuid=${manager.attachments[manager.attachments.size() - 1].uuid}&imageHeight=230&imageWidth=350" alt="">
                    </c:if>
                </div>

                <div class="org-profile__desc">
                    <div class="desc__title">
                        <h2>${manager.name}</h2>
                        <p>${manager.position}</p>
                    </div>

                    <div class="desc__btn">
                        <portlet:renderURL var="urlMore">
                            <portlet:param name="action" value="renderPerson"/>
                            <portlet:param name="id" value="${manager.id}"/>
                        </portlet:renderURL>

                        <div class="btn__inner">
                            <a href="${urlMore}" class="button button--icon-left">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xlink:href="${cr}/sprite/sprite.svg#icon-more"></use>
                                </svg>
                                <span>Подробнее</span>
                            </a>
                        </div>
                    </div>

                    <ul class="desc__options">
                        <c:if test="${not empty manager.phone}">
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
                                                <c:forEach var="item" items="${fn:split(manager.phone,', ')}">
                                                    <a href="tel:${item}" class="link-text">${item}</a>;
                                                </c:forEach>
                                            </div>
                                        </li>
                                    </c:if>

                                    <c:if test="${faxs.containsKey(idManager.toString())}">
                                        <li>
                                            <div class="_icon">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                         xlink:href="${cr}/sprite/sprite.svg#icon-fax"></use>
                                                </svg>
                                            </div>
                                            <div class="_text"> ${faxs.get(idManager.toString())}</div>
                                        </li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>

                        <c:if test="${not empty manager.email}">
                            <li>
                                <div class="_icon">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                             xlink:href="${cr}/sprite/sprite.svg#icon-email"></use>
                                    </svg>
                                </div>
                                <div class="_text"><a href="mailto:${manager.email}" class="link-text"></a></div>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>

            <c:if test="${not empty manager.curatorGovernments}">
                <div class="org-person__row">
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

                        <div class="government-section__list">
                            <c:forEach var="item" items="${manager.curatorGovernments}">
                                <div class="government-section__list-item">
                                    <div class="group">
                                        <div class="group__icon">
                                            <c:choose>
                                                <c:when test="${item.government.uuid.length() > 1}">
                                                    <c:catch var="e">
                                                        <c:import url="http://localhost${item.government.uuid}" />
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
                                        <div class="group__info">
                                            <h4 class="group__info-title">
                                                <a href="${item.government.link}">${item.government.title}</a>
                                            </h4>
                                            <div class="group__info-desc">${item.description}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                </div>
            </c:if>
        </div>
    </c:forEach>
</c:if>

<c:if test="${not empty deputes}">
    <c:forEach var="depute" items="${deputes}">
        <c:set var="idDepute" value="${depute.id}" />

        <div class="org-profile" id="user-${depute.id}">
            <div class="org-profile__main">
                <div class="org-profile__img">
                    <c:if test="${not empty depute.attachments}">
                        <img src="${cr}/image-api/view.ido?uuid=${depute.attachments[depute.attachments.size() - 1].uuid}&imageHeight=230&imageWidth=350" alt="">
                    </c:if>
                </div>

                <div class="org-profile__desc">
                    <div class="desc__title">
                        <h2>${depute.name}</h2>
                        <p>${depute.position}</p>
                    </div>

                    <div class="desc__btn">
                        <portlet:renderURL var="urlMore">
                            <portlet:param name="action" value="renderPerson"/>
                            <portlet:param name="id" value="${depute.id}"/>
                        </portlet:renderURL>

                        <div class="btn__inner">
                            <a href="${urlMore}" class="button button--icon-left">
                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xlink:href="${cr}/sprite/sprite.svg#icon-more"></use>
                                </svg>
                                <span>Подробнее</span>
                            </a>
                        </div>
                    </div>

                    <ul class="desc__options">
                        <c:if test="${not empty depute.phone}">
                            <li>
                                <ul class="desc__options desc__options--line">
                                    <li>
                                        <div class="_icon">
                                            <svg>
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                     xlink:href="${cr}/sprite/sprite.svg#icon-phone"></use>
                                            </svg>
                                        </div>
                                        <div class="_text"><a href="tel:${depute.phone}" class="link-text">${depute.phone}</a></div>
                                    </li>

                                    <c:if test="${faxs.containsKey(idDepute.toString())}">
                                        <li>
                                            <div class="_icon">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                         xlink:href="${cr}/sprite/sprite.svg#icon-fax"></use>
                                                </svg>
                                            </div>
                                            <div class="_text"> ${faxs.get(idDepute.toString())}</div>
                                        </li>
                                    </c:if>
                                </ul>
                            </li>
                        </c:if>

                        <c:if test="${not empty depute.email}">
                            <li>
                                <div class="_icon">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                             xlink:href="${cr}/sprite/sprite.svg#icon-email"></use>
                                    </svg>
                                </div>
                                <div class="_text"><a href="mailto:${depute.email}" class="link-text"></a></div>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>

            <c:if test="${not empty depute.curatorGovernments}">
                <div class="org-person__row">
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

                        <div class="government-section__list">
                            <c:forEach var="item" items="${depute.curatorGovernments}">
                                <div class="government-section__list-item group">
                                    <div class="group">
                                        <div class="group__icon">
                                            <c:choose>
                                                <c:when test="${item.government.uuid.length() > 1}">
                                                    <c:catch var="e">
                                                        <c:import url="http://localhost${item.government.uuid}" />
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
                                        <div class="group__info">
                                            <h4 class="group__info-title">
                                                <a href="${item.government.link}">${item.government.title}</a>
                                            </h4>
                                            <div class="group__info-desc">${item.description}</div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                </div>
            </c:if>
        </div>
    </c:forEach>
</c:if>