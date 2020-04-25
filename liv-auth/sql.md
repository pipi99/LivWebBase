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
   LOCKED               varchar(8)                        null comment '是否锁定  1是  0否'
) comment '用户信息表';

```