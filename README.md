# 弱联网服务端架构
---
## HTTPServer说明
此Demo为个人兴趣所写，是本人通过半年多服务端学习的一次大胆尝试，将之前学到的很多知识都融合在一起，除了对以前的知识整合之外，也不断进行改变和创新！目前该项目还是一个简单框架，可能未来发展还会加入更多新鲜元素，尽请期待！

## 关键技术
Maven、Netty、JSON-RPC、MySQL、Mybatis、Redis、Jedis、Base64、AES、MD5、JMX、slf4j、token验证、反向代理、路由分发、双重锁单例等。

## HTTP弱联网
一般来说弱联网包含单机游戏和联网游戏的特点，既允许离线游戏又允许联网游戏。普遍适用于交互需求较低的游戏，例如消消乐、卡牌、跑酷等。通常使用HTTP协议实现客户端与服务端之间的通讯，客户端请求一次，服务端响应后立即断开。优势在于减少服务端网络带宽，同时客户端在没有稳定的网络条件下还可以保证游戏效果！我们项目没有采用Tomcat开发HTTP服务端，而是采用Netty开发HTTP服务端，Netty作为开源NIO框架，提供异步的、事件驱动的网络应用程序框架，能够支持大量用户并发行为。

## 架构发展
1、最初服务端架构----最普通的C/S模型，所有游戏逻辑部署在一台服务器上。  
![此处输入图片的描述][1]  
2、优化服务端架构----登录服务分离，减少主服务器压力。  
![此处输入图片的描述][2]  
3、分布式服务端架构----逻辑服务拆分（场景、战斗、聊天等），服务端由Login服务器、Gate服务器和RPC服务器构成。RPC服务器承担服务拆分后的各个服务，同时加入Redis内存型数据库，加速数据读写。  
![此处输入图片的描述][3]

## 项目架构
1、本项目采用上述第三种架构，将登陆服务、逻辑服务拆分。  
2、项目中包含本地启动端口和网页启动端口。本地启动端口用于在测试中直接启动服务端，网页启动端口用于在网页中启动服务端。通过修改net.properties文件下的USE\_NET参数，即可在两种启动方式中切换。功能上由JMX实现，不过这个功能被人喷挺鸡肋的......  
3、端口明细：  

| 服务器      | 本地启动端口 | 网页启动端口 |
|:-----------:|:------------:|:------------:|
| Login服务器 | 9901         | 9900         |
| Gate 服务器 | 9911         | 9910         |
| RPC  服务器 | 9921         | 9920         |

| 数据库 | 端口 |
|:------:|:----:|
| MySQL  | 3306 |
| Redis  | 6379 |

## 整体流程
1、登录服务：  
-初始客户端只记录Login服务器的ip和端口。  
--客户端发送请求，Login服务器接收消息，验证用户名和密码。  
---验证成功，通过公式计算token，并将token和id存入Redis数据库，作为成功登录的凭证。同时返回id、token、Gate服务器的ip和port到客户端。  
-----客户端获取响应后，设置Gate服务器ip和port，以后只与Gate服务器交互。  
`session = MD5(固定前缀 "KEY" + "\_" + id + "\_" + 时间戳)`  

2、逻辑服务：  
-客户端将带有id和token的请求消息发送给Gate服务器。  
--Gate服务器判断token和id是否相符。不存在说明request无效或者token过期，丢弃；存在说明验证成功，继续。  
----Gate服务器作为反向代理服务器，根据RequestCode和SubCode协议将Request发送给RPC服务器对应接口处理。  
-----RPC服务器获取Request，处理消息并返回响应到Gate服务器，再由Gate服务器将响应返回给客户端。  
`Client ----> Gate ----> Node ----> Gate ----> Client`  

## 协议
客户端与服务端之间的交互最重要的就是制定一致的协议，项目中定义了客户端到服务端的请求码（RequestCode）和服务端到客户端的响应码（ResponseCode），以及用于区分不同服务的子请求码（SubCode）。
```请求码
public static final short TEST = 9981; // 测试
public static final short USER = 10000; // 登录
public static final short INFO = 20000; // 信息
public static final short SCORE = 30000; // 成绩
```
```响应码
public static final short SUCCESS = 1000; // 成功
public static final short COMMON_ERR = 5000; // 默认错误
public static final short SERVER_ERR = 5001; // 服务器错误
public static final short AUTH_ERR = 5002; // 认证错误
public static final short PROTO_ERR = 5003; // 协议错误
public static final short Login_Username_Err = 10001; // 用户名不存在
public static final short Login_Password_Err = 10002; // 用户密码错误
public static final short Login_Black_List = 10003; // 用户进入黑名单
public static final short Register_Username_Err = 10004; // 用户名已存在
public static final short Register_Username_Ill = 10005; // 用户名不合法
```
```子请求码
public static final short TEST = 9981; // 测试
public static final String Login = "login"; // 用户登录
public static final String Register = "register"; // 用户注册
public static final String GetInfo = "getInfo"; // 获取信息
public static final String UpdateInfo = "updateInfo"; // 更新信息
public static final String GetScore = "getScore"; // 获取成绩
public static final String UpdateScore = "updateScore"; // 更新成绩
```

