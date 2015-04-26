<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="homeTemplate">
    <tiles:putAttribute name="body">
        <div class="content">
            <h1>welcome to MovieDB</h1>
            <p>
                Text Text Text Text
            </p>
        </div>
        <div class="wrapper row2">
            <div class="group btmspace-50" style="margin-left:30px;">
                <br />
                <div class="one_half first">
                    <h6>Today's birthday</h6>
                    <div class="one_half first">
                        <a href="<c:url value="/persons/get?uri=${person.mID}" />">
                            <img class="imgl borderedbox inspace-5" style="max-width:200px; max-height:300px" src="<c:url value="${birthdayPerson.imagePath}"/>" alt="" />
                        </a>
                    </div>
                    <div class="one_half">
                        <h6 style="font-size:18px">${birthdayPerson.name}</h6>
                        <p>
                            born on <fmt:formatDate value="${birthdayPerson.birthday}" pattern="dd.MM.yyyy" /> in ${birthdayPerson.placeOfBirth}
                        </p>
                    </div>
                </div>
                <div class="one_half">
                    <h2>Today's best movie</h2>
                    <div class="one_half first">
                        <h6 style="font-size:18px">${currentMovie.title}</h6>
                        <p>
                            released on <fmt:formatDate value="${currentMovie.releaseDate}" pattern="dd.MM.yyyy" />
                        </p>
                    </div>
                    <div class="one_half">
                        <a href="<c:url value="/movies/get?uri=${movie.mID}" />">
                            <img class="imgl borderedbox inspace-5" style="max-width:200px; max-height:300px" src="<c:url value="${currentMovie.imagePath}"/>" alt="" />
                        </a>
                    </div>
                    <p>

                    </p>
                </div>
            </div>
        </div>

        <div class="wrapper row3">
            <div class="clear">
                <h2>The last Oscar winners</h2>
                <ul class="nospace clear">
                    <c:set var="i" value="0"/>
                    <c:forEach items="${persons}" var="person">
                        <li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
                            <a href="<c:url value="/persons/get?uri=${person.mID}" />">${person.name}<br />
                                <img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${person.imagePath}"/>" alt="" />
                            </a>
                            <c:set var="i" value="${i + 1}"/>
                        </li>
                    </c:forEach>
                    <c:set var="i" value="0"/>
                    <c:forEach items="${movies}" var="movie">
                        <li class="one_quarter ${i % 4 == 0 ? " first" : ""}">
                            <a href="<c:url value="/movies/get?uri=${movie.mID}" />">${movie.title}<br />
                                <img class="imgl borderedbox inspace-5" style="height:80px" src="<c:url value="${movie.imagePath}"/>" alt="" />
                            </a>
                            <c:set var="i" value="${i + 1}"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>