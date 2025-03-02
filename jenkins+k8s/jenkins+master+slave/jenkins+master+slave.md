# Jenkins的master-slave分布式构建

## 1. 什么事Master-Slave分布式构建

![alt text](image.png)

Jenkins的Master-Slave分布式构建，就是将构建任务分发到不同的Slave节点上，由Slave节点执行构建任务，Master节点负责分发任务和收集结果

## 2. 如何实现Master-Slave分布式构建

### 2.1 开启代理程序的TCP端口
> Manage Jenkins -> Security -> Security -> Agents

![alt text](image-1.png)


### 2.2 创建节点
> Manage Jenkins -> System Configuration -> Nodes

* 可以看到当前的节点信息，点击`New Node`，可以创建新的Node

![alt text](image-2.png)

* 创建节点，填写节点名称，选择`Permanent Agent`，点击`Create`；可以指定节点标签，用于指定构建任务在哪个节点上运行(比如按照项目或团队等进行)，也可以不指定

![alt text](image-3.png)

![alt text](image-4.png)

![alt text](image-5.png)

* 点开`Slave1`，可以查看

![alt text](image-6.png)

运行如下命令，将Slave1的节点添加到Master中
```
curl -sO http://192.168.50.135:8080/jnlpJars/agent.jar
java -jar agent.jar -jnlpUrl http://192.168.50.135:8080/computer/Slave1/jenkins-agent.jnlp -secret f30c85784d4427b3b860100031424b241d077d9604ac9929b1dbd86efbc6f1b6 -workDir "/var/lib/jenkins"
```
![alt text](image-7.png)

* slave1节点已经添加到Master中，并且状态为`Online`

![alt text](image-8.png)

* 在slave节点上安装git
```
yum install -y git
```

* 确保slave节点可以免密访问Gitlab
> 可以像Jenkins server一样，将私有key放到slave节点上

### 2.3 创建Job并分配到Slave节点——freestyle

![alt text](image-9.png)

![alt text](image-10.png)


### 2.4 创建Job并分配到Slave节点——Pipeline

#### 2.4.1 通过label指定节点
* 这里给节点加上标签
![alt text](image-11.png)


* 在pipeline中通过label选择节点——声明式

![alt text](image-13.png)

```
pipeline {
    agent {
        node {
            label 'dev-slave'
        }
    }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
```

![alt text](image-12.png)

#### 2.4.2 通过name指定节点——脚本式

![alt text](image-15.png)
```
node('Slave1') {
    stage('Build') {
        sh 'echo "Hello world"'
    }
}
```

![alt text](image-14.png)