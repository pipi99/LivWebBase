### web桌面项目 <sub><small>sql脚本</small></sub>

 DDL
```mysql
drop DATABASE if exists `mmwebdesktop`;
CREATE DATABASE `mmwebdesktop` /*!40100 DEFAULT CHARACTER SET utf8 */;
use mmwebdesktop;

drop table if exists DICT;

/*==============================================================*/
/* Table: 数据字典表                                                  */
/*==============================================================*/
create table DICT 
(
   ID                   bigint                         not null  primary key auto_increment comment '主键',
   DICTTYPE             varchar(128)                   null comment '字典类型：desktopresolution分辨率  ，desktopbgimg背景图片，desktopsubject',
   DCODE                 varchar(128)                   null comment '编码',
   DNAME                 varchar(256)                   null comment '名称',
   REMARK               varchar(256)                   null comment '说明'
) comment '数据字典';
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','19201080','140,140','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','16801050','140,140','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1600900','140,140','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1440900','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','14001050','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1366768','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1360768','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','12801024','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1280960','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1280800','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1280768','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1280720','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1280600','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1152864','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','1024768','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','800600','120,120','分辨率');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopresolution','800600','120,120','分辨率');

insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopsubject','theme_bai','梦幻白','主题');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopsubject','theme_hei','经典黑','主题');

insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_01','beijing_01','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_02','beijing_02','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_03','beijing_03','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_04','beijing_04','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_05','beijing_05','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_06','beijing_06','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_07','beijing_07','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_08','beijing_08','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_09','beijing_09','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_10','beijing_10','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_11','beijing_11','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_12','beijing_12','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_13','beijing_13','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_14','beijing_14','背景图片');
insert into DICT(dicttype, DCODE, DNAME, remark) values('desktopbgimg','beijing_15','beijing_15','背景图片');


drop table if exists DESKTOPSUBJECT;

/*==============================================================*/
/* Table: 桌面主题表                                        */
/*==============================================================*/
create table DESKTOP_SUBJECT 
(
   ID                   bigint                         not null primary key auto_increment  comment '主键',
   SUBJECT              int                            null comment '主题名称',
   BGCOLOR              varchar(128)                   null comment '背景颜色',
   BGIMG                int                            null comment '背景图片',
   USER_ID              int                            null comment '所属用户账号',
   DESKTOP_RESOLUTION   varchar(128)                   null comment '屏幕分辨率'
) comment '桌面主题表';

drop table if exists DESKTOP;

/*==============================================================*/
/* Table: 用户桌面表                                                */
/*==============================================================*/
create table DESKTOP 
(
   ID                   bigint                         not null primary key auto_increment comment '主键',
   SORT                 int                            null comment '排序',
   ALIAS                varchar(128)                   null comment '名称',
   DEL                  int                            null comment '是否删除  1是  0否',
   ISDEFAULT            int                            null comment '是否默认展示1是 0否，如果都为0 取第一个，如果默认多个为1 按排序取第一个',
   USER_ID              bigint                         null comment '所树用户ID'
)comment '用户桌面';


drop table if exists DESKTOP_ELEMENTS;

/*==============================================================*/
/* Table: 桌面元素，文件夹，APP,挂件等                             */
/*==============================================================*/
create table DESKTOP_ELEMENTS 
(
   ID                   bigint                         not null comment '主键',
   TITLE                varchar(128)                   null comment '名称',
   LOCATION             int                            null comment '位置',
   INDEXLOCATIONARR     varchar(128)                   null comment '位置，所占网格的下标数组 a1,a2,a3..',
   SIZE_XY              varchar(128)                   null comment '网格坐标，  x,y',
   ICONS                varchar(16)                    null comment '图标',
   ALLOWDEL             int                            null comment '是否可删除 1是  0否',
   ALLOWRENAME          int                            null comment '是否允许重命名  1 是 0否',
   ALLOWDRAG            int                            null comment '是否可拖动 1是  0否',
   ALLOWSWITCHPAGE      int                            null comment '是否允许切换页面  1是 0否',
   DESKTOP_ID           bigint                         null comment '外键，桌面ID',
   PARENT_FOLDERID      bigint                         null comment '内连接，外键 上级目录ID',
   DEEP                 int                            null comment '文件夹级别1，2，3，4 限制最多4级',
   IFRAMEURL            varchar(2048)                  null comment '链接',
   WIDGET_TYPE          varchar(64)                    null comment '桌面元素类型：folder ,app ,widget'
) comment '桌面元素，文件夹，APP,挂件等';



```