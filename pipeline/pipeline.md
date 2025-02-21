# pipeline

## 1. pipeline简介

1）概念
Pipeline，简单来说，就是一套运行在jenkins上的工作流框架，将原来独立运行于单个或者多个节点的任务连接起来，实现单个任务难以完成的复杂流程编排和可视化的工作。

2）使用Pipeline有以下好处：
代码：Pipeline以代码的形式实现，通常被检入源代码控制，使团队能够编辑，审查和迭代其传送流程。
持久：无论是计划内的还是计划外的服务器重启，Pipeline都是可恢复的。
可停止：Pipeline可接收交互式输入，以确定是否继续执行Pipeline。
多功能：Pipeline支持现实世界中复杂的持续交付要求。它支持fork/join、循环执行，并行执行任务的功能。
可扩展：Pipeline插件支持其DSL的自定义扩展，以及与其他插件集成的多个选项。

3）如何创建 Jenkins Pipeline呢？
* Pipeline脚本是由Groovy语言实现的，但是没必要单独去学习Groovy
* Pipeline支持两种语法：**Declarative(声明式)**和**Scripted Pipeline(脚本式)**语法
* Pipeline也有两种创建方法：可以直接在jenkins的Web UI界面中输入脚本；也可以通过创建一个Jenkinsfile脚本文件放入项目源码库中（一般都推荐在jenkins中直接从源代码控制(SCM)中直接载入Jenkinsfile Pipeline这种方法）


## 2. 安装pipeline

* 需要安装pipeline插件，默认已经安装
![alt text](image.png)

* 在新建item时，就可以选择pipeline
![alt text](99abc476-3e43-43a4-a1ab-71caafe0af79.png)

* 创建pipeline项目
![alt text](bee0d24b-adbe-40b0-bebb-40f0a8ceee77.png)

## 3. pipeline语法
### 3.1 Declarative Pipeline-声明式语法 / 官方推荐
* 基本语法结构
![alt text](image-1.png)

* 简单demo
![alt text](image-2.png)
```
pipeline {
    agent any

    stages {
        stage('Pull code') {
            steps {
                echo 'Pull code'
            }
        }
        stage('Build project') {
            steps {
                echo 'Build project'
            }
        }
    }
}
```
* 构建pipeline项目
![alt text](412fe98c-0d96-41d4-9dc8-474a48f24893.png)

### 3.2 Scripted Pipeline-脚本式语法
* 基本语法结构
![alt text](image-3.png)
```
node {
    def mvnHome
    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
        git 'https://github.com/jglick/simple-maven-project-with-tests.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        mvnHome = tool 'M3'
    }
    stage('Build') {
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean package'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
            }
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/*.jar'
    }
}

```

* 简单demo
![alt text](56ef025d-4001-433d-939d-824fb47e9769.png)
```
node {
    def mvnHome
    stage('Pull code') {
        echo 'Pull code'
    }
    stage('Build project') {
        echo 'Build project'
    }
}
```
输出和声明式是一样的
![alt text](5b208fda-cd66-46a4-8dd9-897edd67b78a.png)

## 4. Pipeline项目-实现拉取/构建/部署项目

可以使用Pipeline语法生成一些script
![alt text](04e8be2b-904a-4dec-b573-120989608d11.png)

![alt text](0c43b363-148b-4e04-956e-fcd0152177fa.png)

### 4.1 拉取代码
* 通过`Pipeline Syntax`生成脚本，`checkout: Check out from version control`
![alt text](27f81150-8adf-4ff0-a529-501dbcb4ed0d.png)

![alt text](08e193eb-532c-4c91-9633-3128f7ba0cf7.png)

* 将生成的script拷贝到Pipeline脚本中

![alt text](8c48c28e-ec59-464b-a128-a3658e64d8cc.png)

```
pipeline {
    agent any

    stages {
        stage('Checkout code') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'root-sshkey', url: 'git@192.168.50.130:jenkins/jenkins-java-freestyle.git']])
            }
        }
    }
}
```

* 成功拉取代码

![alt text](bd8fab36-7c84-4e14-8520-ada863a61476.png)

![alt text](435b15d5-f4e2-4a19-a51e-40283ba7e165.png)

### 4.2 构建项目
* 通过`Pipeline Syntax`生成脚本，选择`sh:Shell Script`

![alt text](QQ_1740139738663.png)

* 将生成的脚本拷贝到项目的Pipeline中
![alt text](8a5735e3-5af6-4023-995a-42e8d1fa83e9.png)

* 成功构建代码

![alt text](48eb06ba-80ba-42d4-bbdc-b6f515e7b065.png)

![alt text](7fd9b618-ea0e-4540-9009-b509ca8b9404.png)

### 4.3 部署项目
* 通过`Pipeline Syntax`生成脚本，选择`deploy: Deploy war/ear to a container`，这里的配置和之前freestyle和maven项目的`Post-build Actions`配置类似

![alt text](16a393a1-6908-4648-b8c5-57912e7a4bf1.png)

* 将生成的脚本拷贝到项目的Pipeline中
![alt text](5c650a29-47a4-45e0-8a95-8677e6bdb021.png)

* 成功构建部署

![alt text](fac70a22-f820-4a42-affc-65f9baefe38c.png)


## 5. 将Pipeline脚本进行版本控制-和项目存放在一起
* 在项目根目录下，命名`Jenkinsfile`的文件，将Pipeline脚本拷贝到该文件中

![alt text](d2f424c7-48e3-4f95-92c2-da17ae25dc6f.png)

文件内容如下
```
pipeline {
    agent any

    stages {
        stage('Checkout code') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'root-sshkey', url: 'git@192.168.50.130:jenkins/jenkins-java-freestyle.git']])
            }
        }
        stage('Build project') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy project') {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat_auth', path: '', url: 'http://192.168.50.120:8080/')], contextPath: null, war: 'target/*.war'
            }
        }
    }
}
```

* 配置项目的Pipeline
![alt text](8d38ce7e-d43d-484b-86cf-929b99a14d26.png)
![alt text](c7338922-361d-485d-a20c-7e4e10f01690.png)

* 成功构建部署
![alt text](cb508623-8b04-473c-b046-bece6a0b006e.png)

![alt text](9066f041-1f84-4b1c-b1f7-268d59424ca1.png)