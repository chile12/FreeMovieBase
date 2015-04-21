<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td>Born</td>
			<td><fmt:formatDate value="${person.birthday}" pattern="dd.MM.yyyy" /> in ${person.placeOfBirth}</td>
		</tr>
        <c:if test="${not empty person.deathday}">
            <tr>
                <td>Died</td>
                <td><fmt:formatDate value="${person.deathday}" pattern="dd.MM.yyyy" /> in ${person.placeOfDeath}</td>
            </tr>
        </c:if>
	</tbody>
</table>