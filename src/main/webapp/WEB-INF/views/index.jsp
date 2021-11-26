<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- jsp 한글 인코딩 -->

<!--header 파일 불어오기 -->
<%@ include file="layout/header.jsp" %>

<div class="container">
<c:forEach var="board" items="${boards.content}">
    <div class="card m-2">
        <div class="card-body">
            <h4 class="card-title">${board.title}</h4>
            <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
        </div>
    </div>
</c:forEach>
    <ul class="pagination justify-content-center"> <!-- justify-content-center 가운데정렬 -->
        <c:choose>
            <c:when test="${boards.first}"> <!-- 만약 첫페이지이면  -->
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li><!-- 이전 버튼 비활성화  -->
            </c:when>
            <c:otherwise><!-- 그게 아니면  -->
                <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li> <!-- 이전 버튼 활성화  -->
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${boards.last}"> <!-- 만약 마지막 페이지이면  -->
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li><!-- 다음 버튼 비활성화  -->
            </c:when>
            <c:otherwise><!-- 그게 아니면  -->
                <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li><!-- 다음 버튼 활성화  -->
            </c:otherwise>
        </c:choose>
    </ul>
</div>

<!-- footer 파일 불어오기 -->
<%@ include file="layout/footer.jsp" %>


