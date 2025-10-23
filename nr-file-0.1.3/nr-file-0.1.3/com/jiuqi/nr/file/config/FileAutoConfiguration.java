/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.config;

import com.jiuqi.nr.file.config.FileServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={FileServiceProperties.class})
@ComponentScan(basePackages={"com.jiuqi.nr.file"})
public class FileAutoConfiguration {
}

