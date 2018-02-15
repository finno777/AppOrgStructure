<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<portlet:defineObjects/>

<portlet:renderURL var="redirectMainURL">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<portlet:renderURL var="editOrgUrl">
    <portlet:param name="action" value="editOrganizationView"/>
    <portlet:param name="idOrganization" value="${organization.id}"/>
</portlet:renderURL>

<portlet:renderURL var="addPersonUrl">
    <portlet:param name="action" value="editOrganizationPersonView"/>
    <portlet:param name="idOrganization" value="${organization.id}"/>
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
    organization: `${organization}`
  };
</script>

<div class="government-view government-wrap">
    
    <a href="${redirectMainURL}" class="government__link">← <span>К организациям</span></a>

    <c:if test="${isManager == true}">
        <div class="governments__header">
            <div class="governments__header-buttons">
                <a href="${addPersonUrl}" class="button button--primary">
                    <svg><use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#plus"></use></svg>
                    Добавить участника
                </a>
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
                            <a href="${editOrgUrl}">Редактировать</a>
                            <a href="javascript:void(0)" class="dropdown__item-link--danger">Удалить</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </c:if>
    
    <h1 class="government-view__header">${organization.title}</h1>

    <div class="government-section" data-remove-container>
            <h3 class="government-section__title">
                <span>${organization.titleShort}</span>
            </h3>

                <div class="government-section__list">
    
                    <c:forEach items="${organization.people}" var="per">
                        <portlet:renderURL var="editPersonUrl">
                            <portlet:param name="action" value="editOrganizationPersonView"/>
                            <portlet:param name="idOrganization" value="${organization.id}"/>
                            <portlet:param name="idOrganizationPerson" value="${per.id}"/>
                        </portlet:renderURL>
                        <div class="government-section__list-item group">
                            
                            <div class="group__icon">
                                <c:choose>
                                    <c:when test="${!per.person.attachments.isEmpty()}">
                                        <img src="${cr}image-api/view.ido?uuid=${per.person.attachments[per.person.attachments.size() - 1].uuid}">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="group__icon--empty"></div>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="group__info">
                                <h4 class="group__info-title">
                                    <a href="#">${per.person.name}</a>
                                </h4>
                                <div class="group__info-desc">${per.position}</div>
                            </div>
                            <div class="group__right group__right--centered">
                                <span class="group__right-addon">
                                    <svg>
                                        <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#phone"></use>
                                    </svg>
                                    <a href="tel:${per.person.phone}" class="link-text">${per.person.phone}</a>
                                </span>
                                <c:if test="${isManager == true}">
                                    <div class="dropdown dropdown--icon">
                                        <div class="dropdown__button button button--icon button--settings button--rotate">
                                            <svg>
                                                <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}sprite/sprite.svg#settings"></use>
                                            </svg>
                                        </div>
                                        <div class="dropdown__menu">
                                            <ul class="dropdown__list">
                                                <li class="dropdown__item">
                                                    <a href="${editPersonUrl}">Редактировать</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </c:if>
    
                            </div>
                        </div>
                    </c:forEach>
    
                </div>
        </div>

    <ul class="orgspec">
      <c:if test="${organization.address != null}">
        <li class="orgspec__item">
          <span class="orgspec__left">Адрес: </span>
          <div class="orgspec__right">${organization.address}</div>
        </li>
      </c:if>

      <c:if test="${organization.addressActual != null}">
        <li class="orgspec__item">
          <span class="orgspec__left">Факт. адрес: </span>
          <div class="orgspec__right">${organization.addressActual}</div>
        </li>
      </c:if>

      <c:if test="${organization.phones != null && !organization.phones.isEmpty()}">
        <li class="orgspec__item">
          <span class="orgspec__left">Телефон: </span>
          <div class="orgspec__right">
            <c:forEach items="${organization.phones}" var="phone">
                <a href="tel:${phone.phone}" class="link-text">${phone.phone}</a>;
            </c:forEach>
          </div>
        </li>
      </c:if>
      
      <c:if test="${organization.fax != null}">
        <li class="orgspec__item">
          <span class="orgspec__left">Факс: </span>
          <div class="orgspec__right">${organization.fax}</div>
        </li>
      </c:if>
      
      <c:if test="${organization.mail != null}">
        <li class="orgspec__item">
          <span class="orgspec__left">E-mail: </span>
          <div class="orgspec__right">
            <a href="mailto:${organization.mail}" class="link-text">${organization.mail}</a>
          </div>
        </li>
      </c:if>
      
      <c:if test="${organization.site != null}">
        <li class="orgspec__item">
          <span class="orgspec__left">Сайт: </span>
          <div class="orgspec__right">${organization.site}</div>
        </li>
      </c:if>
      
      <c:if test="${organization.description != null}">
        <li class="orgspec__item orgspec__item-column">
          <span class="orgspec__left">Описание: </span>
          ${organization.description}
        </li>
      </c:if>
      
      <c:if test="${organization.description != null}">
        <li class="orgspec__item orgspec__item-column">
          <span class="orgspec__left">Положение: </span>
          ${organization.provision}
        </li>
      </c:if>
    </ul>

</div>


<script src="<c:url value="/js/gost/controls.js"/>"></script>
<script src="<c:url value="/js/gost/organization.js"/>"></script>