<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="currentPageName" value="${fn:replace(pageContext.request.servletPath, '/jsp/', '')}"/>
<c:set var="currentPageName" value="${fn:replace(currentPageName, '.jsp', '')}"/>

<div class="wrapper">
    <div id="header">
      <div id="logo">
        <h1><a href="#">Movie_DB</a></h1>
        <p>Subtitle</p>
      </div>
      <div id="topnav">
        <ul>
            ${currentPageName == 'index' ? "<li class='active'>" : "<li>"}
            <a href="<c:url value="/home" />">Home</a></li>
            ${currentPageName == 'movies' ? "<li class='active'>" : "<li>"}
            <a href="<c:url value="/movies" />">Filme</a></li>
            ${(currentPageName == 'persons' || currentPageName == 'person') ? "<li class='active'>" : "<li>"}
            <a href="<c:url value="/persons" />">Personen</a></li>
        </ul>
      </div>
      <br class="clear" />
    </div>
</div>