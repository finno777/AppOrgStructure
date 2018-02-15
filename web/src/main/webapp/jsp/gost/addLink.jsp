<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>

<div id="orgDialog__addSourceLink" class="addSourceLink" title="Создание гиперссылки" style="display: none;">
    <div>
        <div class="gost-form__section">
            <label for="org-source-name" class="gost-form__label">
                Адрес
            </label>
            <div class="gost-form__value">
                <input id="org-source-name" class="textbox source-name" type="text" value=""
                       name="org-source-name" >
            </div>
        </div>

        <div class="gost-form__section">
            <label for="org-source-display-name" class="gost-form__label">
                Отображаемое название
            </label>
            <div class="gost-form__value">
                <input id="org-source-display-name" class="textbox source-display-name" type="text" value=""
                       name="org-source-display-name">
            </div>
        </div>

        <div class="gost-form__section">
            <input id="org-source-open-new-tab" class="checkbutton source-open-new-tab" type="checkbox" value="TRUE"
                   name="org-source-open-new-tab">
            <label for="org-source-open-new-tab">
                Открывать в новой вкладке
            </label>
        </div>


    </div>
</div>