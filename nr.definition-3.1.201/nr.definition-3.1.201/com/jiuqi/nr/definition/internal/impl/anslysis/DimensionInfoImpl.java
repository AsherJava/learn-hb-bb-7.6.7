/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl.anslysis;

import com.jiuqi.nr.definition.common.DimensionType;
import com.jiuqi.nr.definition.facade.analysis.DimensionAttribute;
import com.jiuqi.nr.definition.facade.analysis.DimensionInfo;
import com.jiuqi.nr.definition.internal.impl.anslysis.DimensionAttributeImpl;

public class DimensionInfoImpl
implements DimensionInfo {
    private String key;
    private String code;
    private String name;
    private String title;
    private String viewKey;
    private DimensionType type;
    private DimensionAttribute config;

    @Override
    public DimensionType getType() {
        return this.type;
    }

    @Override
    public void setType(DimensionType type) {
        this.type = type;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public DimensionAttribute getConfig() {
        if (null == this.config) {
            this.config = new DimensionAttributeImpl();
        }
        return this.config;
    }

    @Override
    public void setConfig(DimensionAttribute config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getViewKey() {
        return this.viewKey;
    }

    @Override
    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }
}

