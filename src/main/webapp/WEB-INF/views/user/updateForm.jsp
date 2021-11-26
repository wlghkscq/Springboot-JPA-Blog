<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- jsp 한글 인코딩 -->

<!--header 파일 불어오기 -->
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>
        <input type="hidden" id="id" value="${principal.user.id}"/>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly> <!-- readonly 수정불가  -->
        </div>

        <c:if test="${empty principal.user.oauth}"> <!-- oauth 카카오 유저 정보가 비어있으면 패스워드,이메일  수정창 활성화  --> <!--카카오 로그인 유저가아닌 사람만 회원정보 수정가능  -->
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password"  class="form-control" placeholder="Enter password" id="password">
            </div>
        </c:if>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
            </div>



    </form>

    <button id="btn-update" class="btn btn-primary">회원수정완료</button>

</div>

<script src="/js/user.js"></script>
<!-- footer 파일 불어오기 -->
<%@ include file="../layout/footer.jsp" %>


