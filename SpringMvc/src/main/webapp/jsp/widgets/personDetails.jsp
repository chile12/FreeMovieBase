<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table summary="Allgemeine Infos" cellpadding="0" cellspacing="0">
	<tbody>
    <tr>
        <td>Gender</td>
        <td>${person.gender}</td>
    </tr>
    <tr>
        <td>Born</td>
        <td><fmt:formatDate value="${person.birthday}" pattern="MM.dd.yyyy" /> in ${person.placeOfBirth}</td>
    </tr>
    <c:if test="${not empty person.height}">
        <tr>
            <td>Height</td>
            <td>${person.height} m</td>
        </tr>
    </c:if>
    <c:if test="${not empty person.weight}">
        <tr>
            <td>Weight</td>
            <td>${person.weight} kg</td>
        </tr>
    </c:if>
        <ul>
            <c:if test="${not empty person.deathday}">
                <tr>
                    <td>Died</td>
                    <td><fmt:formatDate value="${person.deathday}" pattern="MM.dd.yyyy" /> in ${person.placeOfDeath}</td>
                </tr>
                <c:if test="${not empty person.causeOfDeath}">
                    <tr>
                        <td>Cause Of Death</td>
                        <td>from ${person.causeOfDeath}</td>
                    </tr>
                </c:if>
            </c:if>
        </ul>
    <ul>
        <c:if test="${not empty person.nationalities}">
            <tr>
                <td>Citizenship</td>
                <td>
                    <ul>
                        <c:forEach items="${person.nationalities}" var="nation">
                            <li>${nation}</li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
        <ul>
            <c:if test="${not empty person.merriages}">
                <tr>
                    <td>Merriages</td>
                    <td>
                        <ul>
                            <c:forEach items="${person.merriages}" var="merri">
                                <li>${merri.spouse2.name}</li>
                                <ul>
                                    <li><fmt:formatDate value="${merri.from}" pattern="MM.dd.yyyy" /></li>
                                    <c:if test="${not empty merri.to}">
                                        <li>to: <fmt:formatDate value="${merri.to}" pattern="MM.dd.yyyy" /></li>
                                    </c:if>
                                </ul>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:if>
        </ul>
    <ul>
        <c:if test="${not empty person.parents}">
            <tr>
                <td>Parents</td>
                <td>
                    <ul>
                        <c:forEach items="${person.parents}" var="parent">
                            <li><a href="/persons/get?uri=${parent.mID}">${parent.name}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.children}">
            <tr>
                <td>Children</td>
                <td>
                    <ul>
                        <c:forEach items="${person.children}" var="child">
                            <li><a href="/persons/get?uri=${child.mID}">${child.name}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.siblings}">
            <tr>
                <td>Siblings</td>
                <td>
                    <ul>
                        <c:forEach items="${person.siblings}" var="sib">
                            <li><a href="/persons/get?uri=${sib.mID}">${sib.name}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.moviesActor}">
            <tr>
                <td>Actor in</td>
                <td>
                    <ul>
                        <c:forEach items="${person.moviesActor}" var="act">
                            <c:set var="ttt" value="${fn:split(act, '+++')}" />
                            <li><a href="/movies/get?uri=${ttt[0]}">${ttt[1]}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.moviesDirector}">
            <tr>
                <td>Directed</td>
                <td>
                    <ul>
                        <c:forEach items="${person.moviesDirector}" var="act">
                            <c:set var="ttt" value="${fn:split(act, '+++')}" />
                            <li><a href="/movies/get?uri=${ttt[0]}">${ttt[1]}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.moviesWriter}">
            <tr>
                <td>Written for</td>
                <td>
                    <ul>
                        <c:forEach items="${person.moviesWriter}" var="act">
                            <c:set var="ttt" value="${fn:split(act, '+++')}" />
                            <li><a href="/movies/get?uri=${ttt[0]}">${ttt[1]}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.moviesProducer}">
            <tr>
                <td>Produced</td>
                <td>
                    <ul>
                        <c:forEach items="${person.moviesProducer}" var="act">
                            <c:set var="ttt" value="${fn:split(act, '+++')}" />
                            <li><a href="/movies/get?uri=${ttt[0]}">${ttt[1]}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
    <ul>
        <c:if test="${not empty person.moviesMusicer}">
            <tr>
                <td>Arranged Music for</td>
                <td>
                    <ul>
                        <c:forEach items="${person.moviesMusicer}" var="act">
                            <c:set var="ttt" value="${fn:split(act, '+++')}" />
                            <li><a href="/movies/get?uri=${ttt[0]}">${ttt[1]}</a></li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </c:if>
    </ul>
	</tbody>
</table>