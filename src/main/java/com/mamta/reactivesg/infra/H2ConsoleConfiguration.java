package com.mamta.reactivesg.infra;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Configuration
//@Profile("dev")
public class H2ConsoleConfiguration {

    private Server server;

    @EventListener(ApplicationStartedEvent.class)
    public void start() throws SQLException {

        String webPort = "8082";
        this.server = Server.createWebServer("-webPort", webPort).start();

    }

    @EventListener(ContextClosedEvent.class)
    public void stop () {
        this.server.stop();
    }

}
