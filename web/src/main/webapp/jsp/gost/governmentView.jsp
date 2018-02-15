<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<portlet:renderURL var="redirectMainURL">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<portlet:renderURL var="createDivisionURL">
    <portlet:param name="action" value="editDivisionsView" />
    <portlet:param name="governmentId" value="${government.id}"/>
</portlet:renderURL>

<portlet:renderURL var="addGovernmentPersonURL">
    <portlet:param name="action" value="addGovernmentPersonView" />
    <portlet:param name="id" value="${government.id}"/>
</portlet:renderURL>

<portlet:renderURL var="editGoverURL">
    <portlet:param name="action" value="editGovernmentView"/>
    <portlet:param name="id" value="${government.id}"/>
</portlet:renderURL>

<c:set var="isManager" scope="request" value="${false}/"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>


<script type="text/javascript">
  var managementApp = {
    cr: '${pageContext.request.contextPath}/',
    currentPage: 'FORM',
    application: {
      secret_app_id: '${secretAppIdHeader}'
    },
    redirect: 'redirectMainURL',
    government: `${government}`
  };
</script>

<a href="${redirectMainURL}" class="government__link">← <span>К управлениям</span></a>

<div class="government-view government-wrap">
    <c:if test="${isManager == true}">
        <div class="governments__header">
            <div class="governments__header-buttons">
                <div class="toggler__wrap government__toggler">
                    <div class="toggler toggler--as_list">
                        <button class="toggler__button button button--primary">
                            <svg class="toggler__plus"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#plus"></use></svg>
                            Добавить
                            <span class="toggler__arrow">
                                <svg class="toggler__arrow-icon"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#arrow_down"></use></svg>
                            </span>
                        </button>
            
                        <div class="toggler__menu">
                            <ul class="toggler__list">
                                <li class="toggler__item">
                                    <a href="${addGovernmentPersonURL}">Руководителя</a>
                                </li>
            
                                <li class="toggler__item">
                                    <a href="${createDivisionURL}">Отдел</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <div class="dropdown dropdown--icon">
                <div class="dropdown__button button button--icon button--settings button--rotate">
                    <svg>
                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                    </svg>
                </div>
                <div class="dropdown__menu">
                    <ul class="dropdown__list">
                        <li class="dropdown__item">
                            <a href="${editGoverURL}">Редактировать</a>
                            <a href="javascript:void(0)"  data-delete-government="${government.id}" class="dropdown__item-link--danger">Удалить</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
    
    <h1 class="government-view__header">${government.title}</h1>

    <c:if test="${government.person != null}">
        
        <portlet:renderURL var="editGovernmentPersonURL">
            <portlet:param name="action" value="addGovernmentPersonView"/>
            <portlet:param name="personId" value="${government.person.id}"/>
            <portlet:param name="id" value="${government.id}"/>
        </portlet:renderURL>

        <div class="persons">
            <div class="persons__item">
                <div class="persons__img">
                    <c:choose>
                        <c:when test="${!government.person.person.attachments.isEmpty()}">
                            <img src="${cr}image-api/view.ido?uuid=${government.person.person.attachments[government.person.person.attachments.size() - 1].uuid}">
                        </c:when>
                        <c:otherwise>
                            <div class="persons__img--empty"></div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="persons__info">
                    <div class="persons__info-name">${government.person.person.name}</div>
                    <div class="persons__info-position">${government.person.position}</div>
                    <div class="persons__info-contacts">
                    <div class="persons__info-contacts-item">
                        <svg>
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#phone"></use>
                        </svg>

                        <c:choose>
                            <c:when test="${not empty government.person.person.phone}">
                                <span class="_text">
                                    <a href="tel:${government.person.person.phone}" class="link-text">${government.person.person.phone}</a>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span>-</span>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div class="persons__info-contacts-item">
                        <svg>
                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#email"></use>
                        </svg>
                        <c:choose>
                            <c:when test="${government.person.person.email != null}">
                                <span><a href="mailto:${government.person.person.email}" class="link-text">${government.person.person.email}</a></span>
                            </c:when>
                            <c:otherwise>
                                <span>-</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    </div>
                </div>
                <c:if test="${isManager == true}">
                    <div class="dropdown dropdown--icon persons__button">
                        <div class="dropdown__button button button--icon button--settings button--rotate">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                            </svg>
                        </div>
                        <div class="dropdown__menu">
                            <ul class="dropdown__list">
                                <li class="dropdown__item">
                                    <a href="${editGovernmentPersonURL}">Редактировать</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
    </c:if>

    <c:if test="${not empty government.divisions}">
        <div class="government-section">
            <h3 class="government-section__title">Отделы</h3>
    
            <div class="government-section__list">
                <c:forEach items="${government.divisions}" var="item">
                    <portlet:renderURL var="divisionPageUrl">
                        <portlet:param name="action" value="singledDivisionsView"/>
                        <portlet:param name="id" value="${item.id}"/>
                        <portlet:param name="governmentId" value="${government.id}"/>
                    </portlet:renderURL>
                    <portlet:renderURL var="editDivisionURL">
                        <portlet:param name="action" value="editDivisionsView"/>
                        <portlet:param name="id" value="${item.id}"/>
                        <portlet:param name="governmentId" value="${government.id}"/>
                    </portlet:renderURL>
    
                    <div class="government-section__list-item group" data-remove-container>
                        <div class="group__icon">
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
                                    <div class="group__icon--empty"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="group__info group__info--centered">
                            <h4 class="group__info-title">
                                <a href="${divisionPageUrl}">${item.title}</a>
                            </h4>
                        </div>
                        <c:if test="${isManager == true}">
                            <div class="group__right">
                                <div class="dropdown dropdown--icon">
                                    <div class="dropdown__button button button--icon button--settings button--rotate">
                                        <svg>
                                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                        </svg>
                                    </div>
                                    <div class="dropdown__menu">
                                        <ul class="dropdown__list">
                                            <li class="dropdown__item">
                                                <a href="${editDivisionURL}">Редактировать</a>
                                                <a href="#" data-delete-division="${item.id}" class="dropdown__item-link--danger">Удалить</a>
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
    </c:if>


    <c:if test="${not empty government.organizations || not empty government.groupOrganizations}">
        <div class="government-section">
            <h3 class="government-section__title">Подведомственные организации</h3>
    
            <div class="government-section__list">
                <c:forEach items="${government.organizations}" var="org">
                    <div class="government-section__list-item group">
                        <div class="group__icon">
                            <c:choose>
                                <c:when test="${org.uuid.length() > 1}">
                                    <c:catch var="e">
                                        <c:import url="http://localhost${org.uuid}" />
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
                                <a href="${org.link}">${org.title}</a>
                            </h4>
                            <div class="group__info-desc">${org.description}</div>
                        </div>
                    </div>
                </c:forEach>
        
                <c:forEach items="${government.groupOrganizations}" var="group">
                    <div class="government-section__list-item group">
                        <div class="group__icon">
                            <c:choose>
                                <c:when test="${group.uuid.length() > 1}">
                                    <c:catch var="e">
                                        <c:import url="http://localhost${group.uuid}" />
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
                                <a href="${group.link}">${group.title}</a>
                            </h4>
                            <div class="group__info-desc">${group.description}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty government.subGroupOrganizations}">
        <div class="government-section">
            <h3 class="government-section__title">Территориальные органы</h3>
    
            <div class="government-section__list">
                <c:forEach items="${government.subGroupOrganizations}" var="subGroup">
                    <div class="government-section__list-item group">
                        <div class="group__icon">
                            <div class="group__icon--empty"></div>
                        </div>
                        <div class="group__info group__info--centered">
                            <h4 class="group__info-title">
                                <a href="javascript:void(0)">${subGroup.title}</a>
                            </h4>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:if>

    <div class="government-section">
        <h3 class="government-section__title">Положение об управлении</h3>

        <a href="#" class="government__link expand" data-expand="#governmentDescription">
            <span>Показать</span>
            <svg class="toggler__arrow-icon"><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#arrow_down"></use></svg>
        </a>
        <div id="governmentDescription" style="display: none;">
            ${government.description}
        </div>
    </div>

    <c:if test="${not empty government.curators}">
        <div class="government-section">
            <h3 class="government-section__title">Кураторы</h3>
    
            <div class="government-section__list">
                <c:forEach items="${government.curators}" var="curator">
                    <div class="government-section__list-item group" data-remove-container>
                        <div class="group__icon">
                            <c:choose>
                                <c:when test="${!curator.person.attachments.isEmpty()}">
                                    <img src="${cr}image-api/view.ido?uuid=${curator.person.attachments[curator.person.attachments.size() - 1].uuid}">
                                </c:when>
                                <c:otherwise>
                                    <div class="group__icon--empty"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="group__info">
                            <h4 class="group__info-title">
                                <a href="${curator.link}">${curator.person.name}</a>
                            </h4>
                            <div class="group__info-desc">${curator.description}</div>
                        </div>
                        <c:if test="${isManager == true}">
                            <div class="group__right">
                                <div class="dropdown dropdown--icon">
                                    <div class="dropdown__button button button--icon button--settings button--rotate">
                                        <svg>
                                            <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                        </svg>
                                    </div>
                                    <div class="dropdown__menu">
                                        <ul class="dropdown__list">
                                            <li class="dropdown__item">
                                                <a href="#" data-delete-curator="${curator.id}" class="dropdown__item-link--danger">Удалить</a>
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
    </c:if>
    
</div>


<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/governments-view.js"/>"></script>