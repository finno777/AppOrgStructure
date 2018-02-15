<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<portlet:defineObjects/>

<jsp:useBean id="attributeMap" class="java.util.LinkedHashMap"/>
<c:forEach var="item" items="${attribute}">
    <c:set target="${attributeMap}" property="${item.key}" value="${item.value}"/>
</c:forEach>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<portlet:renderURL var="urlPerson">
    <portlet:param name="action" value="renderPerson"/>
    <portlet:param name="id" value="${person.id}"/>
</portlet:renderURL>

<div class="org-columns__back">
    <c:choose>
        <c:when test="${not empty person}">
            <a href="${urlPerson}" class="_link">К пользователю</a>
        </c:when>
        <c:otherwise>
            <a href="${urlRedirectMain}" class="_link">К руководству</a>
        </c:otherwise>
    </c:choose>
</div>

<div class="org-columns__title">
    <h2>Добавление советника</h2>
</div>

<div class="gost-form gost-form--edit">
    <form class="gost-form__body" id="org-form">
        <div class="gost-form__part">
            <div class="part__body">
                <div class="gost-form__section">
                    <div class="gost-form__desc">Поля, отмеченные символом (*), обязательны для заполнения</div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-name" class="gost-form__label">ФИО советника*</label>
                    <div class="gost-form__value" data-prop-name="name" data-required data-max-length="50">
                        <input id="adviser-name" type="text" class="textbox form-field" value="${person.name}" data-key="ФИО советника">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-position" class="gost-form__label">Должность*</label>
                    <div class="gost-form__value" data-prop-name="position" data-required data-max-length="100">
                        <textarea id="adviser-position" class="textarea form-field" data-key="Должность">${person.position}</textarea>
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-phone" class="gost-form__label">Телефон*</label>

                    <div class="gost-form__add js-add-phone">
                        <button class="_btn" type="button">Добавить телефон</button>
                    </div>

                    <c:choose>
                        <c:when test="${not empty person.phone}">
                            <c:forEach var="phone" items="${fn:split(person.phone,',')}" varStatus="phoneCount">
                                <c:choose>
                                    <c:when test="${phoneCount.count == 1}">
                                        <div class="gost-form__value" data-prop-name="phone" data-required data-max-length="30">
                                            <input id="adviser-phone" type="text" class="textbox form-field"
                                                   value="${phone}" data-key="Телефон">
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="gost-form__value edit-field" data-prop-name="phone" data-max-length="30">
                                            <input id="adviser-phone" type="text" class="textbox form-field"
                                                   value="${phone}" data-key="Телефон">
                                            <div class="edit-box">
                                                <button class="edit-box__btn" type="button">
                                                    <svg>
                                                        <use xmlns:xlink="http://www.w3.org/1999/xlink"
                                                             xlink:href="${cr}/sprite/sprite.svg#icon-settings"></use>
                                                    </svg>
                                                </button>
                                                <ul class="edit-box__list">
                                                    <li class="_item">
                                                        <button class="_btn danger" type="button">Удалить поле</button>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="gost-form__value" data-prop-name="phone" data-required data-prop-name="phone" data-max-length="30">
                                <input id="adviser-phone" type="text" class="textbox form-field" value="" data-key="Телефон">
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-fax" class="gost-form__label">Факс</label>
                    <div class="gost-form__value">
                        <input id="adviser-fax" type="text" class="textbox form-attr" value="${attributeMap.get('Факс')}" data-key="Факс">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-email" class="gost-form__label">Электронная почта</label>
                    <div class="gost-form__value" data-prop-name="email" data-max-length="50">
                        <input id="adviser-email" type="text" class="textbox form-field" value="${person.email}">
                    </div>
                </div>
            </div>
        </div>

        <div class="gost-form__part">
            <div class="part__body">
                <div class="gost-form__section">
                    <div class="gost-form__label">Фотография*:</div>

                    <div class="gost-form__add" data-prop-name="photo" data-attach data-key="photo" data-required>
                        <button class="_btn js-add-file form-attach" data-preview="true" data-type="img" data-allowedformats="img" data-maxfilecount="1" type="button">Добавить фотографию</button>
                    </div>

                    <div class="gost-form__preview"></div>

                    <div class="gost-form__desc">Разрешенные форматы: gif, jpeg, jpg, bmp и png</div>
                </div>
            </div>
        </div>

        <div class="gost-form__part">
            <div class="part__title">Биография</div>
            <div class="part__body">
                <div class="gost-form__section">
                    <label for="adviser-date" class="gost-form__label">Дата рождения</label>
                    <div class="gost-form__value">
                        <input id="adviser-date" type="text" class="datepckr__input form-attr" value="" data-date="${attributeMap.get('Дата рождения')}" data-key="Дата рождения">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-born" class="gost-form__label">Место рождения</label>
                    <div class="gost-form__value" data-prop-name="born" data-max-length="50">
                        <input id="adviser-born" type="text" class="textbox form-attr" value="${attributeMap.get('Место рождения')}" data-key="Место рождения" maxlength="50">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-edu" class="gost-form__label">Образование</label>
                    <div class="gost-form__value" data-prop-name="edu" data-max-length="300">
                        <textarea id="adviser-edu" class="textarea js-tiny-editor" data-key="Образование">${attributeMap.get('Образование')}</textarea>
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-work" class="gost-form__label">Опыт работы</label>
                    <div class="gost-form__value" data-prop-name="work" data-max-length="4000">
                        <textarea id="adviser-work" class="textarea js-tiny-editor" data-key="Опыт работы">${attributeMap.get('Опыт работы')}</textarea>
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="adviser-details" class="gost-form__label">Дополнительная информация</label>
                    <div class="gost-form__value" data-prop-name="info" data-max-length="200">
                        <textarea id="adviser-details" class="textarea js-tiny-editor" data-key="Дополнительная информация">${attributeMap.get('Дополнительная информация')}</textarea>
                    </div>
                </div>
            </div>
        </div>

        <div class="gost-form__part">
            <div class="part__title">Обязанности</div>
            <div class="part__body">
                <div class="gost-form__section">
                    <div class="gost-form__value" data-prop-name="duties" data-max-length="4000">
                        <textarea id="adviser-duties" class="textarea js-tiny-editor" data-key="Обязанности">${attributeMap.get('Обязанности')}</textarea>
                    </div>
                </div>
            </div>
        </div>

        <footer class="gost-form__footer">
            <div class="footer__errors">
                <div class="_label">Не заполнены поля: </div>
                <div class="_list js-formFooter-errors"></div>
            </div>

            <div class="footer__buttons">
                <c:choose>
                    <c:when test="${not empty person}">
                        <button class="button button--primary" type="submit">Сохранить</button>
                        <a href="${urlPerson}" class="button">Отмена</a>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="button button--primary">Добавить советника</button>
                        <a href="${urlRedirectMain}" class="button">Отмена</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </footer>
    </form>
</div>