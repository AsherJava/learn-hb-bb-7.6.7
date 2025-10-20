/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.annotation.ComponentStandaloneScan
 */
package com.jiuqi.bde.fetch.impl;

import com.jiuqi.bde.common.annotation.ComponentStandaloneScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentStandaloneScan(basePackages={"com.jiuqi.bde.fetch.impl"})
@PropertySource(value={"classpath:/bde-shiro/bde-shiro.properties"})
public class BdeFetchImplAutoConfiguration {
}

