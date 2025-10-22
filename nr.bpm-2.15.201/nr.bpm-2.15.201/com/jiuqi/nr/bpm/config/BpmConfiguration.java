/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.bpm.impl", "com.jiuqi.nr.bpm.todo", "com.jiuqi.nr.bpm.condition", "com.jiuqi.nr.bpm.movedata", "com.jiuqi.nr.bpm.asynctask", "com.jiuqi.nr.bpm.func", "com.jiuqi.nr.bpm.upload", "com.jiuqi.np.i18n.helper", "com.jiuqi.nr.bpm.i18n", "com.jiuqi.nr.bpm.dataflow.serviceImpl", "com.jiuqi.nr.bpm.instance", "com.jiuqi.nr.bpm.IO"})
@Configuration
public class BpmConfiguration {
    @Autowired
    private DataSource dataSource;
}

