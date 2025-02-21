# docker installation

## 1. Centos7安装docker

* 步骤 1：更新系统
```
sudo yum update -y
```

* 步骤 2：卸载旧版本的 Docker（如果有）
```
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
```

* 步骤 3：安装必要的依赖包
```
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

* 步骤 4：添加 Docker 官方软件源
```
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```

* 步骤 5：安装 Docker Engine
```
sudo yum install docker-ce docker-ce-cli containerd.io
```

* 步骤 6：启动并设置 Docker 开机自启
```
sudo systemctl start docker
sudo systemctl enable docker
```

* 步骤 7：验证 Docker 安装
```
sudo docker run hello-world
```
如下输出
```
Hello from Docker!
This message shows that your installation appears to be working correctly.
...
```