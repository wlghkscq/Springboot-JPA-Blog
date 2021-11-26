<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- jsp 한글 인코딩 -->

<!--header 파일 불어오기 -->
<%@ include file="../layout/header.jsp" %>

<div class="container">

   <form>
      <!-- 제목 -->
      <div class="form-group">
         <input type="text" class="form-control" placeholder="Enter title" id="title">
      </div>

      <!-- 내용 -->
      <div class="form-group">
         <textarea class="form-control summernote" rows="5" id="content"></textarea>
      </div>
   </form>
      <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>
</div>

<script>
   $('.summernote').summernote({
      tabsize: 2,
      height: 300
   });
</script>
<script src="/js/board.js"></script>
<!-- footer 파일 불어오기 -->
<%@ include file="../layout/footer.jsp" %>


