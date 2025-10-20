/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service.impl;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig
implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public String getUrl() {
        return "";
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
    }
}

