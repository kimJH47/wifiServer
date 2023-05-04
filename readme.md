# Contents #

- [Application Architecture](#Application Architecture)
- [Flow Chart](#representations)
- [API](#authentication)
----------------------------------------

# Application Architecture
# Flow Chart
####  공공 와이파이 불러오기
```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiClientServlet
    participant PublicWifiSearchService
    participant SeoulPublicWifiClient
    participant SeoulPublicWifiClient
    participant WifiInfoDao

    client->> WifiClientServlet: HTTP GET /add-wifi
    WifiClientServlet->> PublicWifiSearchService: addPublicWifi()
    PublicWifiSearchService->> SeoulPublicWifiClient: getApiTotalCount()
    SeoulPublicWifiClient-->> PublicWifiSearchService: 전체 wifi 데이터 갯수 반환
    loop 전체 와이파이 정보 저장
        PublicWifiSearchService->> SeoulPublicWifiClient: getPublicWifiList()
        SeoulPublicWifiClient-->> PublicWifiSearchService: api 최대 요청갯수만큼 데이터 반환
        PublicWifiSearchService->> WifiInfoDao: save()
        WifiInfoDao-->> PublicWifiSearchService: 저장된 데이터 갯수 반환
    end
PublicWifiSearchService-->> WifiClientServlet: response 객체 반환
WifiClientServlet-->>client: HTTP reponse with json
```
####  공공 와이파이 전체 조회
```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiInfoServlet
    participant PublicWifiSearchService
    participant WifiInfoDao

    client->> WifiInfoServlet: HTTP GET /find-wifi
    WifiInfoServlet->> PublicWifiSearchService: findOrderByCoordinateWithPagination()
    PublicWifiSearchService->> WifiInfoDao: findOrderByCoordinateWithPagination()
    WifiInfoDao-->>PublicWifiSearchService: wifiDto list with pagination
    PublicWifiSearchService-->> WifiInfoServlet: response 객체 반환
    WifiInfoServlet-->> client : HTTP reponse with json
```
####  공공 와이파이 단건 조회
```mermaid
    sequenceDiagram
    autonumber
    participant client
    participant WifiInfoServlet
    participant PublicWifiSearchService
    participant WifiInfoDao

    client->> WifiInfoServlet: HTTP GET /detail-wifi
    WifiInfoServlet->> PublicWifiSearchService: findOne()
    PublicWifiSearchService->> WifiInfoDao: findOne()
    WifiInfoDao-->>PublicWifiSearchService: 단건 조회된 wifiDto
    PublicWifiSearchService-->> WifiInfoServlet: response 객체 반환
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

    client->> HistoryServlet: HTTP POST /history 
    HistoryServlet->> HistoryService: save()
    HistoryService->> HistoryDao: save()
    HistoryDao-->>HistoryService: 저장된 데이터 갯수 반환
    HistoryService-->>HistoryServlet: reponse 반환
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

    client->> HistoryServlet: HTTP GET /history 
    HistoryServlet->> HistoryService: findAll()
    HistoryService->> HistoryDao: findAll()
    HistoryDao-->>HistoryService: 전체 HistoyDto list 반환
    HistoryService-->>HistoryServlet: reponse 반환
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

    client->> HistoryServlet: HTTP DELETE /history 
    HistoryServlet->> HistoryService: json to id, delete()
    HistoryService->> HistoryDao: delete()
    HistoryDao-->>HistoryService: 삭제된 데이터 갯수 반환
    HistoryService-->>HistoryServlet: reponse 반환
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

    client->> BookmarkGroupServlet: HTTP POST /bookmark-group 
    BookmarkGroupServlet->> BookmarkGroupService: save()
    BookmarkGroupService->> BookmarkGroupDao: save()
    BookmarkGroupDao-->>BookmarkGroupService: 저장된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: reponse 객체반환
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

    client->> BookmarkGroupServlet: HTTP GET /bookmark-group 
    BookmarkGroupServlet->> BookmarkGroupService: findAll()
    BookmarkGroupService->> BookmarkGroupDao: findAll()
    BookmarkGroupDao-->>BookmarkGroupService: 전체 BookmarkGroupDto list 반환
    BookmarkGroupService-->>BookmarkGroupServlet: reponse 객체 반환
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

    client->> BookmarkGroupServlet: HTTP POST /update-bookmark-group
    BookmarkGroupServlet->> BookmarkGroupService: update()
    BookmarkGroupService->> BookmarkGroupDao: update()
    BookmarkGroupDao-->>BookmarkGroupService: 업데이트 된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: reponse 객체 반환
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

    client->> BookmarkGroupServlet: HTTP DELETE /bookmark-group
    BookmarkGroupServlet->> BookmarkGroupService: delete()
    BookmarkGroupService->> BookmarkGroupDao: delete()
    BookmarkGroupDao-->>BookmarkGroupService: 삭제된 데이터 갯수 반환
    BookmarkGroupService-->>BookmarkGroupServlet: reponse 객체 반환
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

    client->> BookmarkListServlet: HTTP POST /bookmark-list
    BookmarkListServlet->> BookmarkListService: save()
    BookmarkListService->> BookmarkListDao: save()
    BookmarkListDao-->>BookmarkListService: 저장된 데이터 갯수 반환
    BookmarkListService-->>BookmarkListServlet: reponse 객체 반환
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

    client->> BookmarkListServlet: HTTP GET /bookmark-list
    BookmarkListServlet->> BookmarkListService: findAll()
    BookmarkListService->> BookmarkListDao: findAll()
    BookmarkListDao-->>BookmarkListService: 전체 BookmarkListDto list 반환
    BookmarkListService-->>BookmarkListServlet: reponse 객체 반환
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

    client->> BookmarkListServlet: HTTP DELETE /bookmark-list
    BookmarkListServlet->> BookmarkListService: delete()
    BookmarkListService->> BookmarkListDao: delete()
    BookmarkListDao-->>BookmarkListService: 삭제된 데이터 갯수반환
    BookmarkListService-->>BookmarkListServlet: reponse 객체 반환
    BookmarkListServlet-->> client : HTTP reponse with json
    
```

# API
