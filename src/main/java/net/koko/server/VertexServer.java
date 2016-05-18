package net.koko.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.stereotype.Component;

@Component
public class VertexServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        StaticHandler staticHandler = StaticHandler.create("static");
        router.route().handler(staticHandler);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

}
