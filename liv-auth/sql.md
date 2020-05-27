DDL
```mysql

drop DATABASE if exists `auth`;
CREATE DATABASE `auth` /*!40100 DEFAULT CHARACTER SET utf8 */;
use auth;

drop table if exists USER;

/*==============================================================*/
/* Table: USER                                                */
/*==============================================================*/
create table USER 
(
   USER_ID              bigint                         not null primary key auto_increment comment '主键',
   USER_NAME            varchar(36)                       null comment '用户名称',
   ORG_ID               bigint                         null comment '组织机构',
   ALIAS                varchar(100)                      null comment '姓名',
   MOBILE               int                            null comment '手机',
   EMAIL                varchar(100)                      null comment '邮箱',
   GENDER               varchar(8)                        null comment '性别',
   DEGREE               int                            null comment '学历',
   BIRTHDAY             date                           null comment '出生年月日',
   PHOTO                varchar(50)                       null comment '照片',
   CREATE_DATE          date                           null comment '创建日期',
   PASSWORD             varchar(100)                      null comment '密码',
   SALT                 varchar(256)                      null comment '盐',
   LOCKED               varchar(8)                        null comment '是否锁定  1是  0否',
   LOCKTIME             timestamp                        null comment '锁定时间'
) comment '用户信息表';


drop table if exists ORGAN;

/*==============================================================*/
/* Table: ORGAN                                               */
/*==============================================================*/
create table ORGAN 
(
   ORGAN_ID             bigint                         not null primary key auto_increment comment '主键',
   ORGAN_NAME           char(200)                      null comment '机构名称',
   PARENT_ID            bigint                         null comment '上级机构ID',
   ORGAN_LEVEL          varchar(10)                    null comment '机构级别',
   ORGAN_TYPE           varchar(10)                    null comment '机构类型',
   DESCRIPTION          varchar(500)                   null comment '机构描述',
   STATE                date                           null comment '数据状态'
) comment '组织机构表';


drop table if exists ROLE;

/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table ROLE 
(
   ROLE_ID              bigint                         not null primary key auto_increment comment '主键',
   ROLE_NAME            varchar(200)                   null comment '角色名称',
   ROLE_ALIAS            varchar(200)                   null comment '角色名称',
   CREATE_DATE          date                           null comment '创建日期',
   DESCRIPTION          varchar(200)                   null comment '角色描述',
   DEL                  varchar(10)                    null comment '是否删除 1是0否'
) comment '角色表';


drop table if exists ROLE_PERMISSION;

/*==============================================================*/
/* Table: ROLE_PERMISSION                                       */
/*==============================================================*/
create table ROLE_PERMISSION 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   PERMISSION_ID        bigint                         null comment '权限ID',
   ROLE_ID              bigint                         null comment '角色ID'
)comment '角色权限关系表';


drop table if exists PERMISSION_RESOURCE;

/*==============================================================*/
/* Table: PERMISSION_RESOURCE                                   */
/*==============================================================*/
create table PERMISSION_RESOURCE 
(
   PERMISSION_ID        bigint                         not null primary key auto_increment comment '主键',
   PERMISSION      int                            null comment '权限类型',
   RESOURCE_ID          bigint                         null comment '资源ID'
) comment '权限资源表';


drop table if exists MENU;

/*==============================================================*/
/* Table: MEUN                                                  */
/*==============================================================*/
create table MENU 
(
   MENU_ID              bigint                         not null primary key auto_increment comment '主键',
   MENU_NAME            char(200)                      null comment '菜单名称',
   PARENT_ID            bigint                         null comment '上级ID',
   DESCRIPTION          varchar(200)                   null comment '说明',
   M_URL                varchar(2000)                  null comment '菜单链接',
   IS_SHARE             varchar(10)                  null comment ' 是否允许任意用户访问 1是 0否'
)  comment '菜单表';

drop table if exists USER_ROLE;

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table USER_ROLE 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   ROLE_ID              bigint                         null comment '角色ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '角色表';

drop table if exists USER_PERMISSION;

/*==============================================================*/
/* Table: USER_PERMISSION                                       */
/*==============================================================*/
create table USER_PERMISSION 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   PERMISSION_ID        bigint                         null comment '权限ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '用户权限';


drop table if exists GROUPS;

/*==============================================================*/
/* Table: GROUPS                                               */
/*==============================================================*/
create table GROUPS
(
   GROUP_ID             bigint                         not null primary key auto_increment comment '主键',
   GROUP_NAME           varchar(200)                   null comment '用户组名称',
   DESCRIPTION          varchar(200)                   null comment '用户组描述',
   DEL                  varchar(10)                    null comment '是否删除 1是  0否'
) comment '用户组';


drop table if exists USER_GROUP;

/*==============================================================*/
/* Table: USER_GROUP                                            */
/*==============================================================*/
create table USER_GROUP 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   GROUP_ID             bigint                         null comment '用户组ID',
   USER_ID              bigint                         null comment '用户ID'
) comment '用户组';

drop table if exists GROUP_ROLE;

/*==============================================================*/
/* Table: GROUP_ROLE                                            */
/*==============================================================*/
create table GROUP_ROLE 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   ROLE_ID              bigint                         null comment '角色ID',
   GROUP_ID             bigint                         null comment '用户组ID'
) comment '用户组';

```