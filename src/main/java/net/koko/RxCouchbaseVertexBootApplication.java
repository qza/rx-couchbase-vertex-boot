package net.koko;

import io.vertx.core.Vertx;

import net.koko.server.VertexServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RxCouchbaseVertexBootApplication {

    @Autowired
    private VertexServer vertexServer;

    public static void main(String[] args) {
        SpringApplication.run(RxCouchbaseVertexBootApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        Vertx.vertx().deployVerticle(vertexServer);
    }
}
