# OneBlog-Server

# 部署
1. 下载
```shell
git clone https://github.com/houxinlin/OneBlog-Server.git
```
2. 修改配置文件

在`/src/main/resources/`中创建`application-prod.properties`文件，内容是数据库配置
```
spring.datasource.url=jdbc:mysql://xxxx:3306/db_blog
spring.datasource.username=root
spring.datasource.password=12345678
```
3. 在数据库中导入src/main/resources/sql/db_blog.sql

4. 安装elasticsearch

5. 打包
```java
./gradlew  bootJar 
```
6. 运行
```java
nohup java -jar OneBlog.jar & 
```
6. 配置Nginx
```
server{
	charset utf-8;
	listen 6060;
	server_name xxxxx;

        location / {
           root /var/www/blog/dist/;
           try_files $uri $uri/ @router;
           index  index.html index.htm;
        }
        location @router {
           rewrite ^.*$ /index.html last;
        }

}

```
8. 后端代理
```
location /OneBlog/ {
        proxy_set_header Host $host;
        proxy_set_header x-real-ip $remote_addr;
        proxy_set_header X-Real-Port $remote_port;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://localhost:8087/OneBlog/;
}
```
