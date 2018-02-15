<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<jsp:useBean id="photos" class="java.util.LinkedHashMap"/>

<portlet:renderURL var="goToMain">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<c:forEach var="item" items="${allAttachPerson}">
    <c:set target="${photos}" property="${item.person.id.toString()}" value="${[item.attachment]}"/>
</c:forEach>

<section class="org-page">
    <div class="org-columns">
        <main class="org-columns__main">
            <div class="org-columns__back">
                <a href="${goToMain}" class="_link">К руководству</a>
            </div>

            <div class="org-columns__title">
                <h2>Настройка портлета</h2>
            </div>

            <div class="org-settings" id="org-setting">
                <div class="org-settings__body">
                    <c:if test="${not empty managers or not empty deputes}">
                        <div class="org-settings__section">
                            <div class="org-settings__title">Порядок руководства</div>

                            <div class="org-settings__list">
                                <c:forEach var="manager" items="${managers}">
                                    <div class="list__row list__row--static">
                                        <div class="row__info">
                                            <div class="row__img">
                                                <c:forEach var="photo" items="${photos.get(manager.id.toString())}">
                                                    <img src="${cr}/image-api/view.ido?uuid=${photo.uuid}&imageHeight=230&imageWidth=350" alt="">
                                                </c:forEach>
                                            </div>

                                            <div class="row__text">
                                                <div class="row__title">${manager.name}</div>
                                                <div class="row__desc">${manager.position}</div>
                                            </div>
                                        </div>
                                        <div class="row__action">
                                            <div class="row__edit">
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
                                                                <a href="#">Редактировать</a>
                                                            </li>

                                                            <li class="dropdown__item">
                                                                <button class="danger js-remove-action" type="button" data-id="${manager.id}" data-url="${cr}/rest-api/orgStructure/deletePerson.action">Удалить</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                                <c:forEach var="depute" items="${deputes}">
                                    <div class="list__row" data-position="${depute.sitePosition}" data-id="${depute.id}" data-name="${depute.name}">
                                        <div class="row__info">
                                            <div class="row__img">
                                                <c:forEach var="photo" items="${photos.get(depute.id.toString())}">
                                                    <img src="${cr}/image-api/view.ido?uuid=${photo.uuid}&imageHeight=230&imageWidth=350" alt="">
                                                </c:forEach>
                                            </div>

                                            <div class="row__text">
                                                <div class="row__title">${depute.name}</div>
                                                <div class="row__desc">${depute.position}</div>
                                            </div>
                                        </div>

                                        <div class="row__action">
                                            <ul class="row__arrows">
                                                <li>
                                                    <button class="_arrow js-move-down">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-down"></use>
                                                        </svg>
                                                    </button>
                                                </li>

                                                <li>
                                                    <button class="_arrow js-move-up">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-up"></use>
                                                        </svg>
                                                    </button>
                                                </li>
                                            </ul>

                                            <div class="row__edit">
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
                                                                <a href="#">Редактировать</a>
                                                            </li>

                                                            <li class="dropdown__item">
                                                                <button class="danger js-remove-action" type="button" data-id="${depute.id}" data-url="${cr}/rest-api/orgStructure/deletePerson.action">Удалить</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty organs}">
                        <div class="org-settings__section">
                            <div class="org-settings__title">Порядок органов управления</div>
                            <div class="org-settings__list">
                                <c:forEach var="organItem" items="${organs}">
                                    <div class="list__row" data-organ data-position="${organItem.sitePosition}" data-id="${organItem.id}" data-name="${organItem.name}">
                                        <div class="row__info">
                                            <div class="row__img">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                         xlink:href="${cr}/sprite/sprite.svg#organ${organItem.id}"></use>
                                                </svg>
                                            </div>

                                            <div class="row__text">
                                                <div class="row__title">${organItem.name}</div>
                                            </div>
                                        </div>

                                        <div class="row__action">
                                            <ul class="row__arrows">
                                                <li>
                                                    <button class="_arrow js-move-down">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-down"></use>
                                                        </svg>
                                                    </button>
                                                </li>

                                                <li>
                                                    <button class="_arrow js-move-up">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-up"></use>
                                                        </svg>
                                                    </button>
                                                </li>
                                            </ul>

                                            <div class="row__edit">
                                                <div class="dropdown dropdown--icon dropdown--fixed">
                                                    <button class="dropdown__button button button--icon button--rotate">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-settings"></use>
                                                        </svg>
                                                    </button>
                                                    <div class="dropdown__menu">
                                                        <ul class="dropdown__list">
                                                            <li class="dropdown__item dropdown__item--up dropdown__item--arrow">
                                                                <button class="js-move-up">Вверх</button>
                                                            </li>

                                                            <li class="dropdown__item dropdown__item--down dropdown__item--arrow">
                                                                <button class="js-move-down">Вниз</button>
                                                            </li>

                                                            <li class="dropdown__item">
                                                                <a href="#">Редактировать</a>
                                                            </li>

                                                            <li class="dropdown__item">
                                                                <button class="danger js-remove-action" data-id="${organItem.id}" data-url="${cr}/rest-api/orgStructure/deletePerson.action"></button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty advisers}">
                        <div class="org-settings__section">
                            <div class="org-settings__title">Порядок советников</div>
                            <div class="org-settings__list">
                                <c:forEach var="adviser" items="${advisers}">
                                    <div class="list__row" data-position="${adviser.sitePosition}" data-id="${adviser.id}" data-name="${adviser.name}">
                                        <div class="row__info">
                                            <div class="row__img">
                                                <c:forEach var="photo" items="${photos.get(adviser.id.toString())}">
                                                    <img src="${cr}/image-api/view.ido?uuid=${photo.uuid}&imageHeight=230&imageWidth=350" alt="">
                                                </c:forEach>
                                            </div>

                                            <div class="row__text">
                                                <div class="row__title">${adviser.name}</div>
                                                <div class="row__desc">${adviser.position}</div>
                                            </div>
                                        </div>

                                        <div class="row__action">
                                            <ul class="row__arrows">
                                                <li>
                                                    <button class="_arrow js-move-down">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-down"></use>
                                                        </svg>
                                                    </button>
                                                </li>

                                                <li>
                                                    <button class="_arrow js-move-up">
                                                        <svg>
                                                            <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                                 xlink:href="${cr}/sprite/sprite.svg#icon-move-up"></use>
                                                        </svg>
                                                    </button>
                                                </li>
                                            </ul>

                                            <div class="row__edit">
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
                                                                <a href="#">Редактировать</a>
                                                            </li>

                                                            <li class="dropdown__item">
                                                                <button class="danger js-remove-action" type="button" data-id="${adviser.id}" data-url="${cr}/rest-api/orgStructure/deletePerson.action">Удалить</button>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:if>
                </div>

                <div class="org-settings__footer">
                    <ul class="org-settings__btn">
                        <li>
                            <button type="button" class="button button--primary js-settings-save">Сохранить</button>
                        </li>
                        <li>
                            <a href="${goToMain}" class="button">Отмена</a>
                        </li>
                    </ul>
                </div>
            </div>
        </main>
    </div>
</section>