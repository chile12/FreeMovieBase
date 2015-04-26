<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
	<tbody>

		<tr>
			<td>Runtime</td>
			<td>${movie.runtime} minutes</td>
		</tr>
            <c:if test="${not empty movie.budget}">
                <tr>
                    <td>Budget</td>
                    <td>${movie.budget}</td>
                </tr>
            </c:if>
        <c:if test="${not empty movie.revenue}">
            <tr>
                <td>Revenue</td>
                <td>${movie.revenue}</td>
            </tr>
        </c:if>
            <c:if test="${not empty movie.genres}">
                <tr>
                    <td>Genres</td>
                    <td>
                        <ul>
                            <c:forEach items="${movie.genres}" var="genre">
                                <li>${genre}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:if>
		<tr>
			<td>Released</td>
            <td><fmt:formatDate value="${movie.releaseDateGermany}" pattern="dd.MM.yyyy" /></td>
		</tr>
        <tr>
            <td>Actors</td>
            <td>
                <ul>
                    <c:forEach items="${movie.actors}" var="actor">
                        <li><a href="/persons/get?uri=${actor.mID}">${actor.name}</a></li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
            <c:if test="${not empty movie.countries}">
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
            </c:if>
            <c:if test="${not empty movie.companies}">
                <tr>
                    <td>Production Companies</td>
                    <td>
                        <ul>
                            <c:forEach items="${movie.companies}" var="company">
                                <li>${company}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:if>
	</tbody>
</table>