<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<c:set var="isManager" scope="request" value="${false}/"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>

<script type="text/javascript">
    orgStruct = {
        cr: '${cr}',
        redirectUrl: '${urlRedirectMain}',
        adminsHeaders: {
            '${headerAppId}': '${secretAppIdHeader}',
        },
        currentNodeId: "${nodeId}",
        organId: "${organ.id}",
        dialog: null,
    };
</script>

<portlet:renderURL var="createMember">
    <portlet:param name="action" value="createPerson"/>
    <portlet:param name="role" value="MEMBER"/>
    <portlet:param name="organId" value="${organ.id}"/>
</portlet:renderURL>

<portlet:renderURL var="editOrgan">
    <portlet:param name="action" value="renderAddOrgan"/>
    <portlet:param name="idOrgan" value="${organ.id}"/>
</portlet:renderURL>

<section class="org-page">
    <c:forEach var="role" items="${roles}">
        <c:if test="${role == 'MANAGER'}">
            <div class="org-panel">
                <div class="org-panel__add">
                    <a href="${createMember}" class="button button--primary button--plus">Добавить участников</a>
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
                                    <a href="${editOrgan}">Редактировать</a>
                                </li>

                                <li class="dropdown__item">
                                    <button class="danger js-remove-action" data-id="${organ.id}" data-url="${cr}/rest-api/orgStructure/deleteOrgan.action">Удалить</button>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>

    <a href="${urlRedirectMain}" class="_link">К руководству</a>
    <br>
    <br>

    <div class="org-columns">
        <main class="org-columns__main">
            <div class="org-columns__title org-columns__title--mb">
                <h2>${organ.name}</h2>
            </div>

            <div class="org-person">
                <c:if test="${not empty organ.description}">
                    <div class="org-person__row">
                        <div class="row__text static">
                                ${organ.description}
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.people}">
                    <div class="org-person__row">
                            <div class="row__title">
                                <h3>Участники</h3>
                            </div>
                            <div class="row__body">
                                <button class="row__toggle" data-close="Скрыть">
                                    <span>Показать</span>
        
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                xlink:href="${cr}/sprite/sprite.svg#arrow-toggle"></use>
                                    </svg>
                                </button>
        
                                <div class="row__text row__text--toggle">
                                    <div class="org-members">
                                        <c:forEach items="${organ.people}" var="ppl">
                                            <portlet:renderURL var="personPage">
                                                <portlet:param name="action" value="renderPerson"/>
                                                <portlet:param name="id" value="${ppl.id}"/>
                                            </portlet:renderURL>

                                            <div class="org-members__row">
                                                <div class="org-members__info">
                                                    <div class="member__img">
                                                        <c:choose>
                                                            <c:when test="${ppl.attachments != null && !ppl.attachments.isEmpty()}">
                                                                <img src="${cr}image-api/view.ido?uuid=${ppl.attachments[ppl.attachments.size() - 1].uuid}">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <svg>
                                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                        xlink:href="${cr}/sprite/sprite.svg#icon-member"></use>
                                                                </svg>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                    <div class="member__text">
                                                        <a class="member__title" href="${personPage}">${ppl.name}</a>
                                                        <div class="member__desc">${ppl.position}</div>
                                                    </div>
                                                </div>
                                                <ul class="org-members__contact">
                                                    <c:if test="${ppl.phone != null}">
                                                        <li>
                                                            <div class="_icon">
                                                                <svg>
                                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                    xlink:href="${cr}/sprite/sprite.svg#icon-phone"></use>
                                                                </svg>
                                                            </div>
                                                            <div class="_text">
                                                                <a href="tel:${ppl.phone}" class="link-text">
                                                                    ${ppl.phone}
                                                                </a>
                                                            </div>
                                                        </li>
                                                    </c:if>
                                                    <c:if test="${ppl.email != null}">
                                                        <li>
                                                            <div class="_icon">
                                                                <svg>
                                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                    xlink:href="${cr}/sprite/sprite.svg#icon-email"></use>
                                                                </svg>
                                                            </div>
                                                            <div class="_text">
                                                                <a href="mailto:${ppl.email}" class="link-text">${ppl.email}</a>
                                                            </div>
                                                        </li>
                                                    </c:if>
                                                </ul>

                                                <c:if test="${isManager == true}">
                                                    <portlet:renderURL var="editMember">
                                                        <portlet:param name="action" value="createPerson"/>
                                                        <portlet:param name="role" value="MEMBER"/>
                                                        <portlet:param name="organId" value="${organ.id}"/>
                                                        <portlet:param name="id" value="${ppl.id}"/>
                                                    </portlet:renderURL>

                                                    <div class="org-members__action">
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
                                                                        <a href="${editMember}">Редактировать</a>
                                                                    </li>
            
                                                                    <li class="dropdown__item">
                                                                        <a href="#" class="danger" data-remove-person="${ppl.id}">Удалить</a>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:if>

                                            </div>
                                        </c:forEach>
        
                                    </div>
                                </div>
                            </div>
                        </div>
                </c:if>

                <c:if test="${not empty attachOrgan}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Документы</h3>
                        </div>

                        <div class="row__body">
                            <div class="org-doc">
                                <c:forEach var="doc" items="${attachOrgan}">
                                    <a href="${cr}/doc-api/view.doc?uuid=${doc.uuid}" class="org-doc__row" download>
                                        <div class="org-doc__img">
                                            <svg>
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                     xlink:href="${cr}/sprite/sprite.svg#${doc.extName}"></use>
                                            </svg>
                                        </div>
                                        <div class="org-doc__desc">
                                            <div class="org-doc__name">
                                                ${doc.fileName}
                                            </div>
                                            <div class="org-doc__size">${doc.extName}, ${doc.size}</div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.decision}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Принятые решения по рассмотрению проектов и иных документов</h3>
                        </div>
                        <div class="row__body">
                            <div class="row__text static">
                                    ${organ.decision}
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.schedule}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>График заседаний</h3>
                        </div>
                        <div class="row__body">
                            <div class="row__text static">
                                    ${organ.schedule}
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.conditions}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Условия присутствия на заседании</h3>
                        </div>
                        <div class="row__body">
                            <div class="row__text static">
                                    ${organ.conditions}
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.meeting}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Заседания</h3>
                        </div>
                        <div class="row__body">
                            <button class="row__toggle" data-close="Скрыть">
                                <span>Показать</span>

                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xlink:href="${cr}/sprite/sprite.svg#arrow-toggle"></use>
                                </svg>
                            </button>

                            <div class="row__text row__text--toggle static">
                                    ${organ.meeting}
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.plan}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>План работы</h3>
                        </div>
                        <div class="row__body">
                            <div class="row__text static">
                                    ${organ.plan}
                            </div>
                        </div>
                    </div>
                </c:if>

                <c:if test="${not empty organ.details}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Контактные данные</h3>
                        </div>
                        <div class="row__body">
                            <div class="row__text static">
                                    ${organ.details}
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty organ.situation}">
                    <div class="org-person__row">
                        <div class="row__title">
                            <h3>Положение об общественном совете при органе власти</h3>
                        </div>
                        <div class="row__body">
                            <button class="row__toggle" data-close="Скрыть">
                                <span>Показать</span>

                                <svg>
                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                         xlink:href="${cr}/sprite/sprite.svg#arrow-toggle"></use>
                                </svg>
                            </button>

                            <div class="row__text row__text--toggle static">
                                    ${organ.situation}
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </main>

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
                                <c:if test="${organSidebar.id != organ.id}">
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
    </div>
</section>

<script src="<c:url value="/js/gost/main.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/organ.js"/>"></script>