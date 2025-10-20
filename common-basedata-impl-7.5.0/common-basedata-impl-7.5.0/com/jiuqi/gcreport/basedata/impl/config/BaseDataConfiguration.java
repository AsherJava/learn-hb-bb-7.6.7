/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.basedata.impl.config;

import com.jiuqi.gcreport.basedata.impl.service.GcBaseDataService;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.web"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.cache"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.dao"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.util"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.init"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.va.init"}), @ComponentScan(value={"com.jiuqi.gcreport.basedata.impl.service"})})
public class BaseDataConfiguration {
    @Lazy(value=false)
    @Bean
    public GcBaseDataCenterTool initGcBaseDataCenterTool(GcBaseDataService basedataService) {
        GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
        tool.setBasedataService(basedataService);
        return tool;
    }
}

