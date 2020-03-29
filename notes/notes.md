domain object:


https://github.com/jjenkov/java-nio-server

实际nio很难做到网络层与业务逻辑解耦，selctor嵌入到业务逻辑中，netty通过事件解耦业务逻辑

文件传输：IO和NIO对比，https://blog.csdn.net/xinfeixiang201411/article/details/80105930

https://jcp.org/en/jsr/detail?id=51
https://jcp.org/en/jsr/detail?id=203

https://docs.oracle.com/javase/tutorial/essential/io/streams.html

http://www.javanio.info/ JAVANIO的书官网

Byte, ByteArray(Byte[])，ByteBuffer，ByteArrayOutputStream，BitSet, Netty ByteBuf 区别。

Scalable I/O: Events- Vs Multithreading-based

NIO用于服务端，多线程IO经典设计。

https://en.wikipedia.org/wiki/Elliotte_Rusty_Harold (JavaIo和Java网络编程作者)

Readiness Selection 就绪性选择

Java NIO Pipe vs BlockingQueue

Pipe vs Channel

对IO抽象不同：
Streams(经典IO概念-one thread one connection): stream metaphor is that the differences between these sources and destinations are abstracted away
Byte[]

Channels(NIO概念):a new primitive I/O abstraction
Buffer:NIO data transfer is based on buffers
Selector:provides a mechanism for waiting on channels and recognizing when one or more become available for data transfer
channel.receive(buffer)
channel.register(selector)

Netty Channel VS NIO Channel?

Pipe stream，Pipe channel，Exchanger，Disruptor

理解核心抽象和隐喻以及他们的能力

网络处理基本抽象：1 client 2 server(hander=read+decode+compute+encode+send)