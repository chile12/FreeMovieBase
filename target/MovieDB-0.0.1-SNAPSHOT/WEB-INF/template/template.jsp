<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF8" />
        <link href="<c:url value="/resources/css/layout.css" />" rel="stylesheet" />
	</head>
	<body>
		<tiles:insertAttribute name="header" />
		<div class="wrapper">
			<div id="container">
				<div id="content">
					<tiles:insertAttribute name="body" />
				</div>
				<div id="column">
					<div class="subnav">
						<tiles:insertAttribute name="secondaryNav" ignore="true" />
					</div>
				</div>
				<br class="clear" />
			</div>
		</div>
		<tiles:insertAttribute name="footer" />
	</body>
</html>