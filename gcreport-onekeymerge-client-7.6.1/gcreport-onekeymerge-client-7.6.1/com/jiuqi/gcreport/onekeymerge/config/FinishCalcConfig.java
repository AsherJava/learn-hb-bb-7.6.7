/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.jiuqi.gcreport.finishcalc")
@PropertySource(value={"classpath:/config/finishcalc.properties"})
public class FinishCalcConfig {
    private Integer timeOut;
    private Integer dataSumTimeOut;

    public Integer getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getDataSumTimeOut() {
        return this.dataSumTimeOut;
    }

    public void setDataSumTimeOut(Integer dataSumTimeOut) {
        this.dataSumTimeOut = dataSumTimeOut;
    }
}

