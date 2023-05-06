<%--
  Created by IntelliJ IDEA.
  User: kim
  Date: 2023/04/30
  Time: 6:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>위치조회 히스토리</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="/css/jumbotron-narrow.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <title>Hello, world!</title>
</head>
<body>
<h1><%= "죄표 히스토리" %>
</h1>
<br/>
<div>
    <a href="/index.jsp">홈</a> |
    <a href="/history.jsp">위치 히스토리 보기</a> |
    <a href="/load-wifi.jsp">와이파이 정보 불러오기</a> |
    <a href="/bookmark/list.jsp">북마크 보기</a> |
    <a href="/bookmark/group-list.jsp">북마크 그룹관리</a>
</div>
<table class="table table-horizontal table-bordered,table table-hover" id="result">
    <thead class="thead-strong">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody id="tbody">
    <tr>
    </tr>
    </tbody>
</table>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        $(document).on("click", "#delete", function(){
            let id = $(this).parent().parent().find("td:eq(0)").text();
            console.log(id);
            $.ajax({
                type: 'DELETE',
                url: '/api/history',
                data : JSON.stringify({
                    id : id
                }),
                contentType: 'application/json; charset=utf-8',
            }).done(function (data){
                location.reload();
            })
        });

        $.ajax({
            type: 'GET',
            url: '/api/history',
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            let entity = data.entity;
            let str = "";
            console.log(data)
            $("tr:has(td)").remove();
            $.each(entity, function (i) {
                str += "<TR>"
                str += '<TD id="id">' + entity[i].id + '</TD>'
                str += '<TD id="latitude">' + entity[i].latitude + '</TD>'
                str += '<TD id="longitude">' + entity[i].longitude + '</TD>'
                str += '<TD id="create-date">' + entity[i].createDate + '</TD>'
                str += '<TD id="note"><button type="button" class="btn btn-primary" id = "delete" >삭제</button></TD>'
                str += '</TR>'
            });
            $("#result").append(str);
        })
    });
</script>
</html>
