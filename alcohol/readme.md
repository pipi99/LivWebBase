###项目说明
  * tomcat控制台乱码
  ---
    在 tomcat / conf 目录下，设置 logging.properties ，增加参数  java.util.logging.ConsoleHandler.encoding = GBK，重启