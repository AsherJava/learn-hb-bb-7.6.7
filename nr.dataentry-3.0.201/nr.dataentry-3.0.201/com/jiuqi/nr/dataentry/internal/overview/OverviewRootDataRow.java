/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl
 *  com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataentry.internal.overview.OverviewVirtualNodeI18n;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;

public class OverviewRootDataRow
extends EntityRowImpl {
    public static final String ROOT_KEY = "all-unit";
    public static final String ROOT_TITLE = "\u5168\u90e8\u5355\u4f4d";
    private String key;
    private String code;
    private String title;
    private IEntityRow data;
    private String referEntityId;

    public OverviewRootDataRow(ReadonlyTableImpl table, DimensionValueSet rowKeys, int rowIndex) {
        super(table, rowKeys, rowIndex);
    }

    public OverviewRootDataRow(String key) {
        this(null, null, 0);
        this.key = key;
    }

    public static OverviewRootDataRow getRootDataRow() {
        OverviewRootDataRow unGroupRow = new OverviewRootDataRow(ROOT_KEY);
        unGroupRow.setReferEntityId(ROOT_KEY);
        unGroupRow.setCode(ROOT_KEY);
        OverviewVirtualNodeI18n virtualI18n = (OverviewVirtualNodeI18n)BeanUtil.getBean(OverviewVirtualNodeI18n.class);
        String languageName = virtualI18n.getLanguageName(ROOT_KEY);
        if (languageName != null && !languageName.isEmpty()) {
            unGroupRow.setTitle(languageName);
        } else {
            unGroupRow.setTitle(ROOT_TITLE);
        }
        return unGroupRow;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IEntityRow getData() {
        return this.data;
    }

    public void setData(IEntityRow data) {
        this.data = data;
    }

    public String getReferEntityId() {
        return this.referEntityId;
    }

    public void setReferEntityId(String referEntityId) {
        this.referEntityId = referEntityId;
    }
}

