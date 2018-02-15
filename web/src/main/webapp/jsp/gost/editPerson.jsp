<%@page session="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<%@ taglib prefix="spring" uri="/WEB-INF/tld/spring.tld" %>
<portlet:defineObjects/>

<c:set var="cr" scope="request" value="${pageContext.request.contextPath}"/>
<link type="text/css" rel="stylesheet" href="${cr}/css/gost/main.css"/>

<script type="text/javascript">
    orgStruct = {
        cr: '${cr}',
        adminsHeaders: {
            '${headerAppId}': '${headerAppIdValue}',
            Accept: 'application/json',
            'Content-Type': 'application/json',
        },
        currentNodeId: "${nodeId}",
        dialog: null,
    };
</script>