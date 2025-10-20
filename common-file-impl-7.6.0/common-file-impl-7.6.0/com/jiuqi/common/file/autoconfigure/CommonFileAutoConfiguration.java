/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.file.autoconfigure;

import com.jiuqi.common.file.properties.CommonFileProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.common.file"})
@EnableConfigurationProperties(value={CommonFileProperties.class})
public class CommonFileAutoConfiguration {
    private CommonFileProperties fileProperties;
}

