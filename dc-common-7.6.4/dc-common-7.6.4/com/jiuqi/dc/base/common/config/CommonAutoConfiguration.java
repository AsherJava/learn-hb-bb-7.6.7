/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.config;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.dc.base.common"})
public class CommonAutoConfiguration {
    public static final AtomicInteger compressMinLength = new AtomicInteger(500000);

    public static int getCompressMinLength() {
        return compressMinLength.get();
    }

    @Value(value="${jiuqi.datacenter.compress-min-length:500000}")
    public void setCompressMinLength(Integer compressMinLength) {
        if (compressMinLength != null && compressMinLength.compareTo(0) > 0 && compressMinLength.compareTo(Integer.MAX_VALUE) < 0) {
            CommonAutoConfiguration.compressMinLength.set(compressMinLength);
        }
    }
}

