<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="nospace clear">

<c:set var="i" value="0"/>
<c:forEach items="${item.imagePaths}" var="imagePath">
	<li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
		<img class="imgl borderedbox inspace-5" src="<c:url value="${imagePath}"/>" alt="" />
	</li>
	<c:set var="i" value="${i + 1}"/>
</c:forEach>
</ul>