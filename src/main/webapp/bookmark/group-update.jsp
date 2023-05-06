<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="/css/jumbotron-narrow.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
<h1><%= "북마크 수정" %>
</h1>
<br/>
<div>
    <a href="/index.jsp">홈</a> |
    <a href="/history.jsp">위치 히스토리 보기</a> |
    <a href="/load-wifi.jsp">와이파이 정보 불러오기</a> |
    <a href="/bookmark/list.jsp">북마크 보기</a> |
    <a href="/bookmark/group-list.jsp">북마크 그룹관리</a>
</div>
<div class="col-md-12">
    <div class="col-md-4">
        <form>
            <div class="form-group">
                <label for="id">id</label>
                <input type="text" class="form-control" id="id" readonly>
            </div>
            <div class="form-group">
                <label for="name">이름</label>
                <input type="text" class="form-control" id="name">
            </div>
            <div class="form-group">
                <label for="orders">순서</label>
                <input type="text" class="form-control" id="orders">
            </div>
        </form>
        <a href="/bookmark/group-list.jsp" role="button" class="btn btn-secondary">취소</a>
        <button type="button" class="btn btn-primary" id="btn-update">수정 완료</button>
    </div>
</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("id").value = localStorage.getItem("id");
        document.getElementById("name").value = localStorage.getItem("name");
        document.getElementById("orders").value = localStorage.getItem("orders");
    })
    $(document).on("click", "#btn-update", function(){
        let data = {
            id : document.getElementById("id").value,
            name : document.getElementById("name").value,
            orders : document.getElementById("orders").value
        }
        $.ajax({
            type: 'POST',
            url: '/api/update-bookmark-group',
            data : JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
        }).done(function (data){
            alert("수정이 완료되었습니다.")
            window.location.href = '/bookmark/group-list.jsp';
        })
    });

</script>
</html>