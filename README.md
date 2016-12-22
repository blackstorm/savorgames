# savorgames.com 前后端分离整站源码
后端 spring + mybatis + druid + ssdb

前端 Vue 全家桶

# 后端
## 如何使用？
前往 target 目录获取 sql 结构。请阅读 sql 文件后导入
后端 Java 使用 mvn 构建，修改配置文件后正常步骤即可。

### 关于fabric
fabfile.py 可以将文件打包并部署到服务器，配置服务器端口修改 fabfile.py 文件

# 前端(vue 1.0)
## 如何使用？
前端没有使用 vue 默认脚手架，使用了 ele 的 cooking

cooking watch 启动本地端口进行监听，cooking.config.js 可以编写配置

cooking build 编译项目

无奈的是 package.json 依赖没有描述完整，跑起来需要看一下 import 的包，然后 npm install (包名)

### 注意：请使用 vue 1.0 版本，注意依赖关系

# nginx

跟 nginx 有什么关系？（请阅读 vue-router 服务器配置）

配置你的 nginx ： 无论访问什么目录都返回 index.html（也就是前端编译后的 src/index.tpl）。例如：www.savorgames.com

spring 后台请配置为 api.savorgames.com
防止冲突
