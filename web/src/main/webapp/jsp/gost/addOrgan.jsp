<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<portlet:renderURL var="urlRedirectMain">
    <portlet:param name="action" value="redirectMain"/>
</portlet:renderURL>

<portlet:renderURL var="urlOrgan">
    <portlet:param name="action" value="renderOrgan"/>
    <portlet:param name="idOrgan" value="${organ.id}"/>
</portlet:renderURL>

<script type="text/javascript">
    orgStruct = {
        cr: '${cr}',
        redirectUrl: '${urlRedirectMain}',
        appId: '${appId}',
        portalName: '${portalName}',
        appName: '${appName}',
        organ: true,
        <c:if test="${organ != null}">
        organId: ${organ.id},
        </c:if>
        adminsHeaders: {
            '${headerAppId}': '${secretAppIdHeader}',
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        currentNodeId: "${nodeId}",
        dialog: null,
    };
</script>

<section class="org-page">
    <div class="org-columns">
        <main class="org-columns__main">
            <div class="org-columns__back">
                <c:choose>
                    <c:when test="${not empty organ}">
                        <a href="${urlOrgan}" class="_link">К органу</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${urlRedirectMain}" class="_link">К руководству</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="org-columns__title">
                <h2>Добавление органа управления</h2>
            </div>

            <div class="gost-form gost-form--edit">
                <form class="gost-form__body" id="org-form">
                    <div class="gost-form__part">
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__desc">Поля, отмеченные символом (*), обязательны для заполнения</div>
                            </div>

                            <div class="gost-form__section">
                                <label for="organ-name" class="gost-form__label">Наименования органа управления*</label>
                                <div class="gost-form__value" data-prop-name="name" data-required data-max-length="50">
                                    <input id="organ-name" type="text" class="textbox form-field" value="${organ.name}" data-key="Наименования органа управления">
                                </div>
                            </div>

                            <div class="gost-form__section">
                                <label for="organ-description" class="gost-form__label">Описание</label>
                                <div class="gost-form__value" data-max-length="8000" data-prop-name="desc">
                                    <textarea id="organ-description" class="textarea js-tiny-editor" data-key="Описание">${organ.description}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__label">Документы*:</div>

                                <div class="gost-form__add" data-prop-name="doc" data-attach data-key="Документы" data-error-type="small">
                                    <button class="_btn js-add-file form-attach" data-preview="true" data-type="doc" data-allowedformats="doc, presentation, archive, text" type="button">Добавить документ</button>
                                </div>

                                <div class="gost-form__preview">
                                    <c:forEach var="item" items="${attachOrgan}">
                                        <div class="preview-item preview-item--doc" data-extension="${item.extName}" data-filename="${item.fileName}" data-uniqid="${item.uniqID}" data-size="${item.size}" data-id="${item.id}" data-uuid="${item.uuid}" data-type="${item.type}" data-path="${cr}/image-doc/view.ido?uuid=${item.uuid}">
                                            <div class="preview-item__doc-pic">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#${item.extName.toLowerCase()}"></use>
                                                </svg>
                                            </div>
                                            <span class="preview-item__doc">${item.fileName}</span>
                                            <div class="preview-item__remove-btn preview-item__remove-btn--transparent">
                                                <svg>
                                                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="${cr}/sprite/sprite.svg#icon-close"></use>
                                                </svg>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>

                                <div class="gost-form__desc">Разрешенные форматы: doc, docx, pdf, txt, xls, xlsx, rtf, wps, ppt, pptx, odt, odf, vsd и odp</div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">Принятые решения по рассмотрению проектов и иных документов</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-decision" class="textarea js-tiny-editor" data-key="Принятые решения">${organ.decision}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">График заседаний</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-schedule" class="textarea js-tiny-editor" data-key="График заседаний">${organ.schedule}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">Условия присутствия на заседании</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-conditions" class="textarea js-tiny-editor" data-key="Условия присутствия на заседании">${organ.conditions}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">Заседания</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-meeting" class="textarea js-tiny-editor" data-key="Заседания">${organ.meeting}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">План работы</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-plan" class="textarea js-tiny-editor" data-key="План работы">${organ.plan}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">Контактные данные</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-details" class="textarea js-tiny-editor" data-key="Контактные данные">${organ.details}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="gost-form__part">
                        <div class="part__title">Положение об общественном совете при органе власти</div>
                        <div class="part__body">
                            <div class="gost-form__section">
                                <div class="gost-form__value">
                                    <textarea id="organ-situation" class="textarea js-tiny-editor" data-key="Положение об общественном совете при органе власти">${organ.situation}</textarea>
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
                                <c:when test="${not empty organ}">
                                    <button class="button button--primary" type="submit">Сохранить</button>
                                    <a href="${urlOrgan}" class="button">Отмена</a>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="button button--primary">Добавить орган управления</button>
                                    <a href="${urlRedirectMain}" class="button">Отмена</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </footer>
                </form>
            </div>
        </main>
    </div>
</section>

<script src="<c:url value="/js/gost/form.js"/>"></script>