## 消息组成
`Request(请求class) = id(标识long) + token(口令string) + requestCode(请求码short) + subCode(子请求码string) + data(数据string)`
`Response(响应class) = responseCode(响应码) + data(数据string)`
1、data是JSON字符串，通过JSON.toJSONString序列化特定的类。  
2、Request和Response同样通过JSON.toJSONString序列化为JSON字符串。  
3、最后Request和Response通过Base64编码解码(默认)或者AES加密解密(net.properties文件下的USE\_AES参数)，防止抓包消息内容。  

## 客户端测试
1、并发测试文件在LoginServer的test/java包下的`HttpClientMulti.java`，并发测试登录与获取信息。  
2、完整测试文件在LoginServer的test/java包下的`HttpClient.java`，内含几种完整测试请求。  
![此处输入图片的描述][4]

## 服务端解释
保证启动Redis服务，自行百度下载！！！  
启动LoginServer下的GameInit.java  
启动GateServer下的GameInit.java  
启动RPCServer下的GameInit.java  
0、`LoginServer`、`GateServer`、`RPCServer`工程共同文件：  
`core`包：  
----GameInit，开启、关闭服务器的主函数入口  
----GameServer，服务器管理封装  
----GameServerMBean，服务器管理接口  
`protocol`包：  
----Request，请求  
----RequestCode，请求码  
----ResponseCode，响应码  
----Response，响应  
----SubCode，子请求码  
`util`包：  
----AES，高级对称加密算法  
----Authentication，token生成、token认证  
----Coder，Base64加解密、MD5、SHA1加密  
----Config，初始配置  
----Property，读取配置  
----Redis，Redis工具类  
----SqlSessionFactoryUtil，Mybatis工厂类  

1、`LoginServer`工程：  
`handler`包：  
----UesrHanler，处理用户登录注册事件  
`manager`包：  
----ShieldManager，屏蔽词数据库管理类  
----UserManager，用户数据库管理类  
`mapper`包：  
----ShieldMapper，屏蔽词Mybatis映射类  
----UserMapper，用户Mybatis映射类  
`model`包：  
----LoginModel，登录模型  
----UserModel，用户模型  
`server`包：  
----Filter，消息解析、token验证类  
----HttpHandler，消息接收、发送处理类  
----HttpServer，由Netty实现的HTTP服务端  
----Router，转发消息到对应Handler处理  

2、`GateServer`工程：  
`server`包：  
----Filter，消息解析、token验证类  
----HttpHandler，消息接收、发送处理类  
----HttpServer，由Netty实现的HTTP服务端  
----RPCClient，Gate服务端与RPC服务端交互的管理类  

3、`RPCServer`工程：  
`manager`包：  
----InfoManager，信息数据库管理类  
----ScoreManager，成绩数据库管理类  
`mapper`包：  
----InfoMapper，信息Mybatis映射类  
----ScoreMapper，成绩Mybatis映射类  
`model`包：  
----InfoModel，用户信息模型  
----ScoreModel，成绩模型  
`server`包：  
----HttpHandler，消息接收、发送处理类  
----HttpServer，由Netty实现的HTTP服务端  
----RPCServer，转发消息到对应Service服务处理  
`service`包：  
----InfoService，处理客户端的信息RPC请求  
----ScoreService，处理客户端的成绩RPC请求  

## 书籍推荐
项目中用到了Netty和Mybatis，而这两者的技术都不是几句话能够说清楚道明白的，所以推荐两本书籍`《Netty权威指南》`和`《深入浅出Mybatis技术原理与实战》`，看完基本能够了解使用。Netty是别人封装好的网络层开源框架，Mybatis则是别人封装好的数据库开源框架，所以仅仅依赖这两本书是无法深入理解服务端开发的。要想了解网络底层，你需要看`《Unix网络编程》`来学习Unix、Linux下的原始套接字编程，另外辅助以`《Unix环境高级编程》`来学习Unix、Linux下的服务端开发。最后如果能够了解计算机硬件就更好了，这时候就需要`《深入理解计算机系统》`这本书了！以上的书我都看过，我可以负责任地告诉大家，这几本都不好读，也许你读了前几章还是十分感兴趣的，但是估计坚持不了多几章就虚了。服务端不好学，要学习请坚持！

  [1]: http://www.littleredhat1997.com/code/TEMP/Img/1.png
  [2]: http://www.littleredhat1997.com/code/TEMP/Img/2.png
  [3]: http://www.littleredhat1997.com/code/TEMP/Img/3.png
  [4]: http://www.littleredhat1997.com/code/TEMP/Img/4.png
