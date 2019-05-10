### 秒杀系统整合

#### 简介
&emsp;&emsp;该项目起源于[慕课网秒杀系列课程](https://www.imooc.com/u/2145618/courses?sort=publish)（共四套）,由于课程是2015年的，某些框架和技术都逐渐被替代，我在实现这套课程的时候选用了**springboot + spring data jpa +　freemarker**模板引擎 ， 并且对系统做了**优化**（比如通过**websocket**在秒杀结束后服务端对客户端的实时消息推送），以及使用**redis分布式锁**解决了多线程高并发情况下的线程安全问题。

#### 适合人群
&emsp;&emsp;1. 学习[慕课网秒杀系列课程](https://www.imooc.com/u/2145618/courses?sort=publish)的同学  

&emsp;&emsp;2. 如果你想快速搭建一个秒杀系统来进行压力测试或进行优化  

&emsp;&emsp;3. 想快速构建一个秒杀的场景

#### 说明
&emsp;&emsp;该项目主要侧重点在于秒杀的几个接口，以及围绕这些接口作出的性能优化（redis缓存，redis分布式锁）以应对高并发的场景，而前端方面较为简单。

#### 使用说明
&emsp;&emsp;1. 修改yml文件中的mysql用户名密码，以及redis用户名密码即可  

&emsp;&emsp;2. 页面入口  [127.0.0.1/seckill/list](http://127.0.0.1/seckill/list)  

&emsp;&emsp;3. 秒杀压测接口 [127.0.0.1/kill/1000/execution](127.0.0.1/kill/1000/execution)  

&emsp;&emsp;4. 核心类 SecPressureTestService(压测类)， RedisLock（redis分布式锁），WebSocketService（websocket服务端核心类)


#### 特别鸣谢
&emsp;&emsp;感谢慕课讲师[yijun zhang](https://www.imooc.com/u/2145618/), 同时感谢在完成过程中一些有帮助的博客。
