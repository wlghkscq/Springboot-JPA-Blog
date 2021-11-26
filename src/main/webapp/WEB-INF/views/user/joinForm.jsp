<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- jsp 한글 인코딩 -->

<!--header 파일 불어오기 -->
<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form>

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" placeholder="Enter username" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password">
        </div>

        <div class="form-group">
            <label for="password">RePassword:</label>
            <input type="password" class="form-control" placeholder="Enter RePassword" id="rePassword">
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" class="form-control" placeholder="Enter email" id="email">
        </div>


    </form>

    <button id="btn-save" class="btn btn-primary">회원가입</button>

</div>

<script src="/js/user.js"></script>
<!-- footer 파일 불어오기 -->
<%@ include file="../layout/footer.jsp" %>


