package com.ants.netty.demo;

import com.ants.netty.demo.server.AntsServer;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyDemoApplication implements CommandLineRunner {

    @Autowired
    AntsServer antsServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChannelFuture future = antsServer.start("127.0.0.1", 6666);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                antsServer.destroy();
            }
        });
        //服务端管道关闭的监听器并同步阻塞,直到channel关闭,线程才会往下执行,结束进程
        future.channel().closeFuture().syncUninterruptibly();
    }
}

