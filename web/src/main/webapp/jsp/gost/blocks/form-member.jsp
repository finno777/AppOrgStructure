<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<portlet:defineObjects/>

<div class="org-columns__back">
    <portlet:renderURL var="renderOrgan">
        <portlet:param name="action" value="renderOrgan"/>
        <portlet:param name="idOrgan" value="${organId}"/>
    </portlet:renderURL>

    <a href="${renderOrgan}" class="_link">К органу управления</a>
</div>
<script type="text/javascript">
    if(orgStruct) {
        orgStruct.member = true;
    }
</script>

<div class="org-columns__title">
    <h2>Добавление участника</h2>
</div>

<div class="gost-form gost-form--edit">
    <form class="gost-form__body" id="org-form">
        <div class="gost-form__part">
            <div class="part__body">
                <div class="gost-form__section">
                    <div class="gost-form__desc">Поля, отмеченные символом (*), обязательны для заполнения</div>
                </div>

                <div class="gost-form__section">
                    <label for="member-name" class="gost-form__label">ФИО*</label>
                    <div class="gost-form__value" data-prop-name="name" data-required data-max-length="50">
                        <input id="member-name" type="text" class="textbox form-field" value="${person.name}" data-key="ФИО">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="member-position" class="gost-form__label">Должность</label>
                    <div class="gost-form__value" data-prop-name="position" data-max-length="1000">
                        <textarea id="member-position" class="textarea form-field" maxlength="1000">${person.position}</textarea>
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="member-phone" class="gost-form__label">Телефон</label>

                    <div class="gost-form__value" data-prop-name="phone" data-max-length="30">
                        <input id="member-phone" type="text" class="textbox form-field" value="${person.phone}">
                    </div>
                </div>

                <div class="gost-form__section">
                    <label for="member-email" class="gost-form__label">Электронная почта</label>
                    <div class="gost-form__value" data-prop-name="email" data-max-length="50">
                        <input id="member-email" type="text" class="textbox form-field" value="${person.email}" maxlength="50">
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
                        <a href="#" class="button">Отмена</a>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="button button--primary">Добавить участника</button>
                        <a href="#" class="button">Отмена</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </footer>
    </form>
</div>