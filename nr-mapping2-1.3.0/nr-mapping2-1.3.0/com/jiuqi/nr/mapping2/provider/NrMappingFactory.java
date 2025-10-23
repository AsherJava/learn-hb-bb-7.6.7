/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.mapping.provider.IMappingConfigProvider
 *  com.jiuqi.nvwa.mapping.provider.IMappingFactory
 *  com.jiuqi.nvwa.mapping.provider.IMappingStorageProvider
 */
package com.jiuqi.nr.mapping2.provider;

import com.jiuqi.nr.mapping2.provider.NrMappingConfigProvider;
import com.jiuqi.nr.mapping2.provider.NrMappingStorageProvider;
import com.jiuqi.nvwa.mapping.provider.IMappingConfigProvider;
import com.jiuqi.nvwa.mapping.provider.IMappingFactory;
import com.jiuqi.nvwa.mapping.provider.IMappingStorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrMappingFactory
implements IMappingFactory {
    @Autowired
    private NrMappingConfigProvider configProvider;
    @Autowired
    private NrMappingStorageProvider storageProvider;

    public String getType() {
        return "NR-MAPPING-FACTORY";
    }

    public String getTitle() {
        return "\u5973\u5a32\u62a5\u8868";
    }

    public double getOrder() {
        return 0.0;
    }

    public IMappingConfigProvider getMappingConfigProvider() {
        return this.configProvider;
    }

    public IMappingStorageProvider getMappingStorageProvider() {
        return this.storageProvider;
    }
}

