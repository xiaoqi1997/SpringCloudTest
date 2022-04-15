# Spring Cloud

> 入门简介
>
> * 微服务架构是独立的，微小轻量的架构设计模式。
> * 满足维度，强在整体，Spring Cloud 里集成相关的项目。

## 1.1 技术栈

1. 服务注册与发现
   * EUREKA 升级 Nacos、Zookeeper、Consul、
2. 服务负载与调用
   1. Ribbon、Feign 升级 LoadBalancer、OpenFeign
3. 服务熔断降级
   1. Hystrix 升级 resillience4j、alibaba sentienl
4. 服务网关
   1. Zuul 升级 gateway
5. 服务分布式配置
   1. Spring Cloud Config 升级 Nacos
6. 服务开发
   1. Spring Boot
7. 服务总线
   1. Bus 升级到 Nacos

## 1.2 Spring Boot和Cloud版本

1. Boot是数字版本，Cloud是英文字母版本
2. Spring boot和Spring Cloud 的版本依赖有约束，对应版本要同步使用。

## 1.3 Maven建项

```java

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.qhk</groupId>
  <artifactId>SpringCloudTest</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>pom</packaging>

  <name>Maven</name>
  <!-- 统一管理jar包版本 -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.16.18</lombok.version>
    <mysql.version>5.1.47</mysql.version>
    <druid.version>1.1.16</druid.version>
    <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
  </properties>

  <!-- 1、只是声明依赖，并不实际引入，子项目按需声明使用的依赖 -->
  <!-- 2、子项目可以继承父项目的 version 和 scope -->
  <!-- 3、子项目若指定了 version 和 scope，以子项目为准 -->
  <dependencyManagement>
    <dependencies>
      <!--spring boot 2.2.2-->
      <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-dependencies</artifactId>
        <version>2.2.2.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--spring cloud Hoxton.SR1-->
      <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-dependencies</artifactId>
        <version>Hoxton.SR1</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
      <!--spring cloud alibaba 2.1.0.RELEASE-->
      <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-alibaba-dependencies</artifactId>
        <version>2.1.0.RELEASE</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>

      <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
      </dependency>
      <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
        <version>${druid.version}</version>
      </dependency>
      <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
      </dependency>
      <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>${junit.version}</version>
      </dependency>
      <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
      </dependency>
      <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>${lombok.version}</version>
        <optional>true</optional>
      </dependency>
    </dependencies>
  </dependencyManagement>
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <fork>true</fork>
          <addResources>true</addResources>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

## 1.4 微服务模块构建

1. 建module、改pom、写yml、主启动、业务类

# 2 引入服务注册中心

## 2.1 什么是服务治理

1. 管理各种服务之间调用。
2. 客户端链接到注册中心并维持心跳。
3. Eureka 两个组件
   * Eureka Servce 提供服务注册中心
   * Eureka Client 通过注册中心进行访问。

## 2.2 创建Eureka的服务端和客户端

* 启动类

```java
// 服务端
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class, args);
    }
}
```

* pom配置

~~~XML
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloudTest</artifactId>
        <groupId>com.qhk</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-eureka-server7001</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>

</project>
~~~

* yml文件

~~~yml
server:
  port: 7001
eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    #false 表示不向注册中心注册自己。
    register-with-eureka: false
    #false 表示自己端就是注册中心，职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
~~~

* 客户端,启动类

~~~java

@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
~~~

* 客户端。pom.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloudTest</artifactId>
        <groupId>com.qhk</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>

    <dependencies>
        <!-- 服务注册中心的客户端端 eureka-client -->
        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-eureka-server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.2.8</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!--包含了sleuth+zipkin-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>

        <dependency>
            <groupId>com.qhk</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>

</project>
~~~

* 客户端yml

~~~yml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/db2019?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: root

eureka:
  client:
    # 表示是否将自己注册进EurekaServer默认为true
    register-with-eureka: true
    # 是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbo使用负载均衡
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7001/eureka

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.qhk.springcloud.entities

~~~

## 2.3 Eureka集群模式

1. 与单机版不同区别在于yml文件的配置

   **7001服务端**

   ~~~yml
   server:
     port: 7001
   eureka:
     instance:
       hostname: eureka7001.com #eureka服务端的实例名称
     client:
       #false 表示不向注册中心注册自己。
       register-with-eureka: false
       #false 表示自己端就是注册中心，职责就是维护服务实例，并不需要去检索服务
       fetch-registry: false
       service-url:
         # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址
         defaultZone: http://eureka7002.com:7002/eureka/
   ~~~

   **7002服务端**

   ~~~yml
   server:
     port: 7002
   
   eureka:
     instance:
       hostname: eureka7002.com #eureka服务端的实例名称
     client:
       #false 表示不向注册中心注册自己。
       register-with-eureka: false
       #false 表示自己端就是注册中心，职责就是维护服务实例，并不需要去检索服务
       fetch-registry: false
       service-url:
         # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址
         defaultZone: http://eureka7001.com:7001/eureka/
   ~~~

   **8001客户端**

   ~~~yml
   server:
     port: 8001
   
   spring:
     application:
       name: cloud-payment-service
     datasource:
       type: com.alibaba.druid.pool.DruidDataSource
       driver-class-name: org.gjt.mm.mysql.Driver
       url: jdbc:mysql://localhost:3306/db2019?useUnicode=true&characterEncoding=utf-8&useSSL=false
       username: root
       password: root
   
   eureka:
     client:
       # 表示是否将自己注册进EurekaServer默认为true
       register-with-eureka: true
       # 是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbo使用负载均衡
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
     instance:
       instance-id: payment8001
       prefer-ip-address: true
   mybatis:
     mapper-locations: classpath:mapper/*.xml
     type-aliases-package: com.qhk.springcloud.entities
   
   ~~~

   **8002客户端**

   ~~~yml
   server:
     port: 8002
   
   spring:
     application:
       name: cloud-payment-service
     datasource:
       type: com.alibaba.druid.pool.DruidDataSource
       driver-class-name: org.gjt.mm.mysql.Driver
       url: jdbc:mysql://localhost:3306/db2019?useUnicode=true&characterEncoding=utf-8&useSSL=false
       username: root
       password: root
   
   eureka:
     client:
       # 表示是否将自己注册进EurekaServer默认为true
       register-with-eureka: true
       # 是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbo使用负载均衡
       fetch-registry: true
       service-url:
         defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
     instance:
       instance-id: payment8002
       prefer-ip-address: true
   mybatis:
     mapper-locations: classpath:mapper/*.xml
     type-aliases-package: com.qhk.springcloud.entities
   
   ~~~

   

## 2.4 Eureka的自我保护机制

1. 即客户端一段时间(默认90s)内无响应后，不会立刻清理此客户端的注册信息，会保存信息。目的是为了保持高可用。CAP中的AP分支。
2. 关闭自我保护机制。
   * eureka.server.enable-self-preservation = false 禁用自我保护机制
   * eureka.server.eviction.interval-timer-in-ms = 2000(ms) 多长时间内无响即删除或保存信息转至自我保护机制。
3. 客户端自我保护机制
   * eureka.client.instance.lease-renewal-interval-in-seconds: 1 客户端向服务端发送心跳的间隔时间间隔，单位为秒(默认是30秒)
   * eureka.client.instance.lease-expiration-duration-in-seconds: 2 服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务。

#  3. Zookeeper 服务注册与发现

## 3.1 Zookeeper服务端安装

1. [官网下载tar包地址](https://downloads.apache.org/zookeeper/)

2. ~~~shell
   # 解压 
   tar -zxvf apache-zookerrper-x.x.x-bin.tar.gz
   # 移动
   mv apache-zookerrper-x.x.x-bin.tar.gz /usr/local/zookeeper-x.x.x
   # 新建data和logs文件夹
   cd /usr/local/zookeeper-x.x.x
   mkdir data
   mkdir logs
   # 初始化配置文件
   cd conf
   cp zoo_sample.cfg zoo.cfg
   # 修改信息
   vim zoo.cfg
   #在文件中增加以下路径，路径为本地存在名称路径
       #数据文件夹
       dataDir=/usr/local/zookeeper-3.6.2/data
       #日志文件夹
       dataLogDir=/usr/local/zookeeper-3.6.2/log
   #修改系统配置文件,配置文件中增加以下配置
   vim /etc/profile
    	#zookeeper配置
       export ZOOKEEPER_HOME=/usr/local/zookeeper-x.x.x/
       export PATH=$ZOOKEEPER_HOME/bin:$PATH
       export PATH
   # 使配置文件生效
   source /etc/profile
   # 云主机需要在手动打开防火墙、虚拟机需要手动配置
   # 手动配置防火墙
   firewall-cmd --permanent --zone=public --add-port=2181/tcp
   # 重启防火墙
   systemctl restart firewalld
   # 查看是否开放成功
   netstat -ntl
   # 启动zookeeper
   cd /usr/local/zookeeper-x.x.x/bin
   ./zkServer.sh start
   # 查看启动状态
   ./zkServer.sh status
   # 如果启动失败去查看日志文件
   cd ../logs
   ls
   # 查看到文件名，vim打开文件
   vim xxx.log
   # 如果为8080端口被占用，zookeeper版本中有个内嵌的管理控制台是通过jetty启动，会占用8080。
   # 端口可以修改zoo.cfg文件来修改
   vim /usr/local/zookeeper-x.x.x/conf/zoo.cfg
   # 增加以下代码
   admin.serverPort=未被占用的端口号
   # 如不是此错误，则查看是否java未安装，是否为2181端口被占用，可根据详细的日志文件来进行查看
   ~~~

   ## 3.2 zookeeper 在yml中的配置

   ~~~yml
   server:
     port: 8004
   # 服务别名--- 注册zookeeper到注册中心名称
   spring:
     application:
       name: cloud-provider-payment
     cloud:
       zookeeper:
         connect-string: 82.157.186.132:2181
   ~~~

   ## 3.3 zookeeper 在注册后是临时节点还是持久节点

   1. 在一定时间内有就存在，超时不存在就删除，所以zookeeper在注册后是一个临时结点。

   ## 4. Consul服务注册与发现

   ## 4.1 Consul简介

   1. 分布式的服务发现和配置管理系统，用Go语言开发。

   2. 提供完整的服务网格的解决方案。

   3. [Consul下载地址](https://www.consul.io/downloads)

   4. [Consul中文文档](https://yushuai-w.gitbook.io/consul/)

   5. [Consul在SpringCloud中的使用](https://www.springcloud.cc/spring-cloud-consul.html)

   6. centOs安装命令

      ~~~shell
      sudo yum install -y yum-utils
      sudo yum-config-manager --add-repo https://rpm.releases.hashicorp.com/RHEL/hashicorp.repo
      sudo yum -y install consul
      sudo yum -y install consul-enterprise.x86_64
      ~~~

   7. 启动命令

      ~~~shell
      cd /bin
      ./consul agent -dev -ui -node=consul-dev -client=0.0.0.0
      ~~~

# 5. consul、zookeeper、Eureka 区别

1. 语言区别，Go、Java、Java

2. Eureka 是ap、ZK和consul是CP
3. CAP关注的数据而不是，整体的系统结构
4. AP架构，为了保证高可用，数据可能会有错误，当A和B没有同步数据，A数据更新宕机后未同步到B，会导致B的数据未更新，返回的还是老数据。
5. CP，只满足了一致性和容错。

# 6. Ribbon负载均衡服务调用

## 6.1 简介

	1. 是一套客户端，用户负载均衡算法和服务调用，就是在配置文件中流出LoadBalaners，目前已经停更。
	2. 进程内的LB，Nginx属于服务端的LB，Ribbon属于本地的负载均衡。集成于消费方的调用。
	3. 所谓的Ribbon就是负载均衡+RestTemplate的使用。

## 6.2 RestTemplate的使用

1. getForObject() 函数，返回的是一个Json串数据
2. getForEntity() 函数，返回的是一个包含头信息等返回信息的Response。

## 6.3 Ribbon的负载均衡算法

1. 轮询、IRule(根据特定算法从服务列表中选择一个服务)。

2. 共七种

   ~~~java
   RoundRobinRule 轮询、
   RandomRule 随机、
   RetryRule 按照RoudRobinRule的策略获取服务，对ROundRobinRule的扩展、响应速度越快的实例选择的权重越大、
   WeightedResponseTimeRule 优先过滤由于多次访问故障而处于跳闸状态的服务、
   BestAvailableRule 过滤故障实例，然后选择并发较小的实例、
   ZonAvoidanceRule 默认规则，符合判断Server所在区域的性能和server的可用性选择。
   ~~~

   

3. Ribbon的规则替换

   * 新建一个不和Main类在同一包下的类

     ~~~java
     package com.qhk.myrule;
     
     import com.netflix.loadbalancer.IRule;
     import com.netflix.loadbalancer.RandomRule;
     import org.springframework.context.annotation.Bean;
     import org.springframework.context.annotation.Configuration;
     
     @Configuration
     public class MySelfRule {
         
         @Bean
         public IRule getMyRule() {
             return new RandomRule();
         }
     }
     
     ~~~

   * 修改启动类

     ~~~java
     @SpringBootApplication
     @EnableEurekaClient
     @RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
     public class CloudConsumerOrder80 {
         public static void main(String[] args) {
             SpringApplication.run(CloudConsumerOrder80.class, args);
         }
     }
     ~~~

## 6.4 Ribbon的负载均衡算法

1. rest接口第几次请求 % 服务器集群总数量 = 实际调用服务器位置下标，每次服务重启动后rest接口计数从1开始。 

# 7. OpenFeign服务调用

## 7.1 简介

1. 是一个声明式的web编写web Service客户端，可以使编写WebService变的更简单。
2. 使用方式是定义一个服务接口然后在上边添加注解。
3. 服务接口绑定器。

## 7.2 服务超时

1. OpenFeign默认是一秒钟超时，需要设置Feign的超时控制。

2. yml文件配置

   ~~~yml
   ribbon:
     ReadTimeout: 5000
     ConnectTimeout: 5000
   ~~~

3. 日志打印

   1. 级别：NONE: 默认的，不显示任何日志, BASIC: 仅记录请求方法，URL、响应状态码及执行时间。HEADERS: 除了Basic中定义的之外，还有请求和响应的头信息,FULL： 除了Headers中定义的信息外，还有请求和响应的正文及元数据。

   2. 如何开启，在yml中进行配置

      ~~~yml
      logging:
        level:
          com.qhk.springcloud.service.PaymentFeignService: debug
      ~~~

      配置类

      ~~~java
      package com.qhk.springcloud.config;
      
      import feign.Logger;
      import org.springframework.context.annotation.Bean;
      import org.springframework.context.annotation.Configuration;
      
      @Configuration
      public class FeignConfig {
      
          @Bean
          Logger.Level feignLoggerLevel() {
              return Logger.Level.FULL;
          }
      }
      
      ~~~

# 8. Hystrix 服务降级<断路器>

## 8.1 简介

1. 分布式面临的问题，扇出效应，扇出的链路上某个微服务不可用了，对服务A的调用会占用越来越多的资源。最终导致系统的崩溃。
2. Hystrix 是一个用于处理发呢不是系统的延迟和容错的开源库。断路器本身是一种开关装置。类似一个保险丝的功能，至少要在服务不可用时有一个预期的返回，而不是一直进行等待。
3. 服务降级，服务熔断，接近实时的监控。
4. 已停更。

## 8.2 使用

1. 降级：fallback

   > 当服务不可用了，返回一个友好提示，fallback
   >
   > 会发生降级的情况：
   >
   > 程序运行异常，超时，服务熔断触发服务降级，线程池/信号量打满也会导致服务降级。

   

2. 熔断：break

   > 类比保险丝达到最大服务访问后，直接拒绝访问，然后用服务降级的方式并返回友好提示。

3. 限流：limit

   > 秒杀高并发等操作，严禁一窝蜂过来拥挤，大家排队，一秒钟N个，有序进行。

4. 压力测试后，并发过高后，由于tomcat的默认线程被打满，所以导致整个服务都会被拖慢。

5. 由于故障或表现不佳，导致请求返回结果过慢。

6. 超时导致服务器变慢：超时不再等待，不能一直卡死等待，必须有服务降级。

7. 出错：出错要有兜底。宕机了，必须要有服务降级。

8. 调用者自己有故障或有自我要求，自我等待时间过长，必须要有服务降级。

9. 降级配置：@HystrixCommand

   ~~~java
   @GetMapping("/getTimeout/{id}")
       @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
               @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000") // 捕获异常的超时处理。
       })
       public String getTimeout (@PathVariable Integer id) {
           return paymentService.paymentInfo_Timeout(id);
       }
   	// 兜底方案
       public String paymentInfo_TimeOutHandler(Integer id) {
           return "超时";
       }
   
   @SpringBootApplication
   @EnableEurekaClient
   @EnableCircuitBreaker // 增加
   public class PaymentHystrixMain8001 {
       public static void main(String[] args) {
           SpringApplication.run(PaymentHystrixMain8001.class, args);
       }
   }
   ~~~

10. 出现的问题：代码混合，代码膨胀。统一的和自定义的需要分开。

11. 解决方式,膨胀问题：

    1. 全局处理，写在类头，@DefaultProperty

       ~~~java
       @RestController
       @Log4j2
       @RequestMapping("feignHystrix")
       @DefaultProperties(defaultFallback = "getTimeout")
       public class FeignHystrixController {
           @Resource
           private PaymentHystrixService paymentHystrixService;
       
           @GetMapping("/{id}")
           public String getInfo(@PathVariable Integer id) {
               return paymentHystrixService.getInfo(id);
           }
           @GetMapping("/getTimeout/{id}")
           @HystrixCommand(fallbackMethod = "", commandProperties = {
                   @HystrixProperty(name="", value = "1500")
           })
           public String getTimeout(@PathVariable Integer id) {
               return "错误";
           }
       
       }
       ~~~

12. 解决方式，代码混乱：

    1. 

