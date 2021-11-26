<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- jsp 한글 인코딩 -->

<!--header 파일 불어오기 -->
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <button id="btn-login" class="btn btn-primary">로그인</button>

        <!-- 카카오 로그인 버튼 -->
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=b4d98f4bb5b2ef0ce13957cb06c175d3
&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code">
            <img height="40px" src="/image/kakao_login_button.png"/></a>
    </form>

</div>

<!-- footer 파일 불어오기 -->
<%@ include file="../layout/footer.jsp" %>


