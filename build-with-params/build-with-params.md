# 参数化构建


* 需求如下

![alt text](image.png)

**这里有点要注意：当通过Gitlab webhook或Integration通过trigger事件触发的时候，branch的值将是事件触发时代码所在的branch**

## 1. 配置参数
![alt text](image-1.png)

![alt text](image-2.png)

添加上述配置之后，就可以在项目的导航栏`Build with Parameters`

![alt text](image-5.png)

点击`Build with Parameters`，进入参数配置页面

![alt text](image-6.png)

## 2. 更改Jenksinfile

![alt text](image-3.png)

将`main`修改为`${branch}`

![alt text](image-4.png)

## 3. 构建
![alt text](20e5af66-eb1a-4b95-8726-223c818b145d.png)


## 问题
* Jenkins项目配置的trigger事件，包含Merge
![alt text](image-7.png)

* Gitlab项目配置的webhook，没有包含Merge
![alt text](image-8.png)

* 但是Merge分支的时候仍然触发了构建，这两个地方的配置是优先级高低的的问题，还是两处的配置进行了合并
  * 应该是合并的关系，Jenkins项目的配置中取消了Merge的trigger，Gitlab项目的webhook配置中包含了Merge，最终触发了构建