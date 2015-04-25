<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td>mID</td>
			<td>${movie.mID}</td>
		</tr>
		<tr>
			<td>Erscheinungsjahr</td>
			<td><fmt:formatDate value="${movie.releaseDate}" pattern="dd.MM.yyyy" /> (Deutschland)</td>
		</tr>
		<tr>
			<td>Countries</td>
			<td>
				<ul>
					<c:forEach items="${movie.countries}" var="country">
					<li>${country}</li>
					</c:forEach>
				</ul>
			</td>
		</tr>
	</tbody>
</table>