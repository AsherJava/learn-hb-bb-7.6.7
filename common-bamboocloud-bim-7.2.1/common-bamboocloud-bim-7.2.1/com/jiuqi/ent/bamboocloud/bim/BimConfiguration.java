/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.ent.bamboocloud.bim;

import com.jiuqi.ent.bamboocloud.bim.BimProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(value={"com.jiuqi.ent.bamboocloud.bim.**"})
@PropertySource(value={"classpath:bamboocloud-shiro-anno.properties"})
@EnableConfigurationProperties(value={BimProperties.class})
public class BimConfiguration {
}

