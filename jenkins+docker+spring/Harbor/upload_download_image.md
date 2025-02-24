# 从Harbor上传和下载镜像

**不管是上传还是下载镜像，都需要将Harbor地址添加到docker的信任列表，同时登录Harbor**

## 1. 上传镜像

### 1.1 给镜像打上标签
···
docker tag eureka:v1 192.168.66.120:85/tensquare/eureka:v1
···

### 1.2 登录Harbor
> docker login -u 用户名 -p 密码 192.168.66.120:85

### 1.3 推送镜像
```
docker push 192.168.66.120:85/tensquare/eureka:v1
```
The push refers to repository [192.168.66.120:85/tensquare/eureka]
Get https://192.168.66.120:85/v2/: http: server gave HTTP response to HTTPS client

* 这时会出现以上报错，是因为Docker没有把Harbor加入信任列表中

### 1.4 把Harbor地址加入到Docker信任列表
> vi /etc/docker/daemon.json
```
{
    "registry-mirrors": ["https://zydio188.mirror.aliyuncs.com"],
    "insecure-registries": ["192.168.66.120:85"]
}
```
需要重启Docker，重新推送即可


## 2. 下载镜像
* 从另外的机器上下载镜像
* 下载镜像也需要先登录

### 2.1 修改Docker配置，添加Harbor地址到docker信任列表
> vi /etc/docker/daemon.json
```
{
    "registry-mirrors": ["https://zydio188.mirror.aliyuncs.com"],
    "insecure-registries": ["192.168.66.120:85"]
}
```
重启docker

### 2.2 先登录，再从Harbor下载镜像
```
docker login -u 用户名 -p 密码 192.168.66.102120:85
docker pull 192.168.66.120:85/tensquare/eureka:v1
```
