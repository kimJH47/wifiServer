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
<h1><%= "북마크 목록" %>
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
        <th>북마크 이름</th>
        <th>와이파이 명</th>
        <th>등록일자</th>
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
        $(document).on("click", "#delete", function () {
            $.ajax({
                type: 'DELETE',
                url: '/bookmark-list',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify({
                    id:$(this).parent().parent().find("td:eq(0)").text()
                })
            }).done(function (data) {
                location.reload();
            })
        });
        $.ajax({
            type: 'GET',
            url: '/bookmark-list',
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            let response = data.entity;
            let str = "";
            $("tr:has(td)").remove();
            $.each(response, function (i) {
                str += "<TR>"
                str += '<TD id="id">' + response[i].id + '</TD>'
                str += '<TD id="bookmarkGroupName">' + response[i].bookmarkGroupName + '</TD>'
                str += '<TD id="wifiName">' + response[i].wifiName + '</TD>'
                str += '<TD id="createDate">' + response[i].createDate + '</TD>'
                str += '<TD id="note"><button type="button" class="btn btn-primary" id = "delete" >삭제</button></TD>';
                str += '</TR>'
            });
            $("#result").append(str);
        });
    })
</script>
</html>