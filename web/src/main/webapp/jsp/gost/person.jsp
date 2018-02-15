<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="hasBiography" scope="request" value="false"/>
<jsp:useBean id="photos" class="java.util.LinkedHashMap"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
    <portlet:param name="editMode" value="false"/>
</portlet:renderURL>


<script type="text/javascript">
    orgStruct = {
        redirectUrl: '${urlRedirectMain}',
        cr: '${cr}',
        adminsHeaders: {
            '${headerAppId}': '${headerAppIdValue}',
            Accept: 'application/js',
            'Content-Type': 'application/json',
        },
        currentNodeId: "${nodeId}",
        dialog: null,
    };
</script>

<c:forEach var="item" items="${attribute}">
    <c:if test="${item.key != 'Факс' and item.key != 'Обязанности' and item.key != 'Полномочия' and item.key != 'person'}">
        <c:set var="hasBiography" scope="request" value="true"/>
    </c:if>
</c:forEach>

<c:forEach var="item" items="${allAttachPerson}">
    <c:set target="${photos}" property="${item.person.id.toString()}" value="${[item.attachment]}"/>
</c:forEach>

<section class="org-page">
    <portlet:renderURL var="createAdviser">
        <portlet:param name="action" value="createPerson"/>
        <portlet:param name="role" value="ADVISER"/>
        <portlet:param name="managerId" value="${person.id}"/>
    </portlet:renderURL>

    <portlet:renderURL var="createOrgan">
        <portlet:param name="action" value="renderAddOrgan"/>
    </portlet:renderURL>

    <portlet:renderURL var="urlEdit">
        <portlet:param name="action" value="createPerson"/>
        <portlet:param name="id" value="${person.id}"/>
        <portlet:param name="role" value="${person.role}"/>
    </portlet:renderURL>

    <div class="org-columns">
        <main class="org-columns__main">
            <div class="org-columns__back">
                <a href="${urlRedirectMain}" class="_link">К руководству</a>
            </div>

            <c:if test="${fn:contains(roles, 'ALL') || fn:contains(roles, 'MANAGER')}">
                <div class="org-panel">
                    <c:if test="${person.role == 'MANAGER'}">
                        <div class="org-panel__add">
                            <a href="${createAdviser}" class="button button--primary button--plus">Добавить
                                советника</a>
                        </div>
                    </c:if>
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
                                        <a href="${urlEdit}">Редактировать</a>
                                    </li>

                                    <li class="dropdown__item">
                                        <button class="danger js-remove-action" data-id="${person.id}"
                                                data-url="${cr}/rest-api/orgStructure/deletePerson.action">Удалить
                                        </button>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="org-person">
                <div class="org-person__row">
                    <jsp:include page="blocks/org-person.jsp"/>
                </div>

                <c:if test="${hasBiography}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Биография</h3>
                        </div>

                        <div class="row__body">
                            <button class="row__toggle" data-close="Скрыть" role="button">
                                <span>Показать</span>

                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xlink:href="${cr}/sprite/sprite.svg#arrow-toggle"></use>
                                </svg>
                            </button>

                            <div class="row__text row__text--toggle static">
                                <c:forEach var="item" items="${attribute}">
                                    <c:if test="${item.key != 'Факс' and item.key != 'Обязанности' and item.key != 'Полномочия' and item.key != 'person'}">
                                        <h4>${item.key}</h4>

                                        <div>${item.value}</div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:forEach var="item" items="${attribute}">
                    <c:if test="${item.key == 'Обязанности' or item.key == 'Полномочия'}">
                        <div class="org-person__row">
                            <div class="row__title">
                                <h3>${item.key}</h3>
                            </div>
                            <div class="row__body">
                                <button class="row__toggle" data-close="Скрыть" role="button">
                                    <span>Показать</span>

                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                             xlink:href="${cr}/sprite/sprite.svg#arrow-toggle"></use>
                                    </svg>
                                </button>

                                <div class="row__text row__text--toggle static">
                                    <div>${item.value}</div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>


                <c:if test="${not empty person.curatorGovernments}">
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
                                <c:forEach var="item" items="${person.curatorGovernments}">
                                    <div class="group">
                                        <div class="government-section__list-item group">
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

            <c:if test="${not empty adviser}">
                <div class="org-advisers">
                    <div class="org-advisers__title">
                        <h3>Советники</h3>
                    </div>

                    <div class="org-advisers__list">
                        <c:forEach var="adviserItem" items="${adviser}">
                            <c:set var="idAdviser" value="${adviserItem.id}"/>

                            <portlet:renderURL var="urlEditAdviser">
                                <portlet:param name="action" value="createPerson"/>
                                <portlet:param name="id" value="${adviserItem.id}"/>
                                <portlet:param name="role" value="${adviserItem.role}"/>
                            </portlet:renderURL>

                            <portlet:renderURL var="urlAdviser">
                                <portlet:param name="action" value="renderPerson"/>
                                <portlet:param name="id" value="${adviserItem.id}"/>
                            </portlet:renderURL>

                            <div class="org-advisers__row">
                                <div class="row__info">
                                    <div class="row__img">
                                        <a href="${urlAdviser}">
                                            <c:forEach var="photo" items="${photos.get(idAdviser.toString())}">
                                                <img src="${cr}/image-api/view.ido?uuid=${photo.uuid}&imageHeight=230&imageWidth=350" alt="">
                                            </c:forEach>
                                        </a>
                                    </div>

                                    <div class="row__text">
                                        <div class="row__title">
                                            <a href="${urlAdviser}">${adviserItem.name}</a>
                                        </div>
                                        <div class="row__desc">${adviserItem.position}</div>
                                    </div>
                                </div>
                                <div class="row__action">
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
                                                    <a href="${urlEditAdviser}">Редактировать</a>
                                                </li>

                                                <li class="dropdown__item">
                                                    <button class="danger js-remove-action" data-id="${adviserItem.id}"
                                                            data-url="${cr}/rest-api/orgStructure/deletePerson.action">
                                                        Удалить
                                                    </button>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </main>

        <aside class="org-columns__sidebar">
            <div class="sidemenu">
                <c:if test="${not empty managers and person.role != 'MANAGER'}">
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

                <c:if test="${not empty deputes and fn:length(deputes) > 1 or person.role == 'MANAGER' and fn:length(deputes) > 0}">
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

                <c:if test="${not empty organs}">
                    <div class="sidemenu__section">
                        <div class="sidemenu__title">Коллегиальные органы</div>
                        <ul class="sidemenu__list">
                            <c:forEach var="organSidebar" items="${organs}">
                                <portlet:renderURL var="renderOrgan">
                                    <portlet:param name="action" value="renderOrgan"/>
                                    <portlet:param name="idOrgan" value="${organSidebar.id}"/>
                                </portlet:renderURL>

                                <li><a href="${renderOrgan}">${organSidebar.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </div>
        </aside>
    </div>
</section>

<script src="<c:url value="/js/gost/main.js"/>"></script>