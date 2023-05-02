<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보</title>
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
<h1 id="info">로드 중입니다..</h1>
<a href="/index.jsp">홈</a>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        $.ajax({
            type: 'GET',
            url: '/add-wifi',
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            console.log(JSON.stringify(data))
            document.getElementById("info").innerHTML = data.entity.toString() + "개의 와이파이 정보를 성공적으로 불러왔습니다";
        }).fail(function (error){
            alert(JSON.stringify(error));
        })
    });

</script>
<%--<script src="/js/function.js"></script>--%>
</html>