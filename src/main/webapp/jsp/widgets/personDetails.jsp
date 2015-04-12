<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
	<tbody>
		<tr>
			<td>mID</td>
			<td>${person.mID}</td>
		</tr>
		<tr>
			<td>Geboren</td>
			<td><fmt:formatDate value="${person.birthday}" pattern="dd.MM.yyyy" /> in ${person.placeOfBirth}</td>
		</tr>
		<tr>
			<td>Geschlecht</td>
			<td>${person.gender}</td>
		</tr>
		<tr>
			<td>...</td>
			<td>...</td>
		</tr>
	</tbody>
</table>