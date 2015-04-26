<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF8" />
    <link rel="stylesheet" href="<c:url value="/resources/css/layout.css" />" />
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">

    <tiles:insertAttribute name="stylesheets" ignore="true" />

    <script src="<c:url value="/resources/js/d3.v3.js" />" charset="utf-8"></script>
    <script src="<c:url value="/resources/js/jquery-1.10.1.min.js" />" charset="utf-8"></script>
    <script src="<c:url value="/resources/js/jquery-ui.js" />" charset="utf-8"></script>
    <script src="<c:url value="/resources/js/base.js" />" charset="utf-8"></script>
    <tiles:insertAttribute name="scripts" ignore="true" />
    <script>
        var baseUrl = '<c:url value="/" />';
    </script>
</head>
<body>
<div class="wrapper row0">
    <div id="topbar" class="clear">
        <!-- ################################################################################################ -->
        <div class="fl_left">
            <ul class="nospace inline">
                <li><i class="fa fa-phone"></i> +00 (123) 456 7890</li>
                <li><i class="fa fa-envelope-o"></i> info@domain.com</li>
            </ul>
        </div>
        <div class="fl_right">
            <input type="text" id="search" size="30" value="Suche...">
        </div>
        <!-- ################################################################################################ -->
    </div>
</div>

<div class="wrapper row1">
    <header id="header" class="clear">
        <!-- ################################################################################################ -->
        <div id="logo" class="fl_left">
            <h1><a href="../index.html">MovieDB</a></h1>
        </div>
        <nav id="mainav" class="fl_right">
            <ul class="clear">
                <tiles:insertAttribute name="navigation" />
            </ul>
        </nav>
        <!-- ################################################################################################ -->
    </header>
</div>

<div class="wrapper row2">
    <div id="breadcrumb" class="clear">
        <ul>
            <tiles:insertAttribute name="baseBreadcrump" />
        </ul>
    </div>
</div>

<div class="wrapper row3">
    <main class="container clear">
        <!-- main body -->
        <tiles:insertAttribute name="body" />

        <div class="sidebar one_quarter">
            <tiles:insertAttribute name="secondaryNav" ignore="true" />
        </div>
        <!-- / main body -->
        <div class="clear"></div>
    </main>
</div>

<tiles:insertAttribute name="footer" />
</body>
</html>