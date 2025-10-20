/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.impl;

import com.jiuqi.nvwa.sf.adapter.spring.util.envcheck.AbstractLaunchEnvChecker;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class HostsFileLaunchEnvCheck
extends AbstractLaunchEnvChecker {
    private final Logger logger = LoggerFactory.getLogger(HostsFileLaunchEnvCheck.class);

    @Override
    public void doCheck() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            InetAddress.getLocalHost().getHostName();
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            if (totalTimeMillis > 1000L) {
                this.logger.error("Hosts\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a\u8c03\u7528\u65f6\u95f4\u8d85\u8fc71\u79d2\uff0c\u5b9e\u9645\u8c03\u7528\u65f6\u95f4{}\u79d2", (Object)(totalTimeMillis / 1000L));
            } else {
                this.logger.info("Hosts\u6821\u9a8c\u3010\u901a\u8fc7\u3011\uff1a\u8c03\u7528\u65f6\u95f4{}\u6beb\u79d2", (Object)totalTimeMillis);
            }
        }
        catch (UnknownHostException e) {
            this.logger.error("Hosts\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public String timeoutMessage() {
        return "Hosts\u6821\u9a8c\u3010\u4e0d\u901a\u8fc7\u3011\uff1a[InetAddress.getLocalHost().getHostName()]\u8c03\u7528\u65f6\u95f4\u8fc7\u957f\uff0c\u8bf7\u68c0\u6d4bhosts\u6587\u4ef6\u662f\u5426\u6b63\u786e";
    }
}

