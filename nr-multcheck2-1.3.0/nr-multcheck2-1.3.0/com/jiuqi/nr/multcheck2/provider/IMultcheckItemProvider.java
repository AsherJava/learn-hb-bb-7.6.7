/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.bean.MultcheckScheme;
import com.jiuqi.nr.multcheck2.provider.CheckItemParam;
import com.jiuqi.nr.multcheck2.provider.CheckItemResult;
import com.jiuqi.nr.multcheck2.provider.MultCheckItemDTO;
import com.jiuqi.nr.multcheck2.provider.PluginInfo;
import java.util.Date;
import java.util.Map;

public interface IMultcheckItemProvider {
    public String getType();

    public String getTitle();

    public double getOrder();

    default public void createCheckItemTables(MultcheckScheme scheme) {
    }

    default public void cleanCheckItemTables(Date date) {
    }

    default public MultCheckItemDTO getDefaultCheckItem(String formSchemeKey) {
        return null;
    }

    public PluginInfo getPropertyPluginInfo();

    public String getItemDescribe(String var1, MultcheckItem var2);

    public MultCheckItemDTO copyCheckItem(MultcheckScheme var1, MultcheckItem var2, String var3);

    default public boolean canChangeConfig() {
        return true;
    }

    public String getRunItemDescribe(String var1, MultcheckItem var2);

    public PluginInfo getRunPropertyPluginInfo();

    public CheckItemResult runCheck(CheckItemParam var1);

    default public boolean needSaveRunConfig() {
        return true;
    }

    public PluginInfo getResultPlugin();

    default public String getEntryView() {
        return null;
    }

    default public boolean entryAlwaysDisplayView(Map<String, Object> params) {
        return false;
    }

    default public boolean supportDimension() {
        return true;
    }
}

