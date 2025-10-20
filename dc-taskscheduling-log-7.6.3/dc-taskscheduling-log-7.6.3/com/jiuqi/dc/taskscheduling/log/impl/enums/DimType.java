/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.enums;

import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;

public enum DimType implements IDimType
{
    DATASOURCECODE("\u6570\u636e\u6e90"),
    UNITCODE("\u7ec4\u7ec7\u673a\u6784"),
    PROFITCENTERCODE("\u5229\u6da6\u4e2d\u5fc3"),
    SAPBUKRS("SAP\u6838\u7b97\u516c\u53f8"),
    MODELTYPE("\u6a21\u5757\u7c7b\u578b"),
    BASEDATADEFINE("\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49");

    private String title;

    private DimType(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getName() {
        return this.name();
    }
}

