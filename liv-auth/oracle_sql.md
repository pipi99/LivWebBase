DDL
```oracle

CREATE SEQUENCE V_ADN_SEQ
INCREMENT BY 1 -- 每次加几个
START WITH 1 -- 从1开始计数
NOMAXvalue -- 不设置最大值
NOCYCLE -- 一直累加，不循环
CACHE 10; --设置缓存cache个序列，如果系统down掉了或者其它情况将会导致序列不连续，也可以设置为---------NOCACHE


/*==============================================================*/
/* Table: USER                                                */
/*==============================================================*/
create table SP_USER 
(
   USER_ID              int                         not null primary key  ,
   USER_NAME            varchar(36)                       null ,
   ORG_ID               int                         null ,
   ALIAS                varchar(100)                      null ,
   MOBILE               int                            null ,
   EMAIL                varchar(100)                      null ,
   GENDER               varchar(8)                        null ,
   DEGREE               varchar(8)                            null ,
   BIRTHDAY             date                           null ,
   PHOTO                varchar(50)                       null ,
   CREATE_DATE          date                           null ,
   PASSWORD             varchar(100)                      null ,
   SALT                 varchar(256)                      null ,
   LOCKED               varchar(8)                        null ,
    TEMP                varchar(10)                        null ,
   LOCKTIME             timestamp                        null 
) ;


drop table  ORGAN;

/*==============================================================*/
/* Table: ORGAN                                               */
/*==============================================================*/
create table ORGAN 
(
   ORGAN_ID             int                         not null primary key  ,
   ORGAN_NAME           char(200)                      null ,
   PARENT_ID            int                         null ,
   ORGAN_LEVEL          varchar(10)                    null ,
   ORGAN_TYPE           varchar(10)                    null ,
   DESCRIPTION          varchar(500)                   null ,
   DEL                  varchar(10)                    null 
) ;


drop table ROLE;

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE 
(
   ROLE_ID              int                         not null primary key  ,
   ROLE_NAME            varchar(200)                   null ,
   ROLE_ALIAS            varchar(200)                   null ,
   CREATE_DATE          date                           null ,
   DESCRIPTION          varchar(200)                   null ,
   DEL                  varchar(10)                    null 
) ;


drop table ROLE_PERMISSION;

/*==============================================================*/
/* Table: ROLE_PERMISSION                                       */
/*==============================================================*/
create table ROLE_PERMISSION 
(
   ID                   int                         not null primary key  ,
   PERMISSION_ID        int                         null ,
   ROLE_ID              int                         null 
);


drop table PERMISSION_RESOURCE;

/*==============================================================*/
/* Table: PERMISSION_RESOURCE                                   */
/*==============================================================*/
create table PERMISSION_RESOURCE 
(
   PERMISSION_ID        int                         not null primary key  ,
   PERMISSION           int                            null ,
   RESOURCE_ID          int                         null 
) ;


drop table APP;

/*==============================================================*/
/* Table: APP                                                   */
/*==============================================================*/
create table APP 
(
   APP_ID               int                         not null primary key  ,
   APP_NAME             char(200)                      null ,
   DESCRIPTION          varchar(200)                   null 
) ;


drop table MENU;

/*==============================================================*/
/* Table: MEUN                                                  */
/*==============================================================*/
create table MENU 
(
   MENU_ID              int                         not null primary key  ,
   MENU_NAME            char(200)                      null ,
   APP_ID               int                     null ,
   PARENT_ID            int                         null ,
   DESCRIPTION          varchar(200)                   null ,
   M_URL                varchar(2000)                  null ,
   ICON                varchar(100)                  null ,
   ACCESS_CTRL          varchar(10)                  null ,
   SORT               int                     null ,
   IS_LEAF               int                     null 
)  ;

drop table RESOURCES;

/*==============================================================*/
/* Table: "RESOURCE"                                            */
/*==============================================================*/
create table RESOURCES
(
   RESOURCE_ID               int                         not null primary key  ,
   APP_ID               int                         null  ,
   RESOURCE_NAME             char(200)                      null ,
   PARENT_ID            int                         null ,
   DESCRIPTION          varchar(200)                   null ,
   PATH                 varchar(2000)                  null ,
   ACCESS_CTRL          varchar(10)                    null ,
   ICON                 varchar(50)                    null ,
   SORT                 int                            null ,
   RESOURCE_TYPE             int                            null ,
   PERMISSION_STR       varchar(200)                   null ,
   ROUTER_NAME          varchar(200)                   null ,
   ROUTER_PATH          varchar(200)                   null ,
   ROUTER_COPM          varchar(200)                   null ,
   ROUTER_CACHE         int                            null 
);

drop table USER_ROLE;

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE 
(
   ID                   int                         not null primary key  ,
   ROLE_ID              int                         null ,
   USER_ID              int                         null 
) ;

drop table USER_PERMISSION;

/*==============================================================*/
/* Table: USER_PERMISSION                                       */
/*==============================================================*/
create table USER_PERMISSION 
(
   ID                   int                         not null primary key  ,
   PERMISSION_ID        int                         null ,
   USER_ID              int                         null 
) ;


drop table GROUPS;

/*==============================================================*/
/* Table: GROUPS                                               */
/*==============================================================*/
create table GROUPS
(
   GROUP_ID             int                         not null primary key  ,
   GROUP_NAME           varchar(200)                   null ,
   DESCRIPTION          varchar(200)                   null ,
   DEL                  varchar(10)                    null 
) ;


drop table USER_GROUP;

/*==============================================================*/
/* Table: USER_GROUP                                            */
/*==============================================================*/
create table USER_GROUP 
(
   ID                   int                         not null primary key  ,
   GROUP_ID             int                         null ,
   USER_ID              int                         null 
) ;

drop table GROUP_ROLE;

/*==============================================================*/
/* Table: GROUP_ROLE                                            */
/*==============================================================*/
create table GROUP_ROLE 
(
   ID                   int                         not null primary key  ,
   ROLE_ID              int                         null ,
   GROUP_ID             int                         null 
) ;


drop table ACTIONS;

/*==============================================================*/
/* Table: ACTIONS                                               */
/*==============================================================*/
create table ACTIONS 
(
   ACTION_ID            int                         not null primary key  ,
   MENU_ID              int                         null ,
   ACTION_NAME          varchar(200)                   null ,
   DESCRIPTION          varchar(200)                   null ,
   PERMISSION           int                            null ,
   ICON                varchar(100)                  null ,
   ACTION_URL           varchar(2000)                  null ,
   ACTION_FUN           varchar(2000)                  null 
) ;


drop table DICT;

/*==============================================================*/
/* Table: DICTIONARIES                                          */
/*==============================================================*/
create table DICT
(
   DICT_ID              int                         not null primary key  ,
   
   D_TYPE               varchar(200)                   null ,
   D_VALUE              varchar(200)                   null ,
   D_CODE               varchar(200)                   null ,
   PARENT_CODE         varchar(200)                   null 
) ;


drop table DICT_TYPE;

/*==============================================================*/
/* Table: DICT_TYPE                                             */
/*==============================================================*/
create table DICT_TYPE 
(
   DICTTYPE_ID          int                         not null primary key  ,
   D_TYPE_NAME          varchar(200)                   null ,
   D_TYPE               varchar(200)                   null 
) ;


```