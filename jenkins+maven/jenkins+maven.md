# Jenkins + Maven

## 1. Jenkins构建的项目类型介绍
Jenkins中自动构建项目的类型有很多，常用的有以下三种：
• 自由风格软件项目（FreeStyle Project）
• Maven项目（Maven Project）
• 流水线项目（Pipeline Project）

每种类型的构建其实都可以完成一样的构建过程与结果，只是在操作方式、灵活度等方面有所区别，在实际开发中可以根据自己的需求和习惯来选择。（PS：个人推荐使用流水线类型，因为灵活度非常高）

## 2. 自由风格项目构建
自由风格完成项目的过程：
> 拉取代码->编译->打包->部署

### 2.1 拉取代码
* 新建item
![alt text](3e49ba15-f573-4a2b-83f5-79f064fba561.png)

* 配置代码仓库地址
![alt text](8f700bd1-da83-40f2-b88f-53a1873ff7b7.png)

### 2.2 编译打包
```
echo "build start"
mvn clean package
echo "build end"
```
![alt text](image.png)

![alt text](12c991ef-ef6b-4003-8719-4ec5954f1628.png)

### 2.3 部署
* 安装 `Deploy to container`插件
![alt text](740fec62-630b-4a0e-a919-73ba12ce6182.png)

* 添加tomcat用户凭证

![alt text](31961ee4-125c-4bd7-9ac7-6bb8e4387980.png)

点击`Configure`之后，到页面底部的`Post-build Actions`，点击下拉菜单，可以看到`Deploy war/ear to a container`，如果没有安装`Deploy to container`插件，则不会出现这个选项

![alt text](7f74be01-85a2-47e8-ad29-f348f3b8c966.png)


点击`Deploy war/ear to a container`进行配置；注意，这里**需要将`target/*.jar`修改为`target/*.war`**
![alt text](fc728459-5c99-450e-8896-25c5f7b055e0.png)

* 重新构建项目
![alt text](da39a438-add6-4f8b-a9c4-611cd0558377.png)

* 验证
![alt text](5cae5c0a-b4d5-4d5d-990d-0ceb46e34c42.png)


---
## 问题
### 1. Deploying *.jar to container Tomcat 9.x Remote with context null
![alt text](f66cc279-20b8-412d-8b36-15561207daef.png)

=>

项目打包格式不对，在pom.xml中添加如下配置：
![alt text](2d7212fc-1c56-47f6-a0b0-4c5cab7f1244.png)

### 2. Error assembling WAR: webxml attribute is required 
![alt text](3885b6c6-b58c-4962-8462-3d2c6f4b22cd.png)

=>
需要添加web.xml文件

![alt text](dbca07e1-06e9-4e20-a847-6f4930d62a56.png)
```
<!-- src/main/webapp/WEB-INF/web.xml -->
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <servlet>
        <servlet-name>IndexServlet</servlet-name>
        <servlet-class>com.example.SimpleWebServer.Main$IndexHandler</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>IndexServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```

## 3. Maven项目构建
### 3.1 安装`Maven Integration`插件
![alt text](483fce50-827c-478e-81b9-f0174cc0bcb4.png)

### 3.2 Maven项目构建
> 拉取代码->编译->打包->部署

### 3.3 拉取代码
* 新建item
![alt text](406e36fa-ec02-489a-b12b-0eb39b7db886.png)

* 配置代码仓库地址
![alt text](8986eb7a-6a7d-45a9-9388-44a945a0f0e2.png)

### 3.4 编译打包
![alt text](b8d911cd-fcd0-413e-8981-546e4c4d3cb0.png)

### 3.5 部署
利于`Post-build Actions`进行部署的操作，和freestyle一样

