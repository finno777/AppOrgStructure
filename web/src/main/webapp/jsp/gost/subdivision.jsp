<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<portlet:defineObjects/>
<c:set var="cr" scope="request" value="${pageContext.request.contextPath}/"/>
<link type="text/css" rel="stylesheet" href="<c:url value="/css/gost/main.css"/>"/>


<portlet:renderURL var="createTypeOrgUrl">
    <portlet:param name="action" value="editTypeView" />
</portlet:renderURL>

<portlet:renderURL var="createOrgUrl">
    <portlet:param name="action" value="editOrganizationView" />
</portlet:renderURL>

<portlet:renderURL var="createGroupUrl">
    <portlet:param name="action" value="editGroupView" />
</portlet:renderURL>


<c:set var="isManager" scope="request" value="${false}"/>

<c:forEach items="${roles}" var="role">
    <c:if test="${role.getTitle() == 'manager'}">
        <c:set var="isManager" scope="request" value="${true}"/>
    </c:if>
</c:forEach>

<script type="text/javascript">
    var managementApp = {
        cr: '${pageContext.request.contextPath}/',
        currentPage: 'FORM',
        application: {
            secret_app_id: '${secretAppIdHeader}'
        }
    };
</script>

<div class="subdivision government-wrap">
    
    <div class="subdivision__header">
        <c:if test="${isManager == true}">
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
                                <a href="${createTypeOrgUrl}">Тип организации</a>
                            </li>
        
                            <li class="toggler__item">
                                <a href="${createOrgUrl}">Организацию</a>
                            </li>
    
                            <li class="toggler__item">
                                <a href="${createGroupUrl}">Группу организаций</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <c:forEach items="${typeOrganizations}" var="item">
        <div class="government-section" data-remove-container>
            <h3 class="government-section__title">
                <span>${item.title}</span>
                <c:if test="${isManager == true}">
                    <portlet:renderURL var="editTypeOrgUrl">
                        <portlet:param name="action" value="editTypeView"/>
                        <portlet:param name="idType" value="${item.id}"/>
                    </portlet:renderURL>

                    <div class="dropdown dropdown--icon">
                        <div class="dropdown__button button button--icon button--settings button--rotate">
                            <svg>
                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                            </svg>
                        </div>
                        <div class="dropdown__menu">
                            <ul class="dropdown__list">
                                <li class="dropdown__item">
                                    <a href="${editTypeOrgUrl}">Редактировать</a>
                                    <a href="${editTypeOrgUrl}" class="dropdown__item-link--danger" data-remove="${item.id}" data-type="TypeOrganization">Удалить</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </c:if>
            </h3>

            <div class="government-section__list">
                <c:if test="${item.organizations.isEmpty() && item.groupOrganizations.isEmpty()}">
                    <div class="government-section__list-item group">
                        <div class="government-section__list-item--empty">Не добавлено ни одной организации или групп организаций</div>
                    </div>
                </c:if>

                <c:forEach items="${item.organizations}" var="org">
                    <portlet:renderURL var="viewOrgURL">
                        <portlet:param name="action" value="organizationView"/>
                        <portlet:param name="idOrganization" value="${org.id}"/>
                    </portlet:renderURL>

                    <portlet:renderURL var="editOrgUrl">
                        <portlet:param name="action" value="editOrganizationView"/>
                        <portlet:param name="idOrganization" value="${org.id}"/>
                    </portlet:renderURL>

                    <div class="government-section__list-item group group--hovered" data-remove-container>
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
                                <a href="${viewOrgURL}">${org.title}</a>
                            </h4>
                            <div class="group__info-desc"></div>
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
                                                <a href="${editOrgUrl}">Редактировать</a>
                                                <a href="javascript:void(0)" class="dropdown__item-link--danger" data-remove="${org.id}" data-type="Organization">Удалить</a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
        
                <c:forEach items="${item.groupOrganizations}" var="group">
                    <portlet:renderURL var="viewGroupURL">
                        <portlet:param name="action" value="groupView"/>
                        <portlet:param name="idGroup" value="${group.id}"/>
                    </portlet:renderURL>

                    <portlet:renderURL var="editGroupUrl">
                        <portlet:param name="action" value="editGroupView" />
                        <portlet:param name="idGroup" value="${group.id}"/>
                    </portlet:renderURL>

                    <div class="government-section__list-item group group--hovered">
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
                                <a href="${viewGroupURL}">${group.title}</a>
                            </h4>
                            <div class="group__info-desc"></div>
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
                                                <a href="${editGroupUrl}">Редактировать</a>
                                                <a href="javascript:void(0)" data-remove="${group.id}" data-type="GroupOrganizations">Удалить</a>
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
    </c:forEach>
</div>

<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/dataservice.js"/>"></script>
<script src="<c:url value="/js/gost/subdivision.js"/>"></script>