create table PUBLIC_WIFI_INFO
(
    id             integer not null constraint id primary key autoincrement,
    MGR_NO         TEXT,
    WRDOFC         TEXT,
    NAME           TEXT,
    STREET_ADDRESS TEXT,
    DETAIL_ADDRESS TEXT,
    INSTALL_FLOOR  integer,
    INSTALL_TYPE   TEXT,
    INSTALL_MBY    TEXT,
    SVC_SE         TEXT,
    CMCWR          TEXT,
    CNSTC_YEAR     TEXT,
    INOUT_DOOR     TEXT,
    REMARS3        integer,
    LAT            TEXT,
    LNT            TEXT,
    WORK_DTTM      TEXT
);

create table HISTORY
(
    ID          integer not null constraint Id primary key autoincrement,
    X           TEXT,
    Y           TEXT,
    CREATE_DATE TEXT
);

create table BOOKMARK_LIST
(
    ID                INTEGER not null constraint ID primary key autoincrement,
    BOOKMARK_NAME     TEXT,
    WIFI_NAME         TEXT,
    CREATE_DATE       TEXT,
    BOOKMARK_GROUP_ID integer
        constraint BOOKMARK_GROUP_ID references BOOKMARK_GROUP on delete cascade
);

create table BOOKMARK_GROUP
(
    ID            integer not null constraint ID primary key autoincrement,
    NAME          TEXT,
    "ORDER"       integer,
    CREATE_DATE   TEXT,
    MODIFIED_DATE TEXT
);

