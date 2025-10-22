/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.excel.extend.param;

import com.jiuqi.nr.data.excel.extend.param.ShowSetting;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class SheetNameEnv {
    private final FormSchemeDefine formSchemeDefine;
    private final DimensionCollection masterKeys;
    private final ShowSetting showSetting;

    public SheetNameEnv(FormSchemeDefine formSchemeDefine, DimensionCollection masterKeys, ShowSetting showSetting) {
        this.formSchemeDefine = formSchemeDefine;
        this.masterKeys = masterKeys;
        this.showSetting = showSetting;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public DimensionCollection getMasterKeys() {
        return this.masterKeys;
    }

    public ShowSetting getShowSetting() {
        return this.showSetting;
    }
}

