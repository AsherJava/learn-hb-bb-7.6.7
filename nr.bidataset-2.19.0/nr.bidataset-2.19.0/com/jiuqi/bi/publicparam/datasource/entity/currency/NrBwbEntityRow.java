/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.IFieldsInfo
 */
package com.jiuqi.bi.publicparam.datasource.entity.currency;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import java.util.Locale;

public class NrBwbEntityRow
implements IEntityRow {
    public static final String BWB_ENTITY_KEY = "PROVIDER_BASECURRENCY";
    private static final String BWB_ENTITY_TITLE_ZH = "\u672c\u4f4d\u5e01";
    private static final String BWB_ENTITY_TITLE_EN = "Base currency";
    private String title = "\u672c\u4f4d\u5e01";

    public NrBwbEntityRow() {
        if (!Locale.SIMPLIFIED_CHINESE.getLanguage().equals(NpContextHolder.getContext().getLocale().getLanguage())) {
            this.title = BWB_ENTITY_TITLE_EN;
        }
    }

    public String getEntityKeyData() {
        return BWB_ENTITY_KEY;
    }

    public String getParentEntityKey() {
        return null;
    }

    public Object getEntityOrder() {
        return null;
    }

    public String getIconValue() {
        return null;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.getEntityKeyData();
    }

    public boolean isStoped() {
        return false;
    }

    public IFieldsInfo getFieldsInfo() {
        return null;
    }

    public DimensionValueSet getRowKeys() {
        return null;
    }

    public AbstractData getValue(String code) throws RuntimeException {
        return null;
    }

    public String getAsString(String code) throws RuntimeException {
        return null;
    }

    public AbstractData getValue(int index) throws RuntimeException {
        return null;
    }

    public String getAsString(int index) throws RuntimeException {
        return null;
    }

    public Object getAsObject(int index) throws RuntimeException {
        return null;
    }

    public String[] getParentsEntityKeyDataPath() {
        return null;
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean hasChildren() {
        return false;
    }
}

