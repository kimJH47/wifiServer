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
<h1><%= "와이파이 정보 구하기" %>
</h1>
<br/>
<div><a href="/index.jsp">홈</a> |
    <a href="/history.jsp">위치 히스토리 보기</a> |
    <a href="/load-wifi.jsp">와이파이 정보 불러오기</a> |
    <a href="/bookmark/list.jsp">북마크 보기</a> |
    <a href="/bookmark/group-list.jsp">북마크 그룹관리</a>
</div>
<select id="bookmark-list">
    <option value="none">북마크 그룹 이름 선택</option>
</select>
<button type="button" class="btn btn-primary" id="btn-add">북마크 등록</button>
<table id="vertical-1" class="table table-horizontal table-bordered">
    <tr>
        <th>거리(km)</th>
        <td id ="distance"></td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td id ="mgrNo"></td>
    </tr>
    <tr>
        <th>지역구</th>
        <td id ="WRDOFC"></td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td id ="name"></td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td id ="streetAddress"></td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td id ="detailAddress"></td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td id ="installFloor"></td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td id ="installType"></td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td id ="installMby"></td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td id ="svcEc"></td>
    </tr>
    <tr>
        <th>망종류</th>
        <td id ="cmcwr"></td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td id ="cnstcYear"></td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td id ="inoutDoor"></td>
    </tr>
    <tr>
        <th>WIFI환경</th>
        <td id ="remars3"></td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td id ="longitude"></td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td id ="latitude"></td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td id ="workDttm"></td>
    </tr>
</table>
</body>
<script>
    $(document).on("click", "#btn-add", function(){
        let id = document.getElementById("bookmark-list").value
        let data = {
            bookmarkGroupId : id,
            wifiName : document.getElementById("name").textContent,
        }
        console.log(JSON.stringify(data));
        $.ajax({
            type: 'POST',
            url: '/api/bookmark-list',
            data : JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
        }).done(function (data){
            alert("북마크 등록을 완료하였습니다");
            let list = document.getElementById("bookmark-list");
            list.selectedIndex = 0;

        })
    });
    document.addEventListener("DOMContentLoaded", function () {
        $.ajax({
            type: 'GET',
            url: '/api/bookmark-group',
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            let selectBox = $('#bookmark-list');
            selectBox.empty();
            let response = data.entity;
            selectBox.append('<option value="none">북마크 그룹 이름 선택</option>');
            $.each(response, function (i) {
                let id = response[i].id;
                let name = response[i].name;
                selectBox.append('<option value="' + id + '">' + name + '</option>');
            });
        })

        let mgrNo = localStorage.getItem("mgrNo");
        let latitude = localStorage.getItem("latitude");
        let longitude = localStorage.getItem("longitude");
        let url = '/api/detail-wifi' +
            "?" + "mgrNo=" + mgrNo + "&" +
            "latitude=" + latitude + "&" +
            "longitude=" + longitude;
        $.ajax({
            type: 'GET',
            url: url,
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            let response = data.entity;
            document.getElementById("distance").textContent = response.distance;
            document.getElementById("mgrNo").textContent = response.mgrNo;
            document.getElementById("WRDOFC").textContent = response.WRDOFC;
            document.getElementById("name").textContent = response.name;
            document.getElementById("streetAddress").textContent = response.streetAddress;
            document.getElementById("detailAddress").textContent = response.detailAddress;
            document.getElementById("installFloor").textContent = response.installFloor;
            document.getElementById("installType").textContent  = response.installType;
            document.getElementById("installMby").textContent = response.installMby;
            document.getElementById("svcEc").textContent=  response.svcEc;
            document.getElementById("cmcwr").textContent = response.cmcwr;
            document.getElementById("cnstcYear").textContent = response.cnstcYear;
            document.getElementById("inoutDoor").textContent = response.inoutDoor;
            document.getElementById("remars3").textContent = response.remars3;
            document.getElementById("longitude").textContent = response.longitude;
            document.getElementById("latitude").textContent =response.latitude;
            document.getElementById("workDttm").textContent = response.workDttm;
        })
    })
</script>
<script src="/js/function.js"></script>
</html>