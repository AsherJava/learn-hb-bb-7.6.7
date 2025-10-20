/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.feign.remote.DataModelFeign;
import com.jiuqi.va.feign.remote.DataModelGroupFeign;
import com.jiuqi.va.feign.remote.DataModelMaintainFeign;
import com.jiuqi.va.feign.remote.DataModelTemplateFeign;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.va.feign.impl"})
@EnableFeignClients(clients={DataModelFeign.class, DataModelGroupFeign.class, DataModelMaintainFeign.class, DataModelTemplateFeign.class})
@PropertySource(value={"classpath:va-datamodel-feign.properties"})
public class VaDataModelFeignConfig {
}

