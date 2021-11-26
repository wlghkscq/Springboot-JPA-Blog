let index = {
      init: function () {
          // 회원가입
          $("#btn-save").on("click", () => { // funtion(){} 대신 ()=>{}를 사용한 이유 this 를 바인딩하기위해서 !!
              this.save();
          });

          // 회원수정
         $("#btn-update").on("click", () => { // funtion(){} 대신 ()=>{}를 사용한 이유 this 를 바인딩하기위해서 !!
              this.update();
          });
      },

      save:function (){
        // alert('user의 save 메소드 호출 ');
          let data = {
              username: $("#username").val(),
              password: $("#password").val(),
              email: $("#email").val()
          };

          // console.log(data);

          // ajax 호출시 default 가 비동기 호출
          // ajax 통신을 이용해서 3개의 데이터를 json 으로 변경하여 insert 요청
          // ajax 가 통신을 성공하고 나서 json을 리턴해주면 자동으로 자바 오브젝트로 변환
          $.ajax({
            // 회원가입 수행 요청
              type:"POST",
              url:"/auth/joinProc",
              data: JSON.stringify(data), // 자바스크립트 오브젝트 data 를 json 문자열로 파싱 -> http body 데이터
              contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지 application/json 타입임
              dataType: "json" // 서버로 해서 요청이 왔을 때 기본적으로 모든 것이 String 으로 오는데 생긴게 json 이라면
              // -> javascript 오브젝트로 변경해서 resp (response) 에 담아서 보내줌
          }).done(function (resp){ // 요청의 결과가 성공이면 function()실행
              if (resp.status == 500){
                  alert("회원가입에 실패하였습니다.");
              }else {
                  alert("회원가입이 완료되었습니다.");
                  location.href="/";
              }


          }).fail(function (error){ // 요청의 결과가 실패이면 function()실행
              alert(JSON.stringify(error));
         });
      },

    update:function (){

        let data = {
            id: $("#id").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            // 회원수정 요청
            type:"PUT",
            url:"/user",
            data: JSON.stringify(data), // 자바스크립트 오브젝트 data 를 json 문자열로 파싱 -> http body 데이터
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 타입인지 application/json 타입임
            dataType: "json" // 서버로 해서 요청이 왔을 때 기본적으로 모든 것이 String 으로 오는데 생긴게 json 이라면
            // -> javascript 오브젝트로 변경해서 resp (response) 에 담아서 보내줌
        }).done(function (resp){ // 요청의 결과가 성공이면 function()실행

            alert("회원수정이 완료되었습니다.");
            location.href="/";

        }).fail(function (error){ // 요청의 결과가 실패이면 function()실행
            alert(JSON.stringify(error));
        });
    },

}
index.init();