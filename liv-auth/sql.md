DDL
```mysql

drop DATABASE if exists `auth`;
CREATE DATABASE `auth` /*!40100 DEFAULT CHARACTER SET utf8 */;
use auth;

drop table if exists auth.USER;

/*==============================================================*/
/* Table: USER                                                */
/*==============================================================*/
create table auth.USER 
(
   USER_ID              bigint                         not null primary key auto_increment comment '主键',
   USER_NAME            varchar(36)                       null comment '用户名称',
   ORG_ID               bigint                         null comment '组织机构',
   ALIAS                varchar(100)                      null comment '姓名',
   MOBILE               int                            null comment '手机',
   EMAIL                varchar(100)                      null comment '邮箱',
   GENDER               varchar(8)                        null comment '性别',
   DEGREE               varchar(8)                            null comment '学历',
   BIRTHDAY             date                           null comment '出生年月日',
   PHOTO                varchar(50)                       null comment '照片',
   CREATE_DATE          date                           null comment '创建日期',
   PASSWORD             varchar(100)                      null comment '密码',
   SALT                 varchar(256)                      null comment '盐',
   LOCKED               varchar(8)                        null comment '是否锁定  1是  0否',
    TEMP                varchar(10)                        null comment '是否临时用户  1是  0否,临时用户随时可删除',
   LOCKTIME             timestamp                        null comment '锁定时间'
) comment '用户信息表';


drop table if exists auth.ORGAN;

/*==============================================================*/
/* Table: ORGAN                                               */
/*==============================================================*/
create table auth.ORGAN 
(
   ORGAN_ID             bigint                         not null primary key auto_increment comment '主键',
   ORGAN_NAME           char(200)                      null comment '机构名称',
   PARENT_ID            bigint                         null comment '上级机构ID',
   ORGAN_LEVEL          varchar(10)                    null comment '机构级别',
   ORGAN_TYPE           varchar(10)                    null comment '机构类型',
   DESCRIPTION          varchar(500)                   null comment '机构描述',
   DEL                  varchar(10)                    null comment '是否删除1是 0否'
) comment '组织机构表';


drop table if exists auth.ROLE;

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table auth.ROLE 
(
   ROLE_ID              bigint                         not null primary key auto_increment comment '主键',
   ROLE_NAME            varchar(200)                   null comment '角色名称',
   ROLE_ALIAS            varchar(200)                   null comment '角色名称',
   CREATE_DATE          date                           null comment '创建日期',
   DESCRIPTION          varchar(200)                   null comment '角色描述',
   DEL                  varchar(10)                    null comment '是否删除 1是0否'
) comment '角色表';


drop table if exists auth.ROLE_PERMISSION;

/*==============================================================*/
/* Table: ROLE_PERMISSION                                       */
/*==============================================================*/
create table auth.ROLE_PERMISSION 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   PERMISSION_ID        bigint                         null comment '权限ID',
   ROLE_ID              bigint                         null comment '角色ID'
)comment '角色权限关系表';


drop table if exists auth.PERMISSION_RESOURCE;

/*==============================================================*/
/* Table: PERMISSION_RESOURCE                                   */
/*==============================================================*/
create table auth.PERMISSION_RESOURCE 
(
   PERMISSION_ID        bigint                         not null primary key auto_increment comment '主键',
   PERMISSION           int                            null comment '权限类型',
   RESOURCE_ID          bigint                         null comment '资源ID'
) comment '权限资源表';


drop table if exists APP;

/*==============================================================*/
/* Table: APP                                                   */
/*==============================================================*/
create table APP 
(
   APP_ID               bigint                         not null primary key auto_increment comment '主键',
   APP_NAME             char(200)                      null comment '名称',
   DESCRIPTION          varchar(200)                   null comment '说明'
) comment '应用表';


drop table if exists auth.MENU;

/*==============================================================*/
/* Table: MEUN                                                  */
/*==============================================================*/
create table auth.MENU 
(
   MENU_ID              bigint                         not null primary key auto_increment comment '主键',
   MENU_NAME            char(200)                      null comment '菜单名称',
   APP_ID               bigint                     null comment '所属应用',
   PARENT_ID            bigint                         null comment '上级ID',
   DESCRIPTION          varchar(200)                   null comment '说明',
   M_URL                varchar(2000)                  null comment '菜单链接',
   ICON                varchar(100)                  null comment '菜单图标',
   ACCESS_CTRL          varchar(10)                  null comment ' 访问控制：login： 登录后访问。 perm：授权后访问。open：无需登录或授权访问',
   SORT               bigint                     null comment '排序',
   IS_LEAF               bigint                     null comment '是否子菜单'
)  comment '菜单表';

drop table if exists auth.RESOURCES;

/*==============================================================*/
/* Table: "RESOURCE"                                            */
/*==============================================================*/
create table auth.RESOURCES
(
   RESOURCE_ID               bigint                         not null primary key auto_increment comment '主键',
   APP_ID               bigint                         null  comment '所属应用',
   RESOURCE_NAME             char(200)                      null comment '菜单名称',
   PARENT_ID            bigint                         null comment '上级ID',
   DESCRIPTION          varchar(200)                   null comment '说明',
   PATH                 varchar(2000)                  null comment '菜单链接',
   ACCESS_CTRL          varchar(10)                    null comment '访问控制：login： 登录后访问。 perm：授权后访问。open：无需登录或授权访问',
   ICON                 varchar(50)                    null comment '菜单图标',
   SORT                 int                            null comment '排序',
   RESOURCE_TYPE             int                            null comment '资源类型  1 菜单  2 按钮',
   PERMISSION_STR       varchar(200)                   null comment '权限标识',
   ROUTER_NAME          varchar(200)                   null comment '路由名称',
   ROUTER_PATH          varchar(200)                   null comment '路由地址',
   ROUTER_COPM          varchar(200)                   null comment '路由组件',
   ROUTER_CACHE         int                            null comment '路由缓存  1 是 0否'
)comment '资源表';

drop table if exists auth.USER_ROLE;

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table auth.USER_ROLE 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   ROLE_ID              bigint                         null comment '角色ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '角色表';

drop table if exists auth.USER_PERMISSION;

/*==============================================================*/
/* Table: USER_PERMISSION                                       */
/*==============================================================*/
create table auth.USER_PERMISSION 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   PERMISSION_ID        bigint                         null comment '权限ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '用户权限';


drop table if exists auth.GROUPS;

/*==============================================================*/
/* Table: GROUPS                                               */
/*==============================================================*/
create table auth.GROUPS
(
   GROUP_ID             bigint                         not null primary key auto_increment comment '主键',
   GROUP_NAME           varchar(200)                   null comment '用户组名称',
   DESCRIPTION          varchar(200)                   null comment '用户组描述',
   DEL                  varchar(10)                    null comment '是否删除 1是  0否'
) comment '用户组';


drop table if exists auth.USER_GROUP;

/*==============================================================*/
/* Table: USER_GROUP                                            */
/*==============================================================*/
create table auth.USER_GROUP 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   GROUP_ID             bigint                         null comment '用户组ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '用户组';

drop table if exists auth.GROUP_ROLE;

/*==============================================================*/
/* Table: GROUP_ROLE                                            */
/*==============================================================*/
create table auth.GROUP_ROLE 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   ROLE_ID              bigint                         null comment '角色ID',
   GROUP_ID             bigint                         null comment '用户组ID'
) comment '用户组';


drop table if exists auth.ACTIONS;

/*==============================================================*/
/* Table: ACTIONS                                               */
/*==============================================================*/
create table auth.ACTIONS 
(
   ACTION_ID            bigint                         not null primary key auto_increment comment '主键',
   MENU_ID              bigint                         null comment '所属菜单',
   ACTION_NAME          varchar(200)                   null comment '操作名称',
   DESCRIPTION          varchar(200)                   null comment '操作说明',
   PERMISSION           int                            null comment '权限位 1-32   ',
   ICON                varchar(100)                  null comment '图标',
   ACTION_URL           varchar(2000)                  null comment '权限字符串操作链接（rest风格需要指定参数以前部分）',
   ACTION_FUN           varchar(2000)                  null comment '操作函数'
) comment '菜单操作（按钮）';


drop table if exists auth.DICT;

/*==============================================================*/
/* Table: DICTIONARIES                                          */
/*==============================================================*/
create table auth.DICT
(
   DICT_ID              bigint                         not null primary key auto_increment comment '主键',
   
   D_TYPE               varchar(200)                   null comment '字典类型',
   D_VALUE              varchar(200)                   null comment '字典名称',
   D_CODE               varchar(200)                   null comment '字典值',
   PARENT_CODE         varchar(200)                   null comment '上级字典值，树形结构选择'
) comment '系统字典表';


drop table if exists DICT_TYPE;

/*==============================================================*/
/* Table: DICT_TYPE                                             */
/*==============================================================*/
create table DICT_TYPE 
(
   DICTTYPE_ID          bigint                         not null primary key auto_increment comment '主键',
   D_TYPE_NAME          varchar(200)                   null comment '字典类型名称',
   D_TYPE               varchar(200)                   null comment '字典类型编号'
) comment '系统字典类型表';


```