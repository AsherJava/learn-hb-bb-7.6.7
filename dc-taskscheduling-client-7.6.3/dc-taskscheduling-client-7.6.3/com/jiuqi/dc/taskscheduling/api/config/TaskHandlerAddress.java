/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.taskscheduling.api.config;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.taskscheduling.api.config.TaskHandlerProperties;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskHandlerAddress {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskHandlerProperties taskHandlerProperties;

    public String getMainAddress() {
        if (StringUtils.isEmpty((String)this.taskHandlerProperties.getMainAddress())) {
            return null;
        }
        if (!this.taskHandlerProperties.getMainAddress().startsWith("http://") && !this.taskHandlerProperties.getMainAddress().startsWith("https://")) {
            this.logger.error(String.format("\u8fdc\u7a0b\u4efb\u52a1\u8c03\u5ea6\u4e2d\u5fc3\u914d\u7f6e\u5730\u5740\u4e0d\u5408\u6cd5", this.taskHandlerProperties.getMainAddress()));
            return null;
        }
        return this.taskHandlerProperties.getMainAddress().trim();
    }

    public Map<String, String> getSubAddresses() {
        HashMap<String, String> addressesMap = new HashMap<String, String>();
        if (this.taskHandlerProperties.getSubAddresses() != null && this.taskHandlerProperties.getSubAddresses().size() > 0) {
            for (Map.Entry<String, String> entry : this.taskHandlerProperties.getSubAddresses().entrySet()) {
                if (StringUtils.isEmpty((String)entry.getValue())) {
                    this.logger.error(String.format("\u8fdc\u7a0b\u4efb\u52a1\u8c03\u5ea6\u6a21\u5757\u3010%1$s\u3011\u672a\u914d\u7f6e\u5730\u5740", entry.getKey()));
                    continue;
                }
                if (!entry.getValue().startsWith("http://") && !entry.getValue().startsWith("https://")) {
                    this.logger.error(String.format("\u8fdc\u7a0b\u4efb\u52a1\u8c03\u5ea6\u6a21\u5757\u3010%1$s\u3011\u914d\u7f6e\u5730\u5740\u4e0d\u5408\u6cd5", entry.getKey()));
                    continue;
                }
                addressesMap.put(entry.getKey().toUpperCase(), entry.getValue().trim());
            }
        }
        return addressesMap;
    }
}

