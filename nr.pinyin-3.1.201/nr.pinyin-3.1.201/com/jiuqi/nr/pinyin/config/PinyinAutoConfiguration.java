/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.pinyin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan(basePackages={"com.jiuqi.nr.pinyin"})
public class PinyinAutoConfiguration
extends ApplicationObjectSupport {
}

