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
    <script type="text/javascript" src="js/jquery.twbsPagination.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/paginationjs/2.1.4/pagination.css"/>
    <style>
        #name:hover {
            background-color: lightgray;
            cursor: pointer;
        }
    </style>

</head>
<body>
<h1><%= "와이파이 정보 구하기" %>
</h1>
<br/>
<div>
    <a href="/index.jsp">홈</a> |
    <a href="/history.jsp">위치 히스토리 보기</a> |
    <a href="/load-wifi.jsp">와이파이 정보 불러오기</a> |
    <a href="/bookmark/list.jsp">북마크 보기</a> |
    <a href="/bookmark/group-list.jsp">북마크 그룹관리</a>
</div>
<div>
    <a>LAT: </a> <input type="text" id="input-lat" name="input-lat" size="12">
    <a>LNT: </a> <input type="text" id="input-lnt" name="input-lnt" size="12">
    <button type="button" id="btn-load-location">내위치 가져오기</button>
    <button type="button" id="search_wifi_button" onclick="search_wifi(1)">
        근처 WIFI 정보 보기</button>
</div>
<table class="table table-horizontal table-bordered,table table-hover" id="result">
    <thead class="thead-strong">
    <tr>
        <th>거리(km)</th>
        <th>관리번호</th>
        <th>지역구</th>
        <th>와이파이명</th>
        <th>도로명주소</th>
        <th>상세주소</th>
        <th>설치위치(층)</th>
        <th>설치유형</th>
        <th>설치기관</th>
        <th>서비스구분</th>
        <th>망종류</th>
        <th>설치년도</th>
        <th>실내외구분</th>
        <th>WIFI환경</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>작업일자</th>
    </tr>
    </thead>
    <tbody id="tbody">
    <tr>
    </tr>
    </tbody>
</table>
<div class="container">
    <nav aria-label="Page navigation">
        <ul class="pagination" id="pagination"
            style="justify-content: center"></ul>
    </nav>
</div>
</body>
<script>
    document.getElementById("btn-load-location").onclick = function () {
        navigator.geolocation.getCurrentPosition(geoSuccess, geoError);
    }

    function geoSuccess(position) {
        let latitude = position.coords.latitude;
        document.getElementById("input-lat").value = latitude;
        let longitude = position.coords.longitude;
        document.getElementById("input-lnt").value = longitude;
        let data = {
            latitude: latitude,
            longitude: longitude
        }
        $.ajax({
            type: 'POST',
            url: '/api/history',
            data: JSON.stringify(data),
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            alert("위치정보 불러오기 성공");
        })
    }

    function geoError() {
        alert("위치정보 불러오기 실패");
    }

    document.getElementById("search_wifi_button").onclick = function () {
        search_wifi(1);
    }

    function search_wifi(pageNumber) {
        let url = '/api/find-wifi' +
            "?" + "pageNumber=" + pageNumber.toString() + "&" +
            "latitude=" + document.getElementById("input-lat").value + "&" +
            "longitude=" + document.getElementById("input-lnt").value;
        console.log(url)
        $.ajax({
            type: 'GET',
            url: url,
            contentType: 'application/json; charset=utf-8',
        }).done(function (data) {
            let entity = data.entity;
            let wifiList = entity.wifiList;
            let str = "";
            $("tr:has(td)").remove();
            $.each(wifiList, function (i) {
                str += "<TR>"
                str += '<TD id="distance">' + wifiList[i].distance + '</TD>'
                str += '<TD id="mgrNo">' + wifiList[i].mgrNo + '</TD>'
                str += '<TD id="WRDOFC">' + wifiList[i].WRDOFC + '</TD>'
                str += '<TD id="name">' + wifiList[i].name + '</TD>'
                str += '<TD id="streetAddress">' + wifiList[i].streetAddress + '</TD>'
                str += '<TD id="detailAddress">' + wifiList[i].detailAddress + '</TD>'
                str += '<TD id="installFloor">' + wifiList[i].installFloor + '</TD>'
                str += '<TD id="installType">' + wifiList[i].installType + '</TD>'
                str += '<TD id="installMby">' + wifiList[i].installMby + '</TD>'
                str += '<TD id="svcEc">' + wifiList[i].svcEc + '</TD>'
                str += '<TD id="cmcwr">' + wifiList[i].cmcwr + '</TD>'
                str += '<TD id="cnstcYear">' + wifiList[i].cnstcYear + '</TD>'
                str += '<TD id="inoutDoor">' + wifiList[i].inoutDoor + '</TD>'
                str += '<TD id="remars3">' + wifiList[i].remars3 + '</TD>'
                str += '<TD id="longitude">' + wifiList[i].longitude + '</TD>'
                str += '<TD id="latitude">' + wifiList[i].latitude + '</TD>'
                str += '<TD id="workDttm">' + wifiList[i].workDttm + '</TD>'
                str += '</TR>'
            });
            $("#result").append(str);
            let dataCount = entity.totalCount; //총 갯수
            let pageCount = dataCount / 20; //총 페이지 갯수
            $('#pagination').twbsPagination({
                totalPages: pageCount,
                currentPage: pageNumber,
                visiblePages: 10,
                first: '<span sris-hidden="true">«</span>',
                last: '<span sris-hidden="true">»</span>',
                prev: "이전",
                next: "다음",
                initiateStartPageClick: false,	// onPageClick 자동호출 방지
                onPageClick: function (event, page) {
                    search_wifi(page);
                }
            })

        }).fail(function (error) {
            alert(JSON.stringify(error));
        })
        $(document).on("click", "#name", function () {
            localStorage.clear();
            localStorage.setItem("mgrNo", $(this).parent().find("td:eq(1)").text());
            localStorage.setItem("longitude", document.getElementById("input-lnt").value)
            localStorage.setItem("latitude", document.getElementById("input-lat").value)
            location.href = "/detail-wifi.jsp";
        })
    }
</script>
<script src="/js/function.js"></script>
</html>