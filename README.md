## 源代码
> https://github.com/SkipToYourSoul/smart-sensor

## 项目部署

#### 串口通信模块：

* copy /lib/RXTXcomm.jar to <JAVA_HOME>\jre\lib\ext;
* copy /lib/rxtxSerial.dll to <JAVA_HOME>\jre\bin;
* copy /lib/rxtxParallel.dll to <JAVA_HOME>\jre\bin

#### socket通信模块

* 使用netty实现socket数据通信
* 比较memcache和redis，作为中间缓存

## 说明

* application.properties文件由于每个机器配置不同，在同步代码时可以使用以下命令忽略：git update-index --assume-unchanged src/main/resources/application.properties
* 若要恢复，可使用命令git update-index --no-assume-unchanged src/main/resources/application.properties
* /lib/*.sql文件存储了用到的MYSQL数据表



### 参考链接

* thymeleaf比较符号：http://www.bubuko.com/infodetail-413210.html
* thymeleaf条件判断：http://www.cnblogs.com/suncj/p/4030393.html
* thymeleaf js取值：http://blog.csdn.net/xiaoyu411502/article/details/48582355
* spring-boot处理url参数：http://blog.csdn.net/u010412719/article/details/69788227
* themeleaf常用属性（include）：http://www.cnblogs.com/hjwublog/p/5051732.html
* themeleaf常用属性（url传参）：http://blog.csdn.net/mygzs/article/details/52472039
* spring-boot-jpa更新数据时指定事务：http://blog.csdn.net/hanghangde/article/details/53241150
* jpa数据映射关系：http://blog.csdn.net/lyg_2012/article/details/70195062
* jpa时间戳操作:http://blog.csdn.net/sushengmiyan/article/details/50360451
* spring-boot与spring security整合后post 403：http://blog.csdn.net/sinat_28454173/article/details/52251004