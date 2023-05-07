## STACK ##
- java 8
- tomcat 8.5
- servlet 4.0
- jdbc

# Contents #

- [Application Architecture](#application-architecture)
- [Flow Chart](#flow-chart)
- [API](#api)

----------------------------------------

# Application Architecture #
![system architecture](./doc/architecture.PNG)
# Flow Chart #

#### 공공 와이파이 불러오기

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiClientServlet
    participant PublicWifiSearchService
    participant SeoulPublicWifiClient
    participant SeoulPublicWifiClient
    participant WifiInfoDao

    client->> WifiClientServlet: HTTP GET /api/add-wifi
    WifiClientServlet->> PublicWifiSearchService: addPublicWifi()
    PublicWifiSearchService->> SeoulPublicWifiClient: getApiTotalCount()
    SeoulPublicWifiClient-->> PublicWifiSearchService: 전체 wifi 데이터 갯수 반환
    loop 전체 와이파이 정보 저장
        PublicWifiSearchService->> SeoulPublicWifiClient: getPublicWifiList()
        SeoulPublicWifiClient-->> PublicWifiSearchService: api 최대 요청갯수만큼 데이터 반환
        PublicWifiSearchService->> WifiInfoDao: save()
        WifiInfoDao-->> PublicWifiSearchService: 저장된 데이터 갯수 반환
    end
PublicWifiSearchService-->> WifiClientServlet: 저장된 데이터 갯수 반환
WifiClientServlet-->>client: HTTP reponse with json
```

#### 공공 와이파이 전체 조회

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiInfoServlet
    participant PublicWifiSearchService
    participant WifiInfoDao

    client->> WifiInfoServlet: HTTP GET /api/find-wifi
    WifiInfoServlet->> PublicWifiSearchService: findOrderByCoordinateWithPagination()
    PublicWifiSearchService->> WifiInfoDao: findOrderByCoordinateWithPagination()
    WifiInfoDao-->>PublicWifiSearchService: wifiDto list with pagination
    PublicWifiSearchService-->> WifiInfoServlet: wifiDto list with pagination
    WifiInfoServlet-->> client : HTTP reponse with json
```

#### 공공 와이파이 단건 조회

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiInfoServlet
    participant PublicWifiSearchService
    participant WifiInfoDao

    client->> WifiInfoServlet: HTTP GET /api/detail-wifi
    WifiInfoServlet->> PublicWifiSearchService: findOne()
    PublicWifiSearchService->> WifiInfoDao: findOne()
    WifiInfoDao-->>PublicWifiSearchService: 단건 조회된 wifiDto
    PublicWifiSearchService-->> WifiInfoServlet: 단건 조회된 wifiDto
    WifiInfoServlet-->> client : HTTP reponse with json
```

#### 히스토리 저장

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant HistoryServlet
    participant HistoryService
    participant HistoryDao

    client->> HistoryServlet: HTTP POST /api/history 
    HistoryServlet->> HistoryService: save()
    HistoryService->> HistoryDao: save()
    HistoryDao-->>HistoryService: 저장된 데이터 갯수 반환
    HistoryService-->>HistoryServlet: 저장된 데이터 갯수 반환
    HistoryServlet-->> client : HTTP reponse with json
```

#### 히스토리 조회

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant HistoryServlet
    participant HistoryService
    participant HistoryDao

    client->> HistoryServlet: HTTP GET /api/history 
    HistoryServlet->> HistoryService: findAll()
    HistoryService->> HistoryDao: findAll()
    HistoryDao-->>HistoryService: 전체 HistoyDto list 반환
    HistoryService-->>HistoryServlet: 전체 HistoyDto list 반환
    HistoryServlet-->> client : HTTP reponse with json
    
```

#### 히스토리 삭제

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant HistoryServlet
    participant HistoryService
    participant HistoryDao

    client->> HistoryServlet: HTTP DELETE /api/history 
    HistoryServlet->> HistoryService: json to id, delete()
    HistoryService->> HistoryDao: delete()
    HistoryDao-->>HistoryService: 삭제된 데이터 갯수 반환
    HistoryService-->>HistoryServlet: 삭제된 데이터 갯수 반환
    HistoryServlet-->> client : HTTP reponse with json
    
```

#### 북마크 그룹 저장

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkGroupServlet
    participant BookmarkGroupService
    participant BookmarkGroupDao

    client->> BookmarkGroupServlet: HTTP POST /api/bookmark-group
    BookmarkGroupServlet->> BookmarkGroupService: save()
    BookmarkGroupService->> BookmarkGroupDao: save()
    BookmarkGroupDao-->>BookmarkGroupService: 저장된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: 저장된 데이터 갯수 반환
    BookmarkGroupServlet-->> client : HTTP reponse with json
    
```

#### 북마크 그룹 조회

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkGroupServlet
    participant BookmarkGroupService
    participant BookmarkGroupDao

    client->> BookmarkGroupServlet: HTTP GET /api/bookmark-group 
    BookmarkGroupServlet->> BookmarkGroupService: findAll()
    BookmarkGroupService->> BookmarkGroupDao: findAll()
    BookmarkGroupDao-->>BookmarkGroupService: 전체 BookmarkGroupDto list 반환
    BookmarkGroupService-->>BookmarkGroupServlet: 전체 BookmarkGroupDto list 반환
    BookmarkGroupServlet-->> client : HTTP reponse with json
    
```

#### 북마크 그룹 수정

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkGroupServlet
    participant BookmarkGroupService
    participant BookmarkGroupDao

    client->> BookmarkGroupServlet: HTTP POST /api/update-bookmark-group
    BookmarkGroupServlet->> BookmarkGroupService: update()
    BookmarkGroupService->> BookmarkGroupDao: update()
    BookmarkGroupDao-->>BookmarkGroupService: 업데이트 된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: 업데이트 된 데이터 갯수 반환
    BookmarkGroupServlet-->> client : HTTP reponse with json
    
```

#### 북마크 그룹 삭제

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkGroupServlet
    participant BookmarkGroupService
    participant BookmarkGroupDao

    client->> BookmarkGroupServlet: HTTP DELETE /api/bookmark-group
    BookmarkGroupServlet->> BookmarkGroupService: delete()
    BookmarkGroupService->> BookmarkGroupDao: delete()
    BookmarkGroupDao-->>BookmarkGroupService: 삭제된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: 삭제된 데이터 갯수 반환
    BookmarkGroupServlet-->> client : HTTP reponse with json
    
```

#### 북마크 저장

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkListServlet
    participant BookmarkListService
    participant BookmarkListDao

    client->> BookmarkListServlet: HTTP POST /api/bookmark-list
    BookmarkListServlet->> BookmarkListService: save()
    BookmarkListService->> BookmarkListDao: save()
    BookmarkListDao-->>BookmarkListService: 저장된 데이터 갯수 반환
    BookmarkListService-->>BookmarkListServlet: 저장된 데이터 갯수 반환
    BookmarkListServlet-->> client : HTTP reponse with json
    
```

#### 북마크 조회

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkListServlet
    participant BookmarkListService
    participant BookmarkListDao

    client->> BookmarkListServlet: HTTP GET /api/bookmark-list
    BookmarkListServlet->> BookmarkListService: findAll()
    BookmarkListService->> BookmarkListDao: findAll()
    BookmarkListDao-->>BookmarkListService: 전체 BookmarkListDto list 반환
    BookmarkListService-->>BookmarkListServlet: 전체 BookmarkListDto list 반환
    BookmarkListServlet-->> client : HTTP reponse with json
    
```

#### 북마크 삭제

```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant BookmarkListServlet
    participant BookmarkListService
    participant BookmarkListDao

    client->> BookmarkListServlet: HTTP DELETE /api/bookmark-list
    BookmarkListServlet->> BookmarkListService: delete()
    BookmarkListService->> BookmarkListDao: delete()
    BookmarkListDao-->>BookmarkListService: 삭제된 데이터 갯수반환
    BookmarkListService-->>BookmarkListServlet: 삭제된 데이터 갯수반환
    BookmarkListServlet-->> client : HTTP reponse with json
    
```

# API #

### 공공 와이파이 데이터 등록 ###

```http
GET /api/add-wifi HTTP/1.1
```

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "entity": 23012 /*  저장된 공공 와이파이 데이터 갯수 */
}


```

### 공공 와이파이 전체 데이터 페이징 조회 ###

#### Query Parameters ####

`pageNumber` : 페이지 번호<br>
`latitude` : 위도<br>
`longitude` : 경도<br>

``` http
GET /api/find-wifi/pageNumber=100&latitude=30.55&longitude=120 HTTP/1.1 HTTP/1.1
```

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
    "entity" : {
        "totalCount": 23012 /* 와이파이 데이터 전체 갯수  */,
        "wifiList": [
            {
                "distance": 15333.3,
                "mgrNo": "서울-10553",
                "WRDOFC": "서초구",
                "name": "말죽거리 골목상권",
                "streetAddress": "서울특별시 서초구 양재1동 남부순환로 356길 43",
                "detailAddress": "",
                "installFloor": "1",
                "installType": "1. 주요거리",
                "installMby": "디지털뉴딜(LG U+)",
                "svcEc": "과기부WiFi(복지시설)",
                "cmcwr": "인터넷망_뉴딜용",
                "cnstcYear": "2022",
                "inoutDoor": "실외",
                "remars3": "",
                "latitude": "0.0",
                "longitude": "0.0",
                "workDttm": "2023-04-27 10:58:30.0"
            },
            /* 페이징된 공공 와이파이 정보 데이터  */
        ]
    }
}
```

### 공공 와이파이 단건 조회 ###

#### Query Parameters ####

`mrgNo` : mrgNo<br>
`latitude` : 위도<br>
`longitude` : 경도<br>

``` http
GET /detail-wifi?mgrNo=SC22180001&longitude=120&latitude=30.55 HTTP/1.1
```

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
     "entity": {
        "distance": 97.88,
        "mgrNo": "SC22180001",
        "WRDOFC": "서초구",
        "name": "내곡동체육시설",
        "streetAddress": "서초구 신흥망길 23-5 (내곡동)",
        "detailAddress": "센터건물 벽면",
        "installFloor": "1",
        "installType": "4. 문화체육관광",
        "installMby": "서초구",
        "svcEc": "공공WiFi",
        "cmcwr": "임대망",
        "cnstcYear": "2022",
        "inoutDoor": "실외",
        "remars3": "",
        "latitude": "37.457928",
        "longitude": "127.08255",
        "workDttm": "2023-05-05 10:58:26.0"
    }
}
```

### 히스토리 저장 ###

``` http
POST /history HTTP/1.1
Content-Type: application/json
 
{
    "latitude": "37.457928",
    "longitude": "127.08255"
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 저장된 데이터 갯수  */
}
```

### 히스토리 전체 조회 ###

``` http
GET /history HTTP/1.1
```

``` http
HTTP/1.1 200 OK

{
    "entity": [
        {
            "id": 3,
            "latitude": 35.8546747,
            "longitude": 128.5012344,
            "createDate": "2023-05-06T09:28:07.947"
        },
        /* 조회된 히스토리 데이터  */
    ]
}
```

### 히스토리 삭제 ###

``` http
DELETE /history HTTP/1.1
Content-Type: application/json

{
    "id" : 1
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 삭제된 데이터 갯수 */
}
```

### 북마크 그룹 저장 ###

``` http
POST /bookmark-group HTTP/1.1
Content-Type: application/json
 
{
    "name": "list1",
    "orders": 1
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 저장된 데이터 갯수*/
}
```

### 북마크 그룹 업데이트 ###

``` http
POST /update-bookmark-group HTTP/1.1
Content-Type: application/json
 
{   
    "id" : 1,
    "name": "list1-1",
    "orders": 3
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 업데이트 된 데이터 갯수*/
}
```

### 북마크 그룹 조회 ###

``` http
GET /bookmark-group HTTP/1.1
```

``` http
HTTP/1.1 200 OK

{
    "entity": [
        {
            "id": 1,
            "name": "list1-1",
            "orders": 3,
            "createDate": "2023-05-06T10:01:06.179",
            "modifiedDate": "2023-05-06T10:01:51.932"
        },
        {
            "id": 2,
            "name": "list2",
            "orders": 1,
            "createDate": "2023-05-06T10:01:12.080",
            "modifiedDate": ""
        },
        /* 조회된 북마크 그룹 데이터 */
    ]
}
```

### 북마크 그룹 삭제 ###

``` http
DELETE /bookmark-group HTTP/1.1
Content-Type: application/json

{
    "id" : 1
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 삭제된 데이터 갯수 */
}
```

### 북마크 저장 ###

``` http
POST /bookmark-list HTTP/1.1
Content-Type: application/json
 
{
    "bookmarkGroupId": 1,
    "wifiName": "말죽거리 골목상권"
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 저장된 데이터 갯수*/
}
```

### 북마크 조회 ###

``` http
GET /bookmark-list HTTP/1.1
```

``` http
HTTP/1.1 200 OK

{
    "entity": [
        {
            "id": 3,
            "bookmarkGroupName": "list1-1",
            "wifiName": "말죽거리 골목상권",
            "createDate": "2023-05-02T09:35:08.808"
        },
        {
            "id": 4,
            "bookmarkGroupName": "name3",
            "wifiName": "내곡동체육시설",
            "createDate": "2023-05-02T09:35:40.067"
        },
        /* 조회된 북마크 데이터 */
    ]
}
```

### 북마크 삭제 ###

``` http
DELETE /bookmark-list HTTP/1.1
Content-Type: application/json

{
    "id" : 1
}
```

``` http
HTTP/1.1 200 OK

{
    "entity": 1 /* 삭제된 데이터 갯수 */
}
```
