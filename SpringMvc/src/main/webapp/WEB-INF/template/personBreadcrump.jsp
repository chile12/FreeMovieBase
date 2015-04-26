<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<li><a href="<c:url value="/home" />">Home</a></li>
<li><a href="<c:url value="/persons" />">Persons</a></li>
<tiles:insertAttribute name="lastBreadcrump" ignore="true" />