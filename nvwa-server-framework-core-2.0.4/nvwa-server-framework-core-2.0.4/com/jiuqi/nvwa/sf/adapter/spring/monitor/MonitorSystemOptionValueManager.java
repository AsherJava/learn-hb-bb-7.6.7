/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.config.MonitorSystemOptionsProvider;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MonitorSystemOptionValueManager {
    @Autowired
    private List<MonitorSystemOptionsProvider> monitorConfigOption;

    public Boolean getEnable() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (!monitorConfigOption.getEnable().booleanValue()) continue;
            return true;
        }
        return false;
    }

    public Boolean getEnableApi() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (!monitorConfigOption.getEnableApi().booleanValue()) continue;
            return true;
        }
        return false;
    }

    public Boolean getEnableSql() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (!monitorConfigOption.getEnableSql().booleanValue()) continue;
            return true;
        }
        return false;
    }

    public String getHttpUrl() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (monitorConfigOption.getHttpUrl() == null) continue;
            return monitorConfigOption.getHttpUrl();
        }
        return null;
    }

    public Integer getBatchSize() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (monitorConfigOption.getBatchSize() == null) continue;
            return monitorConfigOption.getBatchSize();
        }
        return 5000;
    }

    public Integer getRetryCount() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (monitorConfigOption.getRetryCount() == null) continue;
            return monitorConfigOption.getRetryCount();
        }
        return 3;
    }

    public Integer getFailedRetrySeconds() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (monitorConfigOption.getFailedRetrySeconds() == null) continue;
            return monitorConfigOption.getFailedRetrySeconds();
        }
        return 900;
    }

    public Integer getSendMaxIntervalSeconds() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (monitorConfigOption.getSendMaxIntervalSeconds() == null) continue;
            return monitorConfigOption.getSendMaxIntervalSeconds();
        }
        return 180;
    }

    public Boolean useLogRecord() {
        for (MonitorSystemOptionsProvider monitorConfigOption : this.monitorConfigOption) {
            if (!monitorConfigOption.isUseLogRecord().booleanValue()) continue;
            return true;
        }
        return false;
    }
}

