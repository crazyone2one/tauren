# Tauren

### Reference Documentation

~~~
涉及到测点数据基本信息，需要将矿端数据库中对应表中的数据复制到本系统的数据库中
~~~

### Guides

* 使用prod环境打包

~~~
mvn clean package
~~~

* 复制backend/target/backend.jar文件到10服务器上/home/app/tauren目录下
* 在10服务器上移除tauren的相关容器和镜像
* 重新生成tauren的镜像和容器，并启动

~~~
cd /home/app/tauren
docker build -t tauren:v1 .
docker run -d -p 18080:8080 --name tauren -v /home/app/files/ftp:/app/files tauren:v1
~~~

### 打包注意事项

* 前端注意事项：
    * frontend/src/api文件夹下index文件
  ~~~
  alovaInstance 注释掉 baseURL: import.meta.env.VITE_APP_BASE_API,
  ~~~
    * frontend文件夹下vite.config.ts文件
  ~~~
  serverConfig 注释掉 rewrite: (path: string) => path.replace(/^\/api/, ""),
  ~~~