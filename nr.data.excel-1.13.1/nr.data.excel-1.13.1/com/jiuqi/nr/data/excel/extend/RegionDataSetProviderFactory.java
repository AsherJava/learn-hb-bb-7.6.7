/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.extend;

import com.jiuqi.nr.data.excel.extend.IRegionDataSetProvider;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegionDataSetProviderFactory {
    private static final Logger logger = LoggerFactory.getLogger(RegionDataSetProviderFactory.class);
    public static final String SNAPSHOT_OLD = "SNAPSHOT";
    public static final String SNAPSHOT = "snapshotRegionDataSetProvider";
    public static final String DEFAULT = "defaultRegionDataSetProvider";
    @Autowired
    private Map<String, IRegionDataSetProvider> beans;

    public IRegionDataSetProvider getRegionDataSetProvider(String type) {
        if (this.beans.containsKey(type)) {
            return this.beans.get(type);
        }
        if (SNAPSHOT.equals(type) && this.beans.containsKey(SNAPSHOT_OLD)) {
            return this.beans.get(SNAPSHOT_OLD);
        }
        logger.error("\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b{}\u7684\u6570\u636e\u96c6\u63d0\u4f9b\u5668\uff0c\u4f7f\u7528\u9ed8\u8ba4\u6570\u636e\u96c6\u63d0\u4f9b\u5668\u67e5\u8be2\u6570\u636e\u3002", (Object)type);
        return this.beans.get(DEFAULT);
    }
}

