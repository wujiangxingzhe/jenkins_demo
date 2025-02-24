# 安装harbor

Harbor安装

Harbor安装在192.168.66.120上面

## 1. 先安装Docker并启动Docker

## 2. 先安装docker-compose
```
sudo curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```

## 3. 给docker-compose添加执行权限
```
sudo chmod +x /usr/local/bin/docker-compose
```

## 4. 查看docker-compose是否安装成功
```
docker-compose -version
```

## 5. 下载Harbor的压缩包（本课程版本为：v1.9.2）
```
https://github.com/goharbor/harbor/releases
```

## 6. 上传压缩包到linux，并解压
```
tar -xzf harbor-offline-installer-v1.9.2.tgz
mkdir /opt/harbor
mv harbor/* /opt/harbor
cd /opt/harbor
```

## 7. 修改Harbor的配置
> vi harbor.yml
修改hostname和port
```
hostname: 192.168.66.120
port: 85
```

## 8. 安装Harbor
```
./prepare
./install.sh
```

## 9. 启动Harbor
```
docker-compose up -d  # 启动
docker-compose stop  # 停止
```