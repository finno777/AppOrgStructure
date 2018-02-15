<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>

<div class="org-advisers">
    <div class="org-advisers__title">
        <h3>Советники</h3>
    </div>

    <div class="org-advisers__list">
        <div class="org-advisers__row">
            <div class="row__info">
                <div class="row__img">
                    <img src="${cr}/images/avatar2.jpg" alt="">
                </div>

                <div class="row__text">
                    <div class="row__title">Малова Мадина Юсуфджановна</div>
                    <div class="row__desc">Советник Руководителя Федерального агентства – руководитель
                        пресс-службы
                    </div>
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
                                <a href="#">Редактировать</a>
                            </li>

                            <li class="dropdown__item">
                                <button class="danger js-remove-action">Удалить</button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>