## 源代码
> https://github.com/SkipToYourSoul/smart-sensor

## 项目部署

#### 串口通信模块：

* copy /lib/RXTXcomm.jar to <JAVA_HOME>\jre\lib\ext;
* copy /lib/rxtxSerial.dll to <JAVA_HOME>\jre\bin;
* copy /lib/rxtxParallel.dll to <JAVA_HOME>\jre\bin

#### socket通信模块

* 使用flink接收socket消息
* 比较memcache和redis，作为中间缓存

## 说明

* application.properties文件由于每个机器配置不同，在同步代码时可以使用以下命令忽略：git update-index --assume-unchanged src/main/resources/application.properties