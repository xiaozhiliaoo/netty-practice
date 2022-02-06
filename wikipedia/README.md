domain object:

epoll，select 必须到真实C代码

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

Netty(新的抽象概念)：universal asynchronous I/O interface called a Channel
Rich Buffer(ByteBuf)
Event Model based on the Interceptor Chain Pattern（ChannelEvent，ChannelHandler，ChannelPipeline）
Netty核心： buffer, channel, and event model

Netty Channel VS NIO Channel?

Go里面的Channel？抽象了什么

Pipe stream，Pipe channel，Exchanger，Disruptor

理解核心抽象和隐喻以及他们的能力

网络处理基本抽象：1 client 2 server(hander=read+decode+compute+encode+send)

Norman Maurer：
https://www.youtube.com/watch?v=DKJ0w30M0vg&t=55s

netty in action 作者 http://normanmaurer.me/

direct memory

netty为什么高性能？其实已经给了答案，照着高性能说，netty的特点。

https://en.wikipedia.org/wiki/Reactor_pattern

C10K：http://www.kegel.com/c10k.html

network_programming

mmap,sendfile

The transferTo method [25] in java.nio.channels.FileChannel is
available for transferring data from a channel to a given writable
byte channel through the sendfile system call. This would allow
us to reduce the communication overhead between the Java
Servlet Engine and the Web server with our proposed approach

Zero-Copy in Linux with sendfile() and splice().

zero-copy operations reduce the number of time-consuming mode switches between user space and kernel space

sendfile - transfer data between file descriptors,sendfile() copies data between one file descriptor and another. 
Because this copying is done within the kernel, sendfile() is more efficient than the 
combination of read(2) and write(2), which would require transferring data to and from user space.

mmap, munmap - map or unmap files or devices into memory

web server read html and send to network, avoid user space and kernel space copy.

transmit data from a "file" descriptor to a "socket"

https://medium.com/@xunnan.xu/its-all-about-buffers-zero-copy-mmap-and-java-nio-50f2a1bfc05c

NIO性能好，

https://www.researchgate.net/profile/Doug_Lea