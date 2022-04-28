我维护业务系统参数如下

使用server模式

-server

下面3个是增加的 skywalking 日志收集用的 java agent和参数

-javaagent:/usr/local/skywalking/agent/skywalking-agent.jar 

-DSW_AGENT_NAME=

-DSW_AGENT_COLLECTOR_BACKEEND_SERVICES=127.0.0.1:11800 

下面3个是堆内存配置 初始大小和最大值一样 512m，年轻代容量 256 m

-Xmx512m 

-Xms512m 

-XX:MaxNewSize=256m 

我们用的是 jdk8 这两个限制永久代容量的参数没用了

-XX:PermSize=256m 

-XX:MaxPermSize=256m 

下面三个是系统属性配置，java.io.tmpdir指定进程的缓存临时目录，catalina.base指定tomcat实例的私有目录，java.awt.headless 开启服务器端headless模式

-Djava.io.tmpdir=

-Dcatalina.base=

-Djava.awt.headless=true 

下面是两个关于控制类卸载的参数，CMSClassUnloadingEnabled 使用CMS GC 时可进行类卸载，但是我们用的是 jdk8，没开 CMS GC，应该是没用。CMSPermGenSweepingEnabled 这个应该是从 jdk6 开始就已经移除了，也没用。

-XX:+CMSClassUnloadingEnabled 

-XX:+CMSPermGenSweepingEnabled

总结一下：14个参数里4个都没用，如果不需要兼容8以前的jdk -XX:PermSize -XX:MaxPermSize可以去掉了， 如果需要限制Metaspace应该增加 -XX:MaxMetaspaceSize，如果需要使用 CMSClassUnloadingEnabled 应该增加 -XX:+UseConcMarkSweepGC 开启 CMS GC，开启后不加 -XX:+CMSClassUnloadingEnabled 应该也可以，根据oracle jdk8 文档中找到关于该参数的说明，这个值是默认开启的。

我们的系统性能要求低（10TPS），业务量少，部署方案是32~64g内存的单台机器部署10~15个微服务的方式，一个jvm进程的内存用量不能太大，最好控制在1.5g以内，所以堆内存设置就比较小。