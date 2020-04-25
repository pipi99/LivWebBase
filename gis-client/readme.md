# GIS客户
  ## 安装nodejs,vue,vue-cli
    nodejs下载安装：https://nodejs.org/en/
    修改npm的本地仓库:
      npm list –global
      npm config ls
      npm config get prefix
      npm config set prefix  "f:\nodejs\node_global"
      npm config set cache  "f:\nodejs\node_cache"
    
    环境变量配置
      NODE_PATH=F:\nodejs\node_global\node_modules
      PATH=F:\nodejs\node_global  (放到默认的npm环节变量之前，比如：C:\Users\w\AppData\Roaming\npm)
    
    设置淘宝镜像：npm install -g cnpm --registry=https://registry.npm.taobao.org
    
    webpack安装：
      npm install --global webpack
      npm install --save-dev webpack-cli
          
    Vue安装：
      npm install -g vue
      cnpm install --global vue-cli
      
    
      
  ## 一、vue创建项目(管理员身份运行 cmd)
    创建项目：
      cd E:\workspace\wg\LivWebPage
      vue init webpack livol
      
   安装OpenLayer
   
     cnpm install ol -D
   
   
     cnpm i  olcs -D
   安装 cesium
   
     cnpm install cesium -D
     cnpm install  copy-webpack-plugin -D
   配置 webpack.base.config.js
  ```
    const webpack = require('webpack')
    const CopyWebpackPlugin = require('copy-webpack-plugin')
    // The path to the CesiumJS source code
    const cesiumSource = 'node_modules/cesium/Source';
    const cesiumWorkers = '../Build/Cesium/Workers';
    
    output: {
       filename: '[name].js',
       path: path.resolve(__dirname, 'dist'),

       // Needed to compile multiline strings in Cesium
       sourcePrefix: ''
    },
    amd: {
       // Enable webpack-friendly use of require in Cesium
       toUrlUndefined: true
    },
    node: {
       // Resolve node module use of fs
       fs: 'empty'
    },
    resolve: {
        alias: {
            // CesiumJS module name
            cesium: path.resolve(__dirname, cesiumSource)
        }
    },
    plugins: [
          // Copy Cesium Assets, Widgets, and Workers to a static directory
          new CopyWebpackPlugin([{ from: 'node_modules/cesium/Build/Cesium/Workers', to: 'Workers' }]),
          new CopyWebpackPlugin([{ from: 'node_modules/cesium/Build/Cesium/ThirdParty', to: 'ThirdParty' }]),
          new CopyWebpackPlugin([{ from: 'node_modules/cesium/Build/Cesium/Assets', to: 'Assets' }]),
          new CopyWebpackPlugin([{ from: 'node_modules/cesium/Build/Cesium/Widgets', to: 'Widgets' }]),
          new webpack.DefinePlugin({
              // Define relative base path in cesium for loading assets
              CESIUM_BASE_URL: JSON.stringify('')
          })
    ],
  ```
   修改webpack.base.config.js，添加cesium
   
### 问题纪录
  ```
    1、package.json中删除 并 升级安装 webpack  wepack-cli  webpac-dev-server
    2、webpack4 TypeError: compilation.mainTemplate.applyPluginsWaterfall is not a function 解决方案
      cnpm i --save-dev html-webpack-plugin@next
      cnpm i extract-text-webpack-plugin
      --各种版本冲突之后（***package.json中一定要删除再安装***）
    
  ```
     
     
     
    

      
    
    