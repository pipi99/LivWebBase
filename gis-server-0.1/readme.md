# GIS服务端

  mysql+openlayer+mapdownloader
  ## 瓦片下载器：mapdownloader
      使用 mysql 数据库，配置 ：MapDownloader.exe.config， 打开 mapdownload ,存储方式修改为 mysql
  ### 数据库表拆分规则
     GIS_MIX ： 混合瓦片数据库前缀
     1-7 层瓦片 约 430M (约43688条) 存储到 GIS_MIX_MAPTILES1_7 表中
     8-10 层瓦片 约 1G 存储到 GIS_MAPTILES8_10 表中
     12-13 层瓦片 约 430M 存储到 GIS_MAPTILES12_13 表中
     层瓦片 约 430M 存储到 GIS_MAPTILES1_7 表中
          
     
    

      
    
    