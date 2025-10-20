/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler;

import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;

public interface ImpExpInnerColumnHandler {
    public String getKey();

    public String[] getLabel();

    public ColumnAlignment getAlign();

    public Integer getWidth();

    public Object getFixExportData(ExcelRowFetchSettingVO var1);

    public void importData(ExcelRowFetchSettingVO var1, String var2);

    default public int getOrder() {
        return 0;
    }
}